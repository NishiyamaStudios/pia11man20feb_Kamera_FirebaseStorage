package se.nishiyamastudios.pia11man20feb_kamera_firebasestorage

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // Handle the returned Uri

        Log.i("PIA11DEBUG", "Vi fick resultat!")

        //.let = försök packa upp den, funkar i många sammanhang!
        uri?.let {

        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.cameraButton).setOnClickListener {
            dispatchTakePictureIntent()
        }

        findViewById<Button>(R.id.galleryButton).setOnClickListener {
            getContent.launch("image/*")
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)



    }

}