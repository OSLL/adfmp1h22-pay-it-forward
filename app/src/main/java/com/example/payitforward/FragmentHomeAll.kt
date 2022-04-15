package com.example.payitforward


import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.payitforward.adapters.TasksAdapter
import com.example.payitforward.pojo.Task
import com.example.payitforward.pojo.User
import com.example.payitforward.util.FirestoreUtil
import com.google.firebase.Timestamp
import java.util.*


class FragmentHomeAll : Fragment() {
    private var tasksList: List<Task> = ArrayList()
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
        FirestoreUtil.getAllTasks { tasks ->
            tasksList = tasks
            tasksAdapter.setItems(tasksList)
        }
    }

    private fun initRecyclerView() {
        tasksRecyclerView = mView.findViewById(R.id.tasks_recycler_view)
        tasksRecyclerView.layoutManager = LinearLayoutManager(mView.context)
        tasksAdapter = TasksAdapter()
        tasksAdapter.setOnTaskClickListener(object : TasksAdapter.onTaskClickListener {
            override fun onTaskClick(position: Int) {
                val intent = Intent(view!!.context, ItemTaskActivity::class.java)
                putExtraData(intent, position)
                startActivity(intent)
            }

        })
        tasksRecyclerView.adapter = tasksAdapter

    }

    private fun putExtraData(intent: Intent, position: Int) {
        intent.putExtra("taskId", tasksList[position].id)
        intent.putExtra("creationDate", getDate(tasksList[position].creationDate.seconds))
        intent.putExtra("deadlineDate", getDate(tasksList[position].deadlineDate.seconds))
        intent.putExtra("authorId", tasksList[position].authorId)
        intent.putExtra("executorId", tasksList[position].executorId)
        intent.putExtra("name", tasksList[position].name)
        intent.putExtra("description", tasksList[position].description)
        intent.putExtra("imageUrl", tasksList[position].imageUrl)
        intent.putExtra("coins", tasksList[position].coins.toString())
        intent.putExtra("taskType", tasksList[position].type)
    }


    fun getDate(timestamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        val date = DateFormat.format("dd-MM-yyyy", calendar).toString()
        return date
    }

}