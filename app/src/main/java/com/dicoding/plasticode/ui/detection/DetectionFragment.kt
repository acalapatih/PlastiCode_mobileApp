package com.dicoding.plasticode.ui.detection

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dicoding.plasticode.databinding.FragmentDetectionBinding
import com.dicoding.plasticode.ml.YourModel
import com.dicoding.plasticode.ui.dashboard.DashboardActivity
import com.dicoding.plasticode.ui.detection.camera.CameraActivity
import com.dicoding.plasticode.ui.detection.result.DetectionResultActivity
import com.dicoding.plasticode.ui.menu.MenuActivity
import com.dicoding.plasticode.utils.reduceFileImage
import com.dicoding.plasticode.utils.rotateFile
import com.dicoding.plasticode.utils.uriToFile
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder


class DetectionFragment : Fragment() {
    private var _binding: FragmentDetectionBinding? = null
    private val binding get() = _binding!!
    private var getFile: File? = null
    private var imageSize = 224

    private lateinit var baseActivity: DashboardActivity

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if (result.resultCode == CAMERA_X_RESULT){
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                result.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                result.data?.getSerializableExtra("picture")
            } as? File
            val isBackCamera = result.data?.getBooleanExtra("isBackCamera", true) as Boolean
            myFile?.let { file ->
                rotateFile(file, isBackCamera)
                getFile = file
                binding.ivDeteksi.setImageBitmap(BitmapFactory.decodeFile(file.path))
                binding.deteksiButton.isEnabled = true
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK){
            val selectedImg = result.data?.data as Uri

            selectedImg.let {
                val myFile = uriToFile(it, requireContext())
                getFile = myFile
                binding.ivDeteksi.setImageURI(it)
                binding.deteksiButton.isEnabled = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivity = activity as DashboardActivity

        if (!allPermissionGranted()){
            ActivityCompat.requestPermissions(
                activity as DashboardActivity,
                REQUIRED_PERMISSION,
                REQUEST_CODE_PERMISSION
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initListener()
    }

    private fun classifyImage(image: Bitmap?): String {
        val model = YourModel.newInstance(requireContext())

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(imageSize * imageSize)
        image?.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)

        var pixel = 0
        for (i in 0 until imageSize) {
            for (j in 0 until imageSize) {
                val `val` = intValues[pixel++] // RGB
                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 255f))
                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 255f))
                byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))
            }
        }

        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
        val confidences = outputFeature0.floatArray

        // find the index of the class with the biggest confidence.
        var maxPos = 0
        var maxConfidence = 0f
        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }
        val classes = arrayOf("HDPE", "LDPE", "OTHER", "PET atau PETE", "PP", "PS", "PVC")
        var s = ""
        for (i in classes.indices) {
            s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100)
        }

        println("PREDIKSI == $s")

        // Releases model resources if no longer used.
        model.close()

        return if (maxConfidence > 0.7) {
            classes[maxPos]
        } else if (maxConfidence > 0.6 && classes[maxPos] != "OTHER") {
            classes[maxPos]
        } else {
            "SUS"
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith(
            "super.onRequestPermissionsResult(requestCode, permissions, grantResults)",
            "androidx.fragment.app.Fragment"
        )
    )
    @Suppress("Deprecation")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION){
            if (!allPermissionGranted()){
                Toast.makeText(activity, "Izin tidak diberikan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSION.all {
        ContextCompat.checkSelfPermission(activity!!.baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun runDetection(): String {
        val file = reduceFileImage(getFile as File)
        var image = BitmapFactory.decodeFile(file.toString())
        val dimension = image.width.coerceAtMost(image.height)
        image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
        image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
        return classifyImage(image)
    }

    private fun runGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun runCameraX() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun initListener() {
        with(binding) {
            cameraButton.setOnClickListener { runCameraX() }
            galleryButton.setOnClickListener { runGallery() }
            deteksiButton.setOnClickListener {
                getFile?.let { file ->
                    DetectionResultActivity.start(requireContext(), file.path, runDetection())
                }
            }
            icMenu.setOnClickListener {
                MenuActivity.start(requireContext())
            }
        }
    }

    companion object{
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSION = 10
    }
}