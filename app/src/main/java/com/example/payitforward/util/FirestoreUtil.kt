package com.example.payitforward.util

import android.util.Log
import androidx.constraintlayout.widget.StateSet.TAG
import com.example.payitforward.pojo.*
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirestoreUtil {
    private val firestore: FirebaseFirestore by lazy { Firebase.firestore }
    private val collectionsMessage = firestore.collection("message")
    private val collectionsTask = firestore.collection("task")
    private val collectionsDialog = firestore.collection("dialog")
    private var currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
    private val collectionsUsers = firestore.collection("users")
    private val collectionsAdditionalPhoto = firestore.collection("additionalPhoto")

    fun setUserId(userId: String) {
        currentUserId = userId
    }

    fun sendMessage(message: Message) {
        collectionsMessage.add(message)
    }

    fun getMessages(dialogId: String, onSuccess: (messages: List<Message>) -> Unit) {
        collectionsMessage.whereEqualTo("dialogId", dialogId).addSnapshotListener { snapshots, _ ->
            if (snapshots != null) {
                val messages = mutableListOf<Message>()
                snapshots.documents.forEach {
                    if (it["type"] == MessageType.TEXT) {
                        messages.add(it.toObject(TextMessage::class.java)!!)
                    } else if ((it["type"] == MessageType.IMAGE)) {
                        messages.add(it.toObject(ImageMessage::class.java)!!)
                    }
                }
                onSuccess(messages.sortedBy { it.time })
            }
        }
    }

    fun getNewMessages(myId: String, onSuccess: (messages: Message) -> Unit) {
        collectionsMessage
            .whereEqualTo("receiverId", myId)
            .addSnapshotListener { snapshots, _ ->
                if (snapshots != null) {
                    val messages = mutableListOf<Message>()
                    snapshots.documents.forEach {
                        if (it["type"] == MessageType.TEXT) {
                            messages.add(it.toObject(TextMessage::class.java)!!)
                        } else {
                            messages.add(it.toObject(ImageMessage::class.java)!!)
                        }
                    }
                    val ans = messages.maxByOrNull { it.time }
                    if (ans != null) {
                        onSuccess(ans)
                    }
                }
            }
    }

    fun getLastMessage(dialogId: String, onSuccess: (message: Message?) -> Unit) {
        collectionsMessage
            .whereEqualTo("dialogId", dialogId)
            .addSnapshotListener { snapshots, _ ->
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
        collectionsTask.document(task.id).set(task)
    }

    fun deleteTask(id: String) {
        collectionsTask.document(id).delete()
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

    fun getDialogsCandidate(onSuccess: (dialogs2: List<Dialog>) -> Unit) {
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

    fun getCompletedTasks(onSuccess: (tasks: List<Task>) -> Unit) {
        collectionsTask
            .whereEqualTo("type", "completed")
            .whereEqualTo("executorId", currentUserId)
            .addSnapshotListener { snapshot, _ ->
                val tasks = mutableListOf<Task>()
                if (snapshot != null) {
                    snapshot.documents.forEach {
                        tasks.add(it.toObject(Task::class.java)!!)
                    }
                    onSuccess(tasks)
                }
            }
    }

    fun getAllTasks(onSuccess: (tasks: List<Task>) -> Unit) {
        collectionsTask
            .addSnapshotListener { snapshot, _ ->
                val tasks = mutableListOf<Task>()
                if (snapshot != null) {
                    snapshot.documents.forEach {
                        tasks.add(it.toObject(Task::class.java)!!)
                    }
                    onSuccess(tasks)
                }
            }
    }

    fun getTasksForGet(authorId: String, onSuccess: (tasks: List<Task>) -> Unit) {
        collectionsTask
            .whereEqualTo("executorId", authorId)
            .addSnapshotListener { snapshot, _ ->
                val tasks = mutableListOf<Task>()
                if (snapshot != null) {
                    snapshot.documents.forEach {
                        tasks.add(it.toObject(Task::class.java)!!)
                    }
                    onSuccess(tasks)
                }
            }
    }

    fun getTasksForGive(authorId: String, onSuccess: (tasks: List<Task>) -> Unit) {
        collectionsTask
            .whereEqualTo("authorId", authorId)
            .addSnapshotListener { snapshot, _ ->
                val tasks = mutableListOf<Task>()
                if (snapshot != null) {
                    snapshot.documents.forEach {
                        tasks.add(it.toObject(Task::class.java)!!)
                    }
                    onSuccess(tasks)
                }
            }
    }

    fun searchTasks(text: String, onSuccess: (tasks: List<Task>) -> Unit) {
        if (text.isEmpty()) {
            getAllTasks(onSuccess)
        } else {
            collectionsTask
                .whereEqualTo("name", text)
                .get()
                .addOnSuccessListener { documents ->
                    onSuccess(documents.toObjects(Task::class.java))
                }
        }
    }

    fun filterTasks(
        filters: List<String>,
        onSuccess: (tasks: List<Task>) -> Unit
    ) {
        collectionsTask
            .whereIn("type", filters)
            .get()
            .addOnSuccessListener { documents ->
                onSuccess(documents.toObjects(Task::class.java))
            }
    }

    fun changeTaskType(taskId: String, typeToChange: String) {
        collectionsTask.document(taskId).update("type", typeToChange)
        if (typeToChange == "inProgress") {
            collectionsTask.document(taskId).update("executorId", currentUserId)
        }
        if (typeToChange == "completed") {
            collectionsTask.document(taskId).update("completionDate", Timestamp.now())
        }
    }

    fun addUser(user: User) {
        collectionsUsers.document(user.id).set(user).addOnSuccessListener { _ ->
            Log.d(TAG, "User added with ID: ${user.id}")
        }
    }

    fun getUser(userId: String, onSuccess: (user: User?) -> Unit) {
        collectionsUsers
            .whereEqualTo("id", userId)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    onSuccess(null)
                } else {
                    onSuccess(documents.toObjects(User::class.java)[0])
                }
            }
    }

    fun changeUserName(userId: String, newName: String) {
        collectionsUsers.document(userId).update("name", newName)
    }

    fun changeUserUsername(userId: String, newUsername: String) {
        collectionsUsers.document(userId).update("username", newUsername)
    }

    fun changeUserBio(userId: String, newBio: String) {
        collectionsUsers.document(userId).update("bio", newBio)
    }

    fun getCurrentUser(): String {
        return Firebase.auth.currentUser!!.uid
    }

    fun changeUserPhoto(userId: String, editPhoto: String?) {
        collectionsUsers.document(userId).update("photo", editPhoto)
    }

    fun addAdditionalPhoto(photo: AdditionalPhoto) {
        collectionsAdditionalPhoto.add(photo)
    }

    fun getAdditionalPhoto(taskId: String, onSuccess: (photos: List<AdditionalPhoto>) -> Unit) {
        collectionsAdditionalPhoto
            .whereEqualTo("taskId", taskId)
            .get()
            .addOnSuccessListener { documents ->
                onSuccess(documents.toObjects(AdditionalPhoto::class.java))
            }
    }
}