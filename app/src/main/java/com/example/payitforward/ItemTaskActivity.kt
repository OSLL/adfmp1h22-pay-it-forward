package com.example.payitforward

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
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
                    taskStatusBinding.buttonMessage.layoutParams.width = 0
                    taskStatusBinding.buttonMessage.visibility = View.INVISIBLE
                    taskStatusBinding.buttonEdit.setOnClickListener { view -> openEditTaskActivity(view, taskId!!) }
                    taskStatusBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                    taskStatusBinding.taskName.text = getIntent().extras!!.getString("name")
                    taskStatusBinding.taskDescriptionTextView.text =
                        getIntent().extras!!.getString("description")
                    //taskStatusBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl")!!.toUri())
                    taskStatusBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
                    taskStatusBinding.currentStatusValue.text = "The task is created by you and waiting for the executor"
                } else {
                    val takeBinding: ActivityTaskTakeBinding =
                        ActivityTaskTakeBinding.inflate(layoutInflater)
                    setContentView(takeBinding.root)
                    takeBinding.buttonMessage.setOnClickListener { view -> openChat(view, taskId!!, authorId!!) }
                    takeBinding.buttonEdit.layoutParams.width = 0
                    takeBinding.buttonEdit.visibility = View.INVISIBLE
                    takeBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                    takeBinding.taskName.text = getIntent().extras!!.getString("name")
                    takeBinding.taskDescriptionTextView.text =
                        getIntent().extras!!.getString("description")
                    //takeBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl")!!.toUri())
                    takeBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
                    takeBinding.buttonTake.setOnClickListener{
                        FirestoreUtil.changeTaskType(taskId.toString(),  "inProcess")
                        finish()
                    }
                }
            }

            "inProgress" -> {
                val authorId = getIntent().extras!!.getString("authorId")
                val taskId = getIntent().extras!!.getString("taskId")
                if (currentUserId == authorId) {
                    val taskStatusBinding: ActivityTaskStatusBinding =
                        ActivityTaskStatusBinding.inflate(layoutInflater)
                    setContentView(taskStatusBinding.root)
                    taskStatusBinding.buttonMessage.layoutParams.width = 0
                    taskStatusBinding.buttonMessage.visibility = View.INVISIBLE
                    taskStatusBinding.buttonEdit.setOnClickListener { view -> openEditTaskActivity(view, taskId!!) }
                    taskStatusBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                    taskStatusBinding.taskName.text = getIntent().extras!!.getString("name")
                    taskStatusBinding.taskDescriptionTextView.text =
                        getIntent().extras!!.getString("description")
                    //taskStatusBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl")!!.toUri())
                    taskStatusBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
                    // TODO: the task is in progress by user @name
                    taskStatusBinding.currentStatusValue.text = "The task is in progress by user"
                } else {
                    // TODO: the task was taken by user or not
                    val completedBinding: ActivityTaskCompletedBinding =
                        ActivityTaskCompletedBinding.inflate(layoutInflater)
                    setContentView(completedBinding.root)
                    completedBinding.buttonMessage.setOnClickListener { view -> openChat(view, taskId!!, authorId!!) }
                    completedBinding.buttonEdit.layoutParams.width = 0
                    completedBinding.buttonEdit.visibility = View.INVISIBLE
                    completedBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                    completedBinding.taskName.text = getIntent().extras!!.getString("name")
                    completedBinding.taskDescriptionTextView.text =
                        getIntent().extras!!.getString("description")
                    // completedBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl")!!.toUri())
                    completedBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
                    completedBinding.buttonDone.setOnClickListener{
                        FirestoreUtil.changeTaskType(taskId.toString(),  "completed")
                        finish()
                    }
                }
            }

            "completed" -> {
                val authorId = getIntent().extras!!.getString("authorId")
                val taskId = getIntent().extras!!.getString("taskId")
                val taskStatusBinding: ActivityTaskStatusBinding =
                    ActivityTaskStatusBinding.inflate(layoutInflater)
                setContentView(taskStatusBinding.root)
                taskStatusBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                taskStatusBinding.taskName.text = getIntent().extras!!.getString("name")
                taskStatusBinding.taskDescriptionTextView.text =
                    getIntent().extras!!.getString("description")
                //taskStatusBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl")!!.toUri())
                taskStatusBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
                if (currentUserId == getIntent().extras!!.getString("authorId")) {
                    // TODO: add name of the user
                    taskStatusBinding.buttonMessage.layoutParams.width = 0
                    taskStatusBinding.buttonMessage.visibility = View.INVISIBLE
                    taskStatusBinding.currentStatusValue.text = "The task was completed by user"
                    taskStatusBinding.buttonEdit.setOnClickListener { view -> openEditTaskActivity(view, taskId!!) }
                } else {
                    taskStatusBinding.buttonMessage.setOnClickListener { view -> openChat(view, taskId!!, authorId!!) }
                    taskStatusBinding.buttonEdit.layoutParams.width = 0
                    taskStatusBinding.buttonEdit.visibility = View.INVISIBLE
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
                    acceptRejectBinding.buttonMessage.layoutParams.width = 0
                    acceptRejectBinding.buttonMessage.visibility = View.INVISIBLE
                    acceptRejectBinding.buttonEdit.setOnClickListener { view ->
                        openEditTaskActivity(view, taskId!!)
                    }
                    acceptRejectBinding.deadlineDate.text =
                        getIntent().extras!!.getString("deadlineDate")
                    acceptRejectBinding.taskName.text = getIntent().extras!!.getString("name")
                    acceptRejectBinding.taskDescriptionTextView.text =
                        getIntent().extras!!.getString("description")
                    //acceptRejectBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl")!!.toUri())
                    acceptRejectBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
                    acceptRejectBinding.buttonAccept.setOnClickListener{
                        FirestoreUtil.changeTaskType(taskId.toString(), "accepted")
                        finish()
                    }
                    acceptRejectBinding.buttonReject.setOnClickListener{
                        FirestoreUtil.changeTaskType(taskId.toString(),  "rejected")
                        finish()
                    }
                } else {
                    val taskStatusBinding: ActivityTaskStatusBinding =
                        ActivityTaskStatusBinding.inflate(layoutInflater)
                    setContentView(taskStatusBinding.root)
                    taskStatusBinding.buttonMessage.setOnClickListener { view -> openChat(view, taskId!!, authorId!!) }
                    taskStatusBinding.buttonEdit.layoutParams.width = 0
                    taskStatusBinding.buttonEdit.visibility = View.INVISIBLE
                    taskStatusBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                    taskStatusBinding.taskName.text = getIntent().extras!!.getString("name")
                    taskStatusBinding.taskDescriptionTextView.text =
                        getIntent().extras!!.getString("description")
                    // taskStatusBinding.taskImageView.setImageURI(getIntent().extras!!.getString("imageUrl")!!.toUri())
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
                taskStatusBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                taskStatusBinding.taskName.text = getIntent().extras!!.getString("name")
                taskStatusBinding.taskDescriptionTextView.text =
                    getIntent().extras!!.getString("description")
                //taskStatusBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl")!!.toUri())
                taskStatusBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
                if (currentUserId == getIntent().extras!!.getString("authorId")) {
                    taskStatusBinding.buttonMessage.layoutParams.width = 0
                    taskStatusBinding.buttonMessage.visibility = View.INVISIBLE
                    taskStatusBinding.buttonEdit.setOnClickListener { view -> openEditTaskActivity(view, taskId!!) }
                    taskStatusBinding.currentStatusValue.text = "The task was accepted by you"
                } else {
                    // TODO: add name of the user
                    taskStatusBinding.buttonMessage.setOnClickListener { view -> openChat(view, taskId!!, authorId!!) }
                    taskStatusBinding.buttonEdit.layoutParams.width = 0
                    taskStatusBinding.buttonEdit.visibility = View.INVISIBLE
                    taskStatusBinding.currentStatusValue.text = "The task was completed by user"
                }
            }

            "rejected" -> {
                val authorId = getIntent().extras!!.getString("authorId")
                val taskId = getIntent().extras!!.getString("taskId")
                val taskStatusBinding: ActivityTaskStatusBinding =
                    ActivityTaskStatusBinding.inflate(layoutInflater)
                setContentView(taskStatusBinding.root)
                taskStatusBinding.deadlineDate.text = getIntent().extras!!.getString("deadlineDate")
                taskStatusBinding.taskName.text = getIntent().extras!!.getString("name")
                taskStatusBinding.taskDescriptionTextView.text =
                    getIntent().extras!!.getString("description")
                //taskStatusBinding.taskImageView.setImageURI( getIntent().extras!!.getString("imageUrl")!!.toUri())
                taskStatusBinding.coinsTextView.text = getIntent().extras!!.getString("coins")
                if (currentUserId == getIntent().extras!!.getString("authorId")) {
                    taskStatusBinding.buttonMessage.layoutParams.width = 0
                    taskStatusBinding.buttonMessage.visibility = View.INVISIBLE
                    taskStatusBinding.buttonEdit.setOnClickListener { view -> openEditTaskActivity(view, taskId!!) }
                    taskStatusBinding.currentStatusValue.text = "The task was rejected by you"
                } else {
                    // TODO: add name of the user
                    taskStatusBinding.buttonEdit.layoutParams.width = 0
                    taskStatusBinding.buttonEdit.visibility = View.INVISIBLE
                    taskStatusBinding.buttonMessage.setOnClickListener { view -> openChat(view, taskId!!, authorId!!) }
                    taskStatusBinding.currentStatusValue.text = "The task was completed by user"
                }
            }
        }
    }
}