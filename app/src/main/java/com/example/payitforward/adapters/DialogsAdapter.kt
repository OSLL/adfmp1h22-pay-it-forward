package com.example.payitforward.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.payitforward.databinding.ItemDialogBinding
import com.example.payitforward.pojo.Dialog
import java.util.ArrayList

class DialogsAdapter : RecyclerView.Adapter<DialogsAdapter.DialogViewHolder>() {
    
    private var dialogsList: MutableList<Dialog> = ArrayList()
    private lateinit var mClickListener: onDialogClickListener


    interface onDialogClickListener {
        fun onDialogClick(position: Int)
    }

    fun setOnDialogClickListener(listener: onDialogClickListener) {
        mClickListener = listener
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        val binding = ItemDialogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DialogViewHolder(binding, mClickListener)
    }

    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
        holder.bind(dialogsList[position])
    }

    override fun getItemCount() = dialogsList.size

    fun setItems(dialogs: Collection<Dialog>) {
        dialogsList.addAll(0, dialogs)
        notifyDataSetChanged()
    }

    class DialogViewHolder(
        val binding: ItemDialogBinding, listener: onDialogClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dialog: Dialog) {
            // binding.dialogImageView
            binding.dialogName.text = dialog.title
            binding.nameTextView.text = dialog.author.name + ": "
            binding.timeTextView.text = dialog.time
            binding.messageTextView.text = dialog.message
        }

        init {
            binding.root.setOnClickListener{
                listener.onDialogClick(bindingAdapterPosition)
            }
        }
    }
}