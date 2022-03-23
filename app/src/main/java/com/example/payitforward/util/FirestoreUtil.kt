package com.example.payitforward.util

import com.example.payitforward.pojo.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirestoreUtil {
    private val firestore: FirebaseFirestore by lazy { Firebase.firestore }
    private val collectionsMessage = firestore.collection("message")
    val collectionsTask = firestore.collection("task")
    val collectionsDialog = firestore.collection("dialog")
    val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

    fun sendMessage(message: Message) {
        collectionsMessage.add(message)
    }

    fun getMessages(dialogId: String, onSuccess: (messages: List<Message>) -> Unit) {
        collectionsMessage.whereEqualTo("dialogId", dialogId).addSnapshotListener { snapshots, e ->
            if (snapshots != null) {
                val messages = mutableListOf<Message>()
                snapshots.documents.forEach {
                    if (it["type"] == MessageType.TEXT) {
                        messages.add(it.toObject(TextMessage::class.java)!!)
                    } else {
                        messages.add(it.toObject(ImageMessage::class.java)!!)
                    }
                }
                onSuccess(messages.sortedBy { it.time })
            }
        }
    }

    fun getLastMessage(dialogId: String, onSuccess: (messages: Message?) -> Unit) {
        collectionsMessage
            .whereEqualTo("dialogId", dialogId)
            .addSnapshotListener { snapshots, e ->
                if (snapshots != null) {
                    val messages = mutableListOf<Message>()
                    snapshots.documents.forEach {
                        if (it["type"] == MessageType.TEXT) {
                            messages.add(it.toObject(TextMessage::class.java)!!)
                        } else {
                            messages.add(it.toObject(ImageMessage::class.java)!!)
                        }
                    }
                    onSuccess(messages.maxByOrNull { it.time })
                }
            }
    }

    fun addTask(task: Task) {
        collectionsTask.add(task)
    }

    fun getDialogsOwner(onSuccess: (dialogs: List<Dialog>) -> Unit) {
        collectionsDialog
            .whereEqualTo("ownerId", currentUserId)
            .get()
            .addOnSuccessListener { snapshots ->
                val dialogs: List<Dialog> = snapshots.toObjects(Dialog::class.java)
                onSuccess(dialogs)
            }
    }

    fun getDialogsCandidate(onSuccess: (dialogs: List<Dialog>) -> Unit) {
        collectionsDialog
            .whereEqualTo("candidateId", currentUserId)
            .get()
            .addOnSuccessListener { snapshots ->
                val dialogs: List<Dialog> = snapshots.toObjects(Dialog::class.java)
                onSuccess(dialogs)
            }
    }

    fun getDialogId(taskId: String, ownerId: String, onSuccess: (id: String) -> Unit) {
        collectionsDialog
            .whereEqualTo("candidateId", currentUserId)
            .whereEqualTo("taskId", taskId)
            .get()
            .addOnSuccessListener { documents ->
                val id: String?
                if (documents.isEmpty) {
                    id = currentUserId + "_" + taskId
                    collectionsDialog.add(Dialog(id, ownerId, currentUserId, taskId))
                } else {
                    id = documents.toObjects(Dialog::class.java)[0].id
                }
                onSuccess(id)
            }
    }

    fun getTask(taskId: String, onSuccess: (task: Task?) -> Unit) {
        collectionsTask
            .whereEqualTo("id", taskId)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    onSuccess(null)
                } else {
                    onSuccess(documents.toObjects(Task::class.java)[0])
                }
            }
    }

    fun getCompletedTask(onSuccess: (tasks: List<Task>) -> Unit) {
        collectionsTask
            .whereEqualTo("completedId", currentUserId)
            .get()
            .addOnSuccessListener { documents ->
                onSuccess(documents.toObjects(Task::class.java))
            }
    }
}