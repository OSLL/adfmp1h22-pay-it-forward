package com.example.payitforward.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.payitforward.FireMessageGlideModule
import com.example.payitforward.GlideApp
import com.example.payitforward.databinding.ItemImageMessageBinding
import com.example.payitforward.databinding.ItemImageYourMessageBinding
import com.example.payitforward.databinding.ItemMessageBinding
import com.example.payitforward.databinding.ItemYourMessageBinding
import com.example.payitforward.pojo.ImageMessage
import com.example.payitforward.pojo.Message
import com.example.payitforward.pojo.MessageType
import com.example.payitforward.pojo.TextMessage
import com.example.payitforward.util.StorageUtil
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat


class MessageAdapter : ListAdapter<Message, MessageAdapter.ItemHolder>(ItemComparator()) {

    private val MSG_TYPE_LEFT = 0
    private val MSG_TYPE_RIGHT = 1
    private val IMG_TYPE_RIGHT = 2
    private val IMG_TYPE_LEFT = 3

    sealed class ItemHolder(val bind: ViewBinding) : RecyclerView.ViewHolder(bind.root) {

        class RightItemHolder(private val binding: ItemYourMessageBinding) : ItemHolder(binding) {
            @SuppressLint("SimpleDateFormat")
            fun bind(message: TextMessage) = with(binding) {
                messageTextView.text = message.text
                val sfd = SimpleDateFormat("HH:mm")
                messageTime.text = sfd.format(message.time.toDate())
            }
        }

        class LeftItemHolder(private val binding: ItemMessageBinding) : ItemHolder(binding) {
            @SuppressLint("SimpleDateFormat")
            fun bind(message: TextMessage) = with(binding) {
                messageTextView.text = message.text
                val sfd = SimpleDateFormat("HH:mm")
                messageTime.text = sfd.format(message.time.toDate())
            }
        }

        class RightImageHolder(private val binding: ItemImageYourMessageBinding,
                               private val context: Context) : ItemHolder(binding) {
            fun bind(message: ImageMessage) = with(binding) {
                val photoRef = StorageUtil.pathToReference(message.image)
                GlideApp.with(context).load(photoRef).into(imageView)
            }
        }

        class LeftImageHolder(private val binding: ItemImageMessageBinding,
                              private val context: Context) : ItemHolder(binding) {
            fun bind(message: ImageMessage) = with(binding) {
                val photoRef = StorageUtil.pathToReference(message.image)
                GlideApp.with(context).load(photoRef).into(imageView)
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        if (viewType == MSG_TYPE_RIGHT) {
            return ItemHolder.RightItemHolder(ItemYourMessageBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
        } else if (viewType == MSG_TYPE_LEFT) {
            return ItemHolder.LeftItemHolder(ItemMessageBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
        } else if (viewType == IMG_TYPE_RIGHT) {
            return ItemHolder.RightImageHolder(ItemImageYourMessageBinding
                .inflate(LayoutInflater.from(parent.context), parent, false), parent.context)
        } else {
            return ItemHolder.LeftImageHolder(ItemImageMessageBinding
                .inflate(LayoutInflater.from(parent.context), parent, false), parent.context)
        }
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        when(holder) {
            is ItemHolder.RightItemHolder -> holder.bind(getItem(position) as TextMessage)
            is ItemHolder.LeftItemHolder -> holder.bind(getItem(position) as TextMessage)
            is ItemHolder.RightImageHolder -> holder.bind(getItem(position) as ImageMessage)
            is ItemHolder.LeftImageHolder -> holder.bind(getItem(position) as ImageMessage)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        return if (getItem(position).senderId == userId){
            if (getItem(position).type == MessageType.IMAGE) {
                IMG_TYPE_RIGHT
            } else {
                MSG_TYPE_RIGHT
            }
        } else {
            if (getItem(position).type == MessageType.IMAGE) {
                IMG_TYPE_LEFT
            } else {
                MSG_TYPE_LEFT
            }
        }
    }

}