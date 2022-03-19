package com.example.payitforward

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.payitforward.databinding.ActivityTaskAcceptRejectBinding
import com.example.payitforward.databinding.ActivityTaskTakeBinding
import com.example.payitforward.databinding.ActivityTaskDoneBinding
import com.example.payitforward.ui.chat.DialogActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.payitforward.pojo.Dialog

class ItemTaskActivity : AppCompatActivity() {

    fun openChat(view: View?) {
        val db = Firebase.firestore
        val collections = db.collection("dialog")
        collections
            .whereEqualTo("candidateId", "1")
            .whereEqualTo("taskId", "5")
            .get()
            .addOnSuccessListener { documents ->
                val id: String?
                if (documents.isEmpty) {
                    id = "1" + "_" + "5"
                    collections.add(Dialog(id, "2", "1", "5"))
                } else {
                    id = documents.toObjects(Dialog::class.java)[0].id
                }
                val intent = Intent(view!!.context, DialogActivity::class.java)
                intent.putExtra("dialogId", id)
                startActivity(intent)
            }
    }

    fun openEditTaskActivity(view: View?) {
        val intent = Intent(view!!.context, EditTaskActivity::class.java)
        startActivity(intent)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent

        when (intent.getStringExtra("taskType")) {
            "accept_reject" -> {
                val acceptRejectBinding: ActivityTaskAcceptRejectBinding =
                    ActivityTaskAcceptRejectBinding.inflate(layoutInflater)
                setContentView(acceptRejectBinding.root)
                acceptRejectBinding.buttonMessage.setOnClickListener { view -> openChat(view) }
                acceptRejectBinding.buttonEdit.setOnClickListener{view -> openEditTaskActivity(view)}
            }
            "take" -> {
                val takeBinding: ActivityTaskTakeBinding =
                    ActivityTaskTakeBinding.inflate(layoutInflater)
                setContentView(takeBinding.root)
                takeBinding.buttonMessage.setOnClickListener { view -> openChat(view) }
                takeBinding.buttonEdit.setOnClickListener{view -> openEditTaskActivity(view)}
            }
            "done" -> {
                val doneBinding: ActivityTaskDoneBinding =
                    ActivityTaskDoneBinding.inflate(layoutInflater)
                setContentView(doneBinding.root)
                doneBinding.buttonMessage.setOnClickListener { view -> openChat(view) }
                doneBinding.buttonEdit.setOnClickListener{view -> openEditTaskActivity(view)}
            }
        }
    }
}