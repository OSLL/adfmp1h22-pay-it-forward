package com.example.payitforward

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.payitforward.adapters.DialogsAdapter
import com.example.payitforward.databinding.ActivityChatBinding
import com.example.payitforward.pojo.Dialog
import com.example.payitforward.pojo.Message
import com.example.payitforward.pojo.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    private lateinit var dialogsAdapter: DialogsAdapter
    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initRecyclerView()
        getDialogs()
    }

    private fun getDialogs() {
        val db = Firebase.firestore
        db.collection("dialog")
            .whereEqualTo("ownerId", "1")
            .get()
            .addOnSuccessListener { snapshots ->
                val dialogs: List<Dialog> = snapshots.toObjects(Dialog::class.java)
                dialogsAdapter.setItems(dialogs)
            }
        db.collection("dialog")
            .whereEqualTo("candidateId", "1")
            .get()
            .addOnSuccessListener { snapshots ->
                val dialogs: List<Dialog> = snapshots.toObjects(Dialog::class.java)
                dialogsAdapter.setItems(dialogs)
            }
    }

    private fun initRecyclerView() {
        binding.listDialogs.layoutManager = LinearLayoutManager(this)
        dialogsAdapter = DialogsAdapter()
        dialogsAdapter.setOnDialogClickListener(object : DialogsAdapter.onDialogClickListener{
            override fun onDialogClick(position: Int) {
                val intent = Intent(this@ChatActivity, DialogActivity::class.java)
                intent.putExtra("dialogId", "1_5")
                startActivity(intent)
            }

        })
        binding.listDialogs.adapter = dialogsAdapter
    }
}