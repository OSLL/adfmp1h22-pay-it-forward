package com.example.payitforward

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.payitforward.adapters.TasksAdapter
import com.example.payitforward.pojo.Task
import com.example.payitforward.pojo.User
import com.example.payitforward.util.FirestoreUtil
import com.example.payitforward.util.StorageUtil
import com.google.firebase.Timestamp
import java.util.ArrayList

class FragmentHomeGet : Fragment() {

    private var tasksList: List<Task> = ArrayList()
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var mView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_home_get, container, false)
        initRecyclerView()
        loadTasks()
        return mView
    }

    private fun loadTasks() {
        var userId: String = FirestoreUtil.getCurrentUser()
        FirestoreUtil.getTasksForGet(userId) { tasks ->
            tasksList = tasks
            tasksAdapter.setItems(tasksList)
        }
    }

    private fun initRecyclerView() {
        tasksRecyclerView = mView.findViewById(R.id.tasks_recycler_view)
        tasksRecyclerView.layoutManager = LinearLayoutManager(mView.context)
        tasksAdapter = TasksAdapter()
        tasksAdapter.setOnTaskClickListener(object : TasksAdapter.onTaskClickListener{
            override fun onTaskClick(position: Int) {
                val intent = Intent(view!!.context, ItemTaskActivity::class.java)
                intent.putExtra("taskType", "new")
                startActivity(intent)
            }

        })
        tasksRecyclerView.adapter = tasksAdapter
    }

}