package com.example.payitforward

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.payitforward.databinding.ActivityTaskAcceptRejectBinding
import com.example.payitforward.databinding.ActivityTaskTakeBinding
import com.example.payitforward.databinding.ActivityTaskDoneBinding
import com.example.payitforward.ui.chat.DialogActivity
import com.example.payitforward.util.FirestoreUtil

class ItemTaskActivity : AppCompatActivity() {

    private fun openChat(view: View?) {
        val taskId = "5"
        val authorId = "1"
        FirestoreUtil.getDialogId(taskId, authorId) { id ->
            val intent = Intent(view!!.context, DialogActivity::class.java)
            intent.putExtra("dialogId", id)
            intent.putExtra("receiverId", authorId)
            startActivity(intent)
        }
    }

    private fun openEditTaskActivity(view: View?) {
        val taskId = "5"
        val intent = Intent(view!!.context, CreateDeedActivity::class.java)
        intent.putExtra("taskId", taskId)
        startActivity(intent)
    }

    private fun cancelActivity() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent

        when (intent.getStringExtra("taskType")) {
            "onReview" -> {
                val acceptRejectBinding: ActivityTaskAcceptRejectBinding =
                    ActivityTaskAcceptRejectBinding.inflate(layoutInflater)
                setContentView(acceptRejectBinding.root)
                acceptRejectBinding.buttonMessage.setOnClickListener { view -> openChat(view) }
                acceptRejectBinding.buttonEdit.setOnClickListener{view -> openEditTaskActivity(view)}
                acceptRejectBinding.buttonMenu.setOnClickListener{cancelActivity()}
                acceptRejectBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                acceptRejectBinding.taskName.text = getIntent().extras!!.getString("name")
                acceptRejectBinding.taskDescriptionTextView.text = getIntent().extras!!.getString("description")
                //acceptRejectBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl"))
                acceptRejectBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
            }
            "new" -> {
                val takeBinding: ActivityTaskTakeBinding =
                    ActivityTaskTakeBinding.inflate(layoutInflater)
                setContentView(takeBinding.root)
                takeBinding.buttonMessage.setOnClickListener { view -> openChat(view) }
                takeBinding.buttonEdit.setOnClickListener{view -> openEditTaskActivity(view)}
                takeBinding.buttonMenu.setOnClickListener{cancelActivity()}
                takeBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                takeBinding.taskName.text = getIntent().extras!!.getString("name")
                takeBinding.taskDescriptionTextView.text = getIntent().extras!!.getString("description")
                //takeBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl"))
                takeBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
            }
            "completed" -> {
                val doneBinding: ActivityTaskDoneBinding =
                    ActivityTaskDoneBinding.inflate(layoutInflater)
                setContentView(doneBinding.root)
                doneBinding.buttonMessage.setOnClickListener { view -> openChat(view) }
                doneBinding.buttonEdit.setOnClickListener{view -> openEditTaskActivity(view)}
                doneBinding.buttonMenu.setOnClickListener{cancelActivity()}
                doneBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                doneBinding.taskName.text = getIntent().extras!!.getString("name")
                doneBinding.taskDescriptionTextView.text = getIntent().extras!!.getString("description")
                // doneBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl"))
                doneBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
            }
        }
    }
}