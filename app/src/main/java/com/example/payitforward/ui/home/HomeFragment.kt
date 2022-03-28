package com.example.payitforward.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.payitforward.CreateDeedActivity
import com.example.payitforward.FilterActivity
import com.example.payitforward.ItemTaskActivity
import com.example.payitforward.R
import com.example.payitforward.adapters.TabsPagerAdapter
import com.example.payitforward.adapters.TasksAdapter
import com.example.payitforward.databinding.FragmentHomeBinding
import com.example.payitforward.pojo.Task
import com.example.payitforward.util.FirestoreUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var tasksList: List<Task> = ArrayList()
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        mView = binding.root

        val plusButton: FloatingActionButton = binding.fab
        plusButton.setOnClickListener {
            val intent = Intent(this.context, CreateDeedActivity::class.java)
            startActivity(intent)
        }
        binding.tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
        context?.let { ContextCompat.getColor(it, R.color.pink) }
            ?.let { binding.tabLayout.setBackgroundColor(it) }
        binding.tabLayout.tabTextColors =
            context?.let { ContextCompat.getColorStateList(it, android.R.color.white) }
        val numberOfTabs = 3
        binding.tabLayout.tabMode = TabLayout.MODE_FIXED
        val adapter = TabsPagerAdapter(childFragmentManager, lifecycle, numberOfTabs)
        binding.tabsViewpager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.tabsViewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "All"
                }
                1 -> {
                    tab.text = "Get"
                }
                2 -> {
                    tab.text = "Give"
                }
            }
        }.attach()
        binding.filterButtonHome.setOnClickListener {
            val intent = Intent(context, FilterActivity::class.java)
            startActivity(intent)
        }

        binding.sendButtonHome.setOnClickListener{
            // TODO: return tabLayout after search
            binding.tabLayout.visibility = View.GONE
            initRecyclerView()
            var searchText = binding.searchHome.text
            FirestoreUtil.searchTasks(searchText.toString()) { tasks ->
                tasksList = tasks
                tasksAdapter.setItems(tasksList)
            }
        }

        return mView
    }


    private fun initRecyclerView() {
        tasksRecyclerView = mView.findViewById(R.id.tasks_recycler_view)
        tasksRecyclerView.layoutManager = LinearLayoutManager(mView.context)
        tasksAdapter = TasksAdapter()
        tasksAdapter.setOnTaskClickListener(object : TasksAdapter.onTaskClickListener {
            override fun onTaskClick(position: Int) {
                val intent = Intent(view!!.context, ItemTaskActivity::class.java)
                when (tasksList[position].type) {
                    "new" -> {
                        putExtraData(intent, position)
                    }
                    "completed" -> {
                        putExtraData(intent, position)
                    }
                    "onReview" -> {
                        putExtraData(intent, position)
                    }
                }
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
        intent.putExtra("completedId", tasksList[position].completedId)
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