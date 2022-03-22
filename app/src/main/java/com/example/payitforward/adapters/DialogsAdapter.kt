package com.example.payitforward.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.payitforward.databinding.ItemDialogBinding
import com.example.payitforward.pojo.Dialog
import com.example.payitforward.pojo.TextMessage
import com.example.payitforward.util.FirestoreUtil
import java.text.SimpleDateFormat


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

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(dialogs: Collection<Dialog>) {
        dialogsList.addAll(0, dialogs)
        notifyDataSetChanged()
    }

    class DialogViewHolder(
        val binding: ItemDialogBinding, listener: onDialogClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        fun bind(dialog: Dialog) {
            FirestoreUtil.getLastMessage(dialog.id) { message ->
                if (message != null) {
                    val sfd = SimpleDateFormat("HH:mm")
                    binding.timeTextView.text = sfd.format(message.time.toDate())
                    if (message is TextMessage) {
                        binding.messageTextView.text = message.text
                    } else {
                        binding.messageTextView.text = "photo"
                    }
                }

            }
            binding.dialogName.text = "Dialog Name"
        }

        init {
            binding.root.setOnClickListener{
                listener.onDialogClick(bindingAdapterPosition)
            }
        }
    }
}