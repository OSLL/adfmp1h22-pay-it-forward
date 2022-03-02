package com.example.payitforward

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.payitforward.databinding.ActivityTaskAcceptRejectBinding
import com.example.payitforward.databinding.ActivityTaskTakeBinding
import com.example.payitforward.databinding.ActivityTaskDoneBinding


class ItemTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val inputTaskType = intent.getStringExtra("taskType")

        if (inputTaskType == "accept_reject") {
            var acceptRejectBinding: ActivityTaskAcceptRejectBinding =
                ActivityTaskAcceptRejectBinding.inflate(layoutInflater)
            setContentView(acceptRejectBinding.root)
        } else if (inputTaskType == "take") {
            var takeBinding: ActivityTaskTakeBinding =
                ActivityTaskTakeBinding.inflate(layoutInflater)
            setContentView(takeBinding.root)
        }  else if (inputTaskType == "done") {
            var doneBinding: ActivityTaskDoneBinding =
                ActivityTaskDoneBinding.inflate(layoutInflater)
            setContentView(doneBinding.root)
        }

    }
}