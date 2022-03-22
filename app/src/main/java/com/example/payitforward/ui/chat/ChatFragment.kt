package com.example.payitforward.ui.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.payitforward.adapters.DialogsAdapter
import com.example.payitforward.databinding.FragmentChatBinding
import com.example.payitforward.util.FirestoreUtil

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var dialogsAdapter: DialogsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initRecyclerView()
        getDialogs()
        return root
    }

    private fun getDialogs() {
        FirestoreUtil.getDialogsOwner { dialogs ->
            dialogsAdapter.setItems(dialogs)
        }
        FirestoreUtil.getDialogsCandidate { dialogs ->
            dialogsAdapter.setItems(dialogs)
        }
    }

    private fun initRecyclerView() {
        binding.listDialogs.layoutManager = LinearLayoutManager(this.context)
        dialogsAdapter = DialogsAdapter()
        dialogsAdapter.setOnDialogClickListener(object : DialogsAdapter.onDialogClickListener{
            override fun onDialogClick(position: Int) {
                val intent = Intent(this@ChatFragment.context, DialogActivity::class.java)
                intent.putExtra("dialogId", "1_5")
                startActivity(intent)
            }

        })
        binding.listDialogs.adapter = dialogsAdapter
    }
}