package com.example.payitforward.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.payitforward.pojo.Task
import java.util.*
import com.example.payitforward.databinding.ItemTaskBinding
import java.text.SimpleDateFormat


class TasksAdapter : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    private var tasksList: MutableList<Task> = ArrayList()
    private lateinit var mClickListener: onTaskClickListener


    interface onTaskClickListener {
        fun onTaskClick(position: Int)
    }

    fun setOnTaskClickListener(listener: onTaskClickListener) {
        mClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding, mClickListener)
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
        val binding: ItemTaskBinding, listener: onTaskClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
         //   binding.taskImageView
            val sfd = SimpleDateFormat("HH:mm")
            binding.taskName.text = task.name
            binding.deadlineDate.text = sfd.format(task.deadlineDate.toDate())
            binding.coinsTextView.text = task.coins.toString()
        }

        init {
            binding.root.setOnClickListener{
                listener.onTaskClick(bindingAdapterPosition)
            }
        }
    }
}
