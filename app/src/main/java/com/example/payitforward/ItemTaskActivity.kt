package com.example.payitforward

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.payitforward.databinding.ActivityTaskAcceptRejectBinding
import com.example.payitforward.databinding.ActivityTaskTakeBinding
import com.example.payitforward.databinding.ActivityTaskDoneBinding
import com.example.payitforward.ui.chat.DialogActivity
import com.example.payitforward.util.FirestoreUtil

class ItemTaskActivity : AppCompatActivity() {

    private fun openChat(view: View?) {
        val taskId: String = "5"
        val authorId: String = "1"
        FirestoreUtil.getDialogId(taskId, authorId) { id ->
            val intent = Intent(view!!.context, DialogActivity::class.java)
            intent.putExtra("dialogId", id)
            intent.putExtra("receiverId", authorId)
            startActivity(intent)
        }
    }

    private fun openEditTaskActivity(view: View?) {
        val intent = Intent(view!!.context, CreateDeedActivity::class.java)
        startActivity(intent)
    }

    private fun cancelActivity() {
        finish()
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
                acceptRejectBinding.buttonMenu.setOnClickListener{cancelActivity()}
            }
            "take" -> {
                val takeBinding: ActivityTaskTakeBinding =
                    ActivityTaskTakeBinding.inflate(layoutInflater)
                setContentView(takeBinding.root)
                takeBinding.buttonMessage.setOnClickListener { view -> openChat(view) }
                takeBinding.buttonEdit.setOnClickListener{view -> openEditTaskActivity(view)}
                takeBinding.buttonMenu.setOnClickListener{cancelActivity()}
            }
            "done" -> {
                val doneBinding: ActivityTaskDoneBinding =
                    ActivityTaskDoneBinding.inflate(layoutInflater)
                setContentView(doneBinding.root)
                doneBinding.buttonMessage.setOnClickListener { view -> openChat(view) }
                doneBinding.buttonEdit.setOnClickListener{view -> openEditTaskActivity(view)}
                doneBinding.buttonMenu.setOnClickListener{cancelActivity()}
            }
        }
    }
}