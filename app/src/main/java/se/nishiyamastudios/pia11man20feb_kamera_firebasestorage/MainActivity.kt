package se.nishiyamastudios.pia11man20feb_kamera_firebasestorage

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.cameraButton).setOnClickListener {
            dispatchTakePictureIntent()
        }

        findViewById<Button>(R.id.galleryButton).setOnClickListener {

        }

    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, 1) //REQUEST_IMAGE_CAPTURE utbytt mot 1:an
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

}