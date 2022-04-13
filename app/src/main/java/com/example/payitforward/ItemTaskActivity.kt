package com.example.payitforward

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.payitforward.databinding.ActivityTaskAcceptRejectBinding
import com.example.payitforward.databinding.ActivityTaskCompletedBinding
import com.example.payitforward.databinding.ActivityTaskStatusBinding
import com.example.payitforward.databinding.ActivityTaskTakeBinding
import com.example.payitforward.ui.chat.DialogActivity
import com.example.payitforward.util.FirestoreUtil

class ItemTaskActivity : AppCompatActivity() {

    private fun openChat(view: View?, taskId: String, authorId: String) {
        FirestoreUtil.getDialogId(taskId, authorId) { id ->
            val intent = Intent(view!!.context, DialogActivity::class.java)
            intent.putExtra("dialogId", id)
            intent.putExtra("receiverId", authorId)
            startActivity(intent)
        }
    }

    private fun openEditTaskActivity(view: View?, taskId: String) {
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
        // task type  |     Я создатель                 |   не Я
        // new        | status: created                 |   button: take
        // inProgress | status: in progress by username |   button: completed
        // completed  | status: completed by username   |   status: completed
        // onReview   | button:  accept/reject          |   status: waiting for review
        // accepted   | status: Completed by username   |   status: accepted by author
        // rejected   | status: Rejected  by username   |   status: rejected by author
        val currentUserId = FirestoreUtil.getCurrentUser()
        when (intent.getStringExtra("taskType")) {
            "new" -> {
                val authorId = getIntent().extras!!.getString("authorId")
                val taskId = getIntent().extras!!.getString("taskId")
                if (currentUserId == authorId) {
                    val taskStatusBinding: ActivityTaskStatusBinding =
                        ActivityTaskStatusBinding.inflate(layoutInflater)
                    setContentView(taskStatusBinding.root)
                    taskStatusBinding.buttonMessage.setOnClickListener { view -> openChat(view, taskId!!, authorId) }
                    taskStatusBinding.buttonEdit.setOnClickListener { view -> openEditTaskActivity(view, taskId!!) }
                  //  taskStatusBinding.buttonMenu.setOnClickListener { cancelActivity() }
                    taskStatusBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                    taskStatusBinding.taskName.text = getIntent().extras!!.getString("name")
                    taskStatusBinding.taskDescriptionTextView.text =
                        getIntent().extras!!.getString("description")
                    //taskStatusBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl"))
                    taskStatusBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
                    taskStatusBinding.currentStatusValue.text = "The task is created by you and waiting for the executor"
                } else {
                    val takeBinding: ActivityTaskTakeBinding =
                        ActivityTaskTakeBinding.inflate(layoutInflater)
                    setContentView(takeBinding.root)
                    takeBinding.buttonMessage.setOnClickListener { view -> openChat(view, taskId!!, authorId!!) }
                    takeBinding.buttonEdit.setOnClickListener { view -> openEditTaskActivity(view, taskId!!) }
              //      takeBinding.buttonMenu.setOnClickListener { cancelActivity() }
                    takeBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                    takeBinding.taskName.text = getIntent().extras!!.getString("name")
                    takeBinding.taskDescriptionTextView.text =
                        getIntent().extras!!.getString("description")
                    //takeBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl"))
                    takeBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
                }
            }

            "inProgress" -> {
                val authorId = getIntent().extras!!.getString("authorId")
                val taskId = getIntent().extras!!.getString("taskId")
                if (currentUserId == authorId) {
                    val taskStatusBinding: ActivityTaskStatusBinding =
                        ActivityTaskStatusBinding.inflate(layoutInflater)
                    setContentView(taskStatusBinding.root)
                    taskStatusBinding.buttonMessage.setOnClickListener { view -> openChat(view, taskId!!, authorId) }
                    taskStatusBinding.buttonEdit.setOnClickListener { view -> openEditTaskActivity(view, taskId!!) }
                   // taskStatusBinding.buttonMenu.setOnClickListener { cancelActivity() }
                    taskStatusBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                    taskStatusBinding.taskName.text = getIntent().extras!!.getString("name")
                    taskStatusBinding.taskDescriptionTextView.text =
                        getIntent().extras!!.getString("description")
                    //taskStatusBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl"))
                    taskStatusBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
                    // TODO: the task is in progress by user @name
                    taskStatusBinding.currentStatusValue.text = "The task is in progress by user"
                } else {
                    // TODO: the task was taken by user or not
                    val completedBinding: ActivityTaskCompletedBinding =
                        ActivityTaskCompletedBinding.inflate(layoutInflater)
                    setContentView(completedBinding.root)
                    completedBinding.buttonMessage.setOnClickListener { view -> openChat(view, taskId!!, authorId!!) }
                    completedBinding.buttonEdit.setOnClickListener { view -> openEditTaskActivity(view, taskId!!) }
              //      completedBinding.buttonMenu.setOnClickListener { cancelActivity() }
                    completedBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                    completedBinding.taskName.text = getIntent().extras!!.getString("name")
                    completedBinding.taskDescriptionTextView.text =
                        getIntent().extras!!.getString("description")
                    // completedBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl"))
                    completedBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
                }
            }

            "completed" -> {
                val authorId = getIntent().extras!!.getString("authorId")
                val taskId = getIntent().extras!!.getString("taskId")
                val taskStatusBinding: ActivityTaskStatusBinding =
                    ActivityTaskStatusBinding.inflate(layoutInflater)
                setContentView(taskStatusBinding.root)
                taskStatusBinding.buttonMessage.setOnClickListener { view -> openChat(view, taskId!!, authorId!!) }
                taskStatusBinding.buttonEdit.setOnClickListener { view -> openEditTaskActivity(view, taskId!!) }
           //     taskStatusBinding.buttonMenu.setOnClickListener { cancelActivity() }
                taskStatusBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                taskStatusBinding.taskName.text = getIntent().extras!!.getString("name")
                taskStatusBinding.taskDescriptionTextView.text =
                    getIntent().extras!!.getString("description")
                //taskStatusBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl"))
                taskStatusBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
                if (currentUserId == getIntent().extras!!.getString("authorId")) {
                    // TODO: add name of the user
                    taskStatusBinding.currentStatusValue.text = "The task was completed by user"
                } else {
                    taskStatusBinding.currentStatusValue.text = "The task was completed by you"
                }
            }

            "onReview" -> {
                val authorId = getIntent().extras!!.getString("authorId")
                val taskId = getIntent().extras!!.getString("taskId")
                if (currentUserId == authorId) {
                    val acceptRejectBinding: ActivityTaskAcceptRejectBinding =
                        ActivityTaskAcceptRejectBinding.inflate(layoutInflater)
                    setContentView(acceptRejectBinding.root)
                    acceptRejectBinding.buttonMessage.setOnClickListener { view -> openChat(view, taskId!!, authorId) }
                    acceptRejectBinding.buttonEdit.setOnClickListener { view ->
                        openEditTaskActivity(view, taskId!!)
                    }
          //          acceptRejectBinding.buttonMenu.setOnClickListener { cancelActivity() }
                    acceptRejectBinding.deadlineDate.text =
                        getIntent().extras!!.getString("deadlineDate")
                    acceptRejectBinding.taskName.text = getIntent().extras!!.getString("name")
                    acceptRejectBinding.taskDescriptionTextView.text =
                        getIntent().extras!!.getString("description")
                    //acceptRejectBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl"))
                    acceptRejectBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
                } else {
                    val taskStatusBinding: ActivityTaskStatusBinding =
                        ActivityTaskStatusBinding.inflate(layoutInflater)
                    setContentView(taskStatusBinding.root)
                    taskStatusBinding.buttonMessage.setOnClickListener { view -> openChat(view, taskId!!, authorId!!) }
                    taskStatusBinding.buttonEdit.setOnClickListener { view -> openEditTaskActivity(view, taskId!!) }
           //         taskStatusBinding.buttonMenu.setOnClickListener { cancelActivity() }
                    taskStatusBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                    taskStatusBinding.taskName.text = getIntent().extras!!.getString("name")
                    taskStatusBinding.taskDescriptionTextView.text =
                        getIntent().extras!!.getString("description")
                    //taskStatusBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl"))
                    taskStatusBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
                    taskStatusBinding.currentStatusValue.text = "The task is waiting for the review"
                }

            }

            "accepted" -> {
                val authorId = getIntent().extras!!.getString("authorId")
                val taskId = getIntent().extras!!.getString("taskId")
                val taskStatusBinding: ActivityTaskStatusBinding =
                    ActivityTaskStatusBinding.inflate(layoutInflater)
                setContentView(taskStatusBinding.root)
                taskStatusBinding.buttonMessage.setOnClickListener { view -> openChat(view, taskId!!, authorId!!) }
                taskStatusBinding.buttonEdit.setOnClickListener { view -> openEditTaskActivity(view, taskId!!) }
            //    taskStatusBinding.buttonMenu.setOnClickListener { cancelActivity() }
                taskStatusBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                taskStatusBinding.taskName.text = getIntent().extras!!.getString("name")
                taskStatusBinding.taskDescriptionTextView.text =
                    getIntent().extras!!.getString("description")
                //taskStatusBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl"))
                taskStatusBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
                if (currentUserId == getIntent().extras!!.getString("authorId")) {
                    taskStatusBinding.currentStatusValue.text = "The task was accepted by you"
                } else {
                    // TODO: add name of the user
                    taskStatusBinding.currentStatusValue.text = "The task was completed by user"
                }
            }

            "rejected" -> {
                val authorId = getIntent().extras!!.getString("authorId")
                val taskId = getIntent().extras!!.getString("taskId")
                val taskStatusBinding: ActivityTaskStatusBinding =
                    ActivityTaskStatusBinding.inflate(layoutInflater)
                setContentView(taskStatusBinding.root)
                taskStatusBinding.buttonMessage.setOnClickListener { view -> openChat(view, taskId!!, authorId!!) }
                taskStatusBinding.buttonEdit.setOnClickListener { view -> openEditTaskActivity(view, taskId!!) }
          //      taskStatusBinding.buttonMenu.setOnClickListener { cancelActivity() }
                taskStatusBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                taskStatusBinding.taskName.text = getIntent().extras!!.getString("name")
                taskStatusBinding.taskDescriptionTextView.text =
                    getIntent().extras!!.getString("description")
                //taskStatusBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl"))
                taskStatusBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
                if (currentUserId == getIntent().extras!!.getString("authorId")) {
                    taskStatusBinding.currentStatusValue.text = "The task was rejected by you"
                } else {
                    // TODO: add name of the user
                    taskStatusBinding.currentStatusValue.text = "The task was completed by user"
                }
            }
        }
    }
}