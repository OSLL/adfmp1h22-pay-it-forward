package com.example.payitforward.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.payitforward.databinding.ItemDialogBinding
import com.example.payitforward.pojo.Dialog
import com.example.payitforward.pojo.Message
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

    fun updateItem(position: Int) {
        dialogsList[position]
    }

    class DialogViewHolder(
        val binding: ItemDialogBinding, listener: onDialogClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SimpleDateFormat")
        fun bind(dialog: Dialog) {
            val db = Firebase.firestore
            db.collection("message")
                .whereEqualTo("dialogId", dialog.id)
                .addSnapshotListener { snapshots, e ->
                    if (snapshots != null) {
                        val messages: List<Message> = snapshots.toObjects(Message::class.java)
                        val message = messages.maxByOrNull { it.time ?: Timestamp.now() }
                        if (message != null) {
                            val sfd = SimpleDateFormat("HH:mm")
                            binding.timeTextView.text = sfd.format(message.time!!.toDate())
                            binding.messageTextView.text = message.text
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