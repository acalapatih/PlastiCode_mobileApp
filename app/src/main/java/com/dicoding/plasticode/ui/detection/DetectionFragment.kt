package com.dicoding.plasticode.ui.detection

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dicoding.plasticode.R
import com.dicoding.plasticode.databinding.FragmentDetectionBinding
import com.dicoding.plasticode.ui.dashboard.DashboardActivity
import com.dicoding.plasticode.ui.detection.camera.CameraActivity
import com.dicoding.plasticode.ui.detection.result.DetectionResultActivity
import com.dicoding.plasticode.ui.menu.MenuActivity
import com.dicoding.plasticode.utils.reduceFileImage
import com.dicoding.plasticode.utils.rotateFile
import com.dicoding.plasticode.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class DetectionFragment : Fragment() {
    private var _binding: FragmentDetectionBinding? = null
    private val binding get() = _binding!!
    private var getFile: File? = null

    private lateinit var baseActivity: DashboardActivity

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == CAMERA_X_RESULT){
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File

            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            myFile?.let {
                rotateFile(it, isBackCamera)
                getFile = it
                binding.ivDeteksi.setImageBitmap(BitmapFactory.decodeFile(it.path))
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

    private fun runDetection() {
        val intent = Intent(requireContext(), DetectionResultActivity::class.java)
        startActivity(intent)
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

    private fun uploadFile() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
        }
    }

    private fun initObserver() {
        TODO("Not yet implemented")
    }

    private fun initListener() {
        val cameraXButton = view?.findViewById<Button>(R.id.camera_button)
        val galleryButton = view?.findViewById<Button>(R.id.gallery_button)
        val deteksiButton = view?.findViewById<Button>(R.id.deteksi_button)

        with(binding) {
            cameraXButton?.setOnClickListener { runCameraX() }
            galleryButton?.setOnClickListener { runGallery() }
            deteksiButton?.setOnClickListener { runDetection() }
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