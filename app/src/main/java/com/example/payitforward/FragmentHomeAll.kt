package com.example.payitforward


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.payitforward.adapters.TasksAdapter
import com.example.payitforward.pojo.Dialog
import com.example.payitforward.pojo.Task
import com.example.payitforward.pojo.User
import com.example.payitforward.ui.chat.DialogActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class FragmentHomeAll : Fragment() {
    var tasksList: MutableList<Task> = ArrayList()
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var mView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_home_all, container, false)
        initRecyclerView()
        loadTasks()
        return mView
    }

    private fun loadTasks() {
        tasksList = getTasks()
        tasksAdapter.setItems(tasksList)
    }

    private fun getTasks(): MutableList<Task> {
        var lst: MutableList<Task> = ArrayList<Task>()
        for (i in 1..10) {
            lst.add(
                Task(
                    i.toLong(),
                    "21.02.2022",
                    "25.02.2022",
                    User(i.toLong(), "Maria", ""),
                    "Buy cake",
                    "Desiption",
                    "",
                    1,
                    i % 2
                )
            )
        }
        return lst
    }

    private fun initRecyclerView() {
        tasksRecyclerView = mView.findViewById(R.id.tasks_recycler_view)
        tasksRecyclerView.layoutManager = LinearLayoutManager(mView.context)
        tasksAdapter = TasksAdapter()
        tasksAdapter.setOnTaskClickListener(object : TasksAdapter.onTaskClickListener{
            override fun onTaskClick(position: Int) {
                val intent = Intent(view!!.context, ItemTaskActivity::class.java)
                if (tasksList[position].type == 0) {
                    intent.putExtra("taskType", "take")
                } else if (tasksList[position].type == 1)  {
                    intent.putExtra("taskType", "done")
                } else if (tasksList[position].type == 2) {
                    intent.putExtra("taskType", "accept_reject")
                }
                startActivity(intent)

                /*val db = Firebase.firestore
                val collections = db.collection("dialog")
                collections
                    .whereEqualTo("candidateId", "1")
                    .whereEqualTo("taskId", "5")
                    .get()
                    .addOnSuccessListener { documents ->
                        val id: String?
                        if (documents.isEmpty ) {
                            id = "1" + "_" + "5"
                            collections.add(Dialog(id, "2", "1", "5"))
                        } else {
                            id = documents.toObjects(Dialog::class.java)[0].id
                        }
                        val intent = Intent(view!!.context, DialogActivity::class.java)
                        intent.putExtra("dialogId", id)
                        startActivity(intent)
                    }*/
            }

        })
        tasksRecyclerView.adapter = tasksAdapter
    }

}