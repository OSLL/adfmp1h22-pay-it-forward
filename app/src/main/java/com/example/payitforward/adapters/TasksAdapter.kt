package com.example.payitforward.adapters

import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.payitforward.FragmentHomeAll
import com.example.payitforward.GlideApp
import com.example.payitforward.pojo.Task
import java.util.*
import com.example.payitforward.databinding.ItemTaskBinding
import com.example.payitforward.util.StorageUtil
import java.text.SimpleDateFormat


class TasksAdapter(context: Context?) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    private var tasksList: MutableList<Task> = ArrayList()
    private lateinit var mClickListener: onTaskClickListener
    private var mContext: Context? = context


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
        holder.bind(tasksList[position], mContext)
    }

    override fun getItemCount() = tasksList.size

    fun setItems(tasks: Collection<Task>) {
        tasksList.clear()
        tasksList.addAll(0, tasks)
        notifyDataSetChanged()
    }

    class TaskViewHolder(
        val binding: ItemTaskBinding, listener: onTaskClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task,  context: Context?) {
         //   binding.taskImageView
            val sfd = SimpleDateFormat("HH:mm")
            binding.taskName.text = task.name
            binding.deadlineDate.text = getDate(task.deadlineDate.seconds)
            binding.coinsTextView.text = task.coins.toString()
            if (task.imageUrl != null) {
                val photoRef = StorageUtil.pathToReference(task.imageUrl.toString())
                if (context != null) {
                    GlideApp.with(context).load(photoRef).into(binding.taskImageView)
                }
            }
        }

        fun getDate(timestamp: Long): String {
            val calendar = Calendar.getInstance(Locale.ENGLISH)
            calendar.timeInMillis = timestamp * 1000L
            val date = DateFormat.format("dd-MM-yyyy", calendar).toString()
            return date
        }

        init {
            binding.root.setOnClickListener{
                listener.onTaskClick(bindingAdapterPosition)
            }
        }
    }


}
