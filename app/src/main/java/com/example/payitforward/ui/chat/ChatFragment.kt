package com.example.payitforward.ui.chat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.payitforward.adapters.DialogsAdapter
import com.example.payitforward.databinding.FragmentChatBinding
import com.example.payitforward.pojo.Dialog
import com.example.payitforward.util.FirestoreUtil
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var dialogsAdapter: DialogsAdapter
    private var dialogs: List<Dialog> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        try {
            binding = FragmentChatBinding.inflate(inflater, container, false)
        } catch (e: Exception) {
            throw e
        }
        val root: View = binding.root

        initRecyclerView()
        getDialogs()

        binding.sendButton.setOnClickListener {
            val t = binding.searchText.text.toString()
            val ans = mutableListOf<Dialog>()
            val titles = mutableMapOf<Int, String>()
            var flag = 0
            for((i, dialog) in dialogs.withIndex()) {
                FirestoreUtil.getTask(dialog.taskId) { task ->
                    flag += 1
                    if (task != null) {
                        titles[i] = task.name
                    }
                    if (flag == dialogs.size) {
                        for ((key, value) in titles) {
                            if (value.contains(t, ignoreCase = true)) {
                                ans.add(dialogs[key])
                            }
                        }
                        dialogsAdapter.clearItems()
                        dialogsAdapter.setItems(ans)
                        hideKeyboard(requireActivity())
                    }
                }
            }
        }

        binding.search.setEndIconOnClickListener {
            binding.searchText.text?.clear()
            hideKeyboard(requireActivity())
            dialogsAdapter.clearItems()
            dialogsAdapter.setItems(dialogs)
        }

        return root
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getDialogs() {
        FirestoreUtil.getDialogsOwner { dialogs ->
            FirestoreUtil.getDialogsCandidate { dialogs2 ->
                val d = dialogs + dialogs2
                val times = mutableMapOf<String, Timestamp>()
                var flag = 0
                for (i in d) {
                    FirestoreUtil.getLastMessage(i.id) { message ->
                        flag += 1
                        if (message != null) {
                            times[i.id] = message.time
                        }
                        if (flag == d.size) {
                            d.sortedBy { times[it.id] }
                            dialogsAdapter.setItems(d)
                            this.dialogs = d
                        }
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.listDialogs.layoutManager = LinearLayoutManager(this.context)
        dialogsAdapter = DialogsAdapter()
        dialogsAdapter.setOnDialogClickListener(object : DialogsAdapter.onDialogClickListener{
            override fun onDialogClick(position: Int) {
                val intent = Intent(this@ChatFragment.context, DialogActivity::class.java)
                val dialog = dialogsAdapter.getDialog(position)
                val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
                val receiverId = if (dialog.ownerId != currentUserId) {
                    dialog.ownerId
                } else {
                    dialog.candidateId
                }
                val dialogId = dialog.candidateId + "_" + dialog.taskId
                intent.putExtra("dialogId", dialogId)
                intent.putExtra("receiverId", receiverId)
                FirestoreUtil.getTask(dialog.taskId) { task ->
                    if (task != null) {
                        intent.putExtra("title", task.name)
                        startActivity(intent)
                    }
                }
            }

        })
        binding.listDialogs.adapter = dialogsAdapter
    }
}