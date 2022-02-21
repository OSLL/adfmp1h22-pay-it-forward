package com.example.payitforward.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.payitforward.pojo.Task
import java.util.*
import com.example.payitforward.databinding.ItemTaskBinding



class TasksAdapter : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    private var tasksList: MutableList<Task> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasksList[position])
    }

    override fun getItemCount() = tasksList.size

    fun setItems(tasks: Collection<Task>) {
        tasksList.addAll(0, tasks)
        notifyDataSetChanged()
    }

    class TaskViewHolder(
        val binding: ItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
         //   binding.taskImageView
            binding.taskName.text = task.name
            binding.deadlineDate.text = task.deadlineDate
            binding.coinsTextView.text = task.coins.toString()
        }
    }
}
