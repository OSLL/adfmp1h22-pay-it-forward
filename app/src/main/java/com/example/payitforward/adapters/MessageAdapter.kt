package com.example.payitforward.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.payitforward.databinding.ItemMessageBinding
import com.example.payitforward.databinding.ItemYourMessageBinding
import com.example.payitforward.pojo.Message
import java.text.SimpleDateFormat


class MessageAdapter : ListAdapter<Message, MessageAdapter.ItemHolder>(ItemComparator()) {

    private val MSG_TYPE_LEFT = 0
    private val MSG_TYPE_RIGHT = 1
    private val IMG_TYPE_RIGHT = 2
    private val IMG_TYPE_LEFT = 3

    sealed class ItemHolder(val bind: ViewBinding) : RecyclerView.ViewHolder(bind.root) {

        class RightItemHolder(private val binding: ItemYourMessageBinding) : ItemHolder(binding) {
            @SuppressLint("SimpleDateFormat")
            fun bind(message: Message) = with(binding) {
                messageTextView.text = message.text
                val sfd = SimpleDateFormat("HH:mm")
                messageTime.text = sfd.format(message.time!!.toDate())
            }
        }

        class LeftItemHolder(private val binding: ItemMessageBinding) : ItemHolder(binding) {
            @SuppressLint("SimpleDateFormat")
            fun bind(message: Message) = with(binding) {
                messageTextView.text = message.text
                val sfd = SimpleDateFormat("HH:mm")
                messageTime.text = sfd.format(message.time!!.toDate())
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        if (viewType == MSG_TYPE_RIGHT) {
            return ItemHolder.RightItemHolder(ItemYourMessageBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            return ItemHolder.LeftItemHolder(ItemMessageBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        when(holder) {
            is ItemHolder.RightItemHolder -> holder.bind(getItem(position))
            is ItemHolder.LeftItemHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        val userId = "123"
        return if (getItem(position).senderId == userId){
            MSG_TYPE_RIGHT;
        } else {
            MSG_TYPE_LEFT;
        }
    }

}