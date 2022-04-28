package com.example.payitforward

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.payitforward.databinding.ActivityLibraryPhotosBinding
import com.example.payitforward.util.FirestoreUtil
import com.example.payitforward.util.StorageUtil

class LibraryPhotos : AppCompatActivity() {

    private lateinit var binding: ActivityLibraryPhotosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibraryPhotosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val taskId = intent.getStringExtra("taskId") ?: "1"
        FirestoreUtil.getAdditionalPhoto(taskId) { photos ->
            for (photo in photos) {
                if (photo.imageUrl != null) {
                    val photoRef = StorageUtil.pathToReference(photo.imageUrl!!)
                    val chosenPhoto = ImageView(this)
                    val layoutParams = LinearLayout.LayoutParams(500, 500)
                    chosenPhoto.layoutParams = layoutParams
                    chosenPhoto.id = (0..1000000).random()
                    GlideApp.with(this).load(photoRef).into(chosenPhoto)
                    binding.additionalPhotosLayout.addView(chosenPhoto)
                }
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}