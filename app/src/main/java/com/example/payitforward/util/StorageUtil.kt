package com.example.payitforward.util

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.*

object StorageUtil {
    private val storage: FirebaseStorage by lazy { Firebase.storage }
    private val storageRef = storage.reference

    fun uploadMessageImage(photo: Uri, onSuccess: (imagePath: String) -> Unit) {
        val imagesRef: StorageReference = storageRef.child("messagesImages/${UUID.randomUUID()}")
        imagesRef.putFile(photo).addOnSuccessListener {
            onSuccess(imagesRef.path)
        }
    }

    fun pathToReference(path: String) = storage.getReference(path)

    fun uploadTaskImage(photo: Uri, taskId: String, onSuccess: (imagePath: String) -> Unit) {
        val imagesRef: StorageReference = storageRef.child("taskImages/$taskId")
        imagesRef.putFile(photo).addOnSuccessListener {
            onSuccess(imagesRef.path)
        }
    }

    fun uploadAdditionalPhoto(photo: Uri, taskId: Int, onSuccess: (imagePath: String) -> Unit) {
        val imagesRef: StorageReference = storageRef.child("taskAdditionalImages/$taskId")
        imagesRef.putFile(photo).addOnSuccessListener {
            onSuccess(imagesRef.path)
        }
    }

    fun uploadUserImage(photo: Uri, userId: String, onSuccess: (imagePath: String) -> Unit) {
        val imagesRef: StorageReference = storageRef.child("userImages/$userId")
        imagesRef.putFile(photo).addOnSuccessListener {
            onSuccess(imagesRef.path)
        }
    }
}