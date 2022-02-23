package com.example.payitforward

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.payitforward.adapters.MessageAdapter
import com.example.payitforward.databinding.ActivityDialogBinding
import com.example.payitforward.pojo.Message
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class DialogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDialogBinding
    lateinit var adapter: MessageAdapter

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

//        binding.headerTitle.text = intent.getStringExtra("title")

        binding.buttonMenu.setOnClickListener {
            finish()
        }

        initRcView()

        val dialogId = intent.getStringExtra("dialogId")

        val formatter = SimpleDateFormat("HH:mm")

        val db = Firebase.firestore
        val collections = db.collection("message")
        binding.sendButton.setOnClickListener {
            val time = Timestamp.now()
            collections.add(Message(binding.messageEditText.text.toString(), time, "123", "567", dialogId).toMap())
        }
        collections.whereEqualTo("dialogId", dialogId).addSnapshotListener { snapshots, e ->
            if (snapshots != null) {
                val messages: List<Message> = snapshots.toObjects(Message::class.java)
                adapter.submitList(messages.sortedBy { it.time })
            }
        }
    }

    private fun initRcView() = with(binding) {
        adapter = MessageAdapter()
        messageRecyclerView.layoutManager = LinearLayoutManager(this@DialogActivity)
        messageRecyclerView.adapter = adapter
    }
}