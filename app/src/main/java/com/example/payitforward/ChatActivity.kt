package com.example.payitforward

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.payitforward.adapters.DialogsAdapter
import com.example.payitforward.pojo.Dialog
import com.example.payitforward.pojo.User

class ChatActivity : AppCompatActivity() {

    var dialogsList: MutableList<Dialog> = java.util.ArrayList()
    private lateinit var dialogsAdapter: DialogsAdapter
    private lateinit var dialogsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        supportActionBar?.hide()

        initRecyclerView()
        loadTasks()
    }

    private fun loadTasks() {
        dialogsList = getDialogs()
        dialogsAdapter.setItems(dialogsList)
    }

    private fun getDialogs(): MutableList<Dialog> {
        var lst: MutableList<Dialog> = java.util.ArrayList<Dialog>()
        for (i in 1..20) {
            lst.add(
                Dialog(
                    i.toLong(),
                    "21.02.2022",
                    User(i.toLong(), "Vasiliy", ""),
                    "Buy cake and deliver",
                    "Good Job!",
                    ""
                )
            )
        }
        return lst
    }

    private fun initRecyclerView() {
        dialogsRecyclerView = findViewById(R.id.list_dialogs)
        dialogsRecyclerView.layoutManager = LinearLayoutManager(this)
        dialogsAdapter = DialogsAdapter()
        dialogsAdapter.setOnDialogClickListener(object : DialogsAdapter.onDialogClickListener{
            override fun onDialogClick(position: Int) {
                val intent = Intent(this@ChatActivity, DialogActivity::class.java)
                intent.putExtra("title", dialogsList[position].title)
                startActivity(intent)
            }

        })
        dialogsRecyclerView.adapter = dialogsAdapter
    }
}