package com.dicoding.plasticode

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ImageClassActivity: AppCompatActivity() {
    private var result: TextView? = null
    private var confidence:TextView? = null
    private var imageView: ImageView? = null
    private var picture: Button? = null
    private var imageSize = 224

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        result = findViewById(R.id.result)
        confidence = findViewById(R.id.confidence)
        imageView = findViewById(R.id.imageView)
        picture = findViewById(R.id.button)
        picture!!.setOnClickListener {
            // Launch camera if we have permission
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 1)
            } else {
                //Request camera permission if we don't have it.
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }
    }

//    private fun classifyImage(image: Bitmap?) {
//        val model = Model.newInstance(this)
//
//        // Creates inputs for reference.
//
//        // Creates inputs for reference.
//        val inputFeature0 =
//            TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
//        val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
//        byteBuffer.order(ByteOrder.nativeOrder())
//
//        // get 1D array of 224 * 224 pixels in image
//
//        // get 1D array of 224 * 224 pixels in image
//        val intValues = IntArray(imageSize * imageSize)
//        image!!.getPixels(intValues, 0, image!!.width, 0, 0, image!!.width, image!!.height)
//
//        // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
//
//        // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
//        var pixel = 0
//        for (i in 0 until imageSize) {
//            for (j in 0 until imageSize) {
//                val `val` = intValues[pixel++] // RGB
//                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 255f))
//                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 255f))
//                byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))
//            }
//        }
//
//        inputFeature0.loadBuffer(byteBuffer)
//
//        // Runs model inference and gets result.
//        val outputs = model.process(inputFeature0)
//        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
//
//        val confidences = outputFeature0.floatArray
//        // find the index of the class with the biggest confidence.
//        // find the index of the class with the biggest confidence.
//        var maxPos = 0
//        var maxConfidence = 0f
//        for (i in confidences.indices) {
//            if (confidences[i] > maxConfidence) {
//                maxConfidence = confidences[i]
//                maxPos = i
//            }
//        }
//        val classes = arrayOf("Banana", "Orange", "Pen", "Sticky Notes")
//        result!!.text = classes[maxPos]
//
//        val s = StringBuilder()
//        for (i in classes.indices) {
//            s.append(String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100))
//        }
//        confidence!!.text = s.toString()
//
//        // Releases model resources if no longer used.
//        model.close()
//    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            var image = data!!.extras!!["data"] as Bitmap?
            val dimension = image!!.width.coerceAtMost(image.height)
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
            imageView!!.setImageBitmap(image)
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
//            classifyImage(image)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}