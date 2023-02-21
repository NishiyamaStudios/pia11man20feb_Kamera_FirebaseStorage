package se.nishiyamastudios.pia11man20feb_kamera_firebasestorage

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // Handle the returned Uri

        Log.i("PIA11DEBUG", "Vi fick resultat!")

        //.let = försök packa upp den, funkar i många sammanhang!
        //behövde API version minimum 28, man kan lägga in en if else för att hantera kod för olika API-versioner.
        uri?.let {
            val source = ImageDecoder.createSource(this.contentResolver, it)
            val bitmap = ImageDecoder.decodeBitmap(source)

            val theimage = findViewById<ImageView>(R.id.theimage)
            theimage.setImageBitmap(bitmap)

            var storageRef = Firebase.storage.reference
            // Var skall vi spara våra bilder. Går att spara under unika användare etc.
            var imageRef = storageRef.child("androidbild.jpg")

            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,80, baos)
            val data = baos.toByteArray()

            // Sparar man detta i en variabel i stället så kan man "prata" med uppladdningen, avbryta den och liknande.
            // Finns också onProgressListener för en progress bar eller liknande.
            imageRef.putBytes(data)
                .addOnFailureListener {
                    //Kod som kör om det blir fel
                    Log.i("PIA11DEBUG", "Upload fail")
                }.addOnSuccessListener {
                    //Om det gick bra, TaskSnapshot innehåller en mängd metadata om filen
                    Log.i("PIA11DEBUG", "Upload gick bra")
                }

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

        downloadImage()

    }

    // Ladda ner vår bild och visa den när appen startar
    fun downloadImage() {
        // Här vill jag ladda ner
        var storageRef = Firebase.storage.reference
        var imageRef = storageRef.child("androidbild.jpg")

        // När man laddar ner så måste man ange vilken max storlek den får lov att vara
        // _ behövs inte men gör det mer lättläst
        imageRef.getBytes(1_000_000).addOnSuccessListener {
            //När det går bra så får vi tillbaks en ByteArray, alltså datan vi laddat ner

        }.addOnFailureListener {
            //När det går dåligt så får vi ut exceptionmeddelande genom it

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