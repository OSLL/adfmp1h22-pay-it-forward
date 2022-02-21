package com.example.payitforward


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.payitforward.adapters.TasksAdapter
import com.example.payitforward.pojo.Task
import com.example.payitforward.pojo.User
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
                    1
                )
            )
        }
        return lst
    }

    private fun initRecyclerView() {
        tasksRecyclerView = mView.findViewById(R.id.tasks_recycler_view)
        tasksRecyclerView.layoutManager = LinearLayoutManager(mView.context)
        tasksAdapter = TasksAdapter()
        tasksRecyclerView.adapter = tasksAdapter
    }

}