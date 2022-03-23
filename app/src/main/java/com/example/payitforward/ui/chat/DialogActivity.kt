package com.example.payitforward.ui.chat

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.payitforward.adapters.MessageAdapter
import com.example.payitforward.databinding.ActivityDialogBinding
import com.example.payitforward.pojo.ImageMessage
import com.example.payitforward.pojo.TextMessage
import com.example.payitforward.util.FirestoreUtil
import com.example.payitforward.util.StorageUtil
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth


class DialogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDialogBinding
    private lateinit var adapter: MessageAdapter
    private lateinit var dialogId: String
    private val userId = FirebaseAuth.getInstance().currentUser!!.uid
    private lateinit var receiverId: String
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val photo = data?.data
            if (photo != null) {
                StorageUtil.uploadMessageImage(photo) { imagePath ->
                    val time = Timestamp.now()
                    val message = ImageMessage(imagePath, time, userId, receiverId, dialogId)
                    FirestoreUtil.sendMessage(message)
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.buttonMenu.setOnClickListener {
            finish()
        }

        initRcView()

        dialogId = intent.getStringExtra("dialogId") ?: "1"
        receiverId = intent.getStringExtra("receiverId") ?: "1"
        binding.headerTitle.text = intent.getStringExtra("title")

        FirestoreUtil.getMessages(dialogId) { messages ->
            adapter.submitList(messages.sortedBy { it.time })
            binding.messageRecyclerView.smoothScrollToPosition(adapter.itemCount)
        }

        binding.sendButton.setOnClickListener {
            val time = Timestamp.now()
            val message = TextMessage(binding.messageEditText.text.toString(), time, userId, receiverId, dialogId)
            FirestoreUtil.sendMessage(message)
            binding.messageEditText.text.clear()
        }

        binding.addMessageImageView.setOnClickListener {
            checkPermissionForImage()
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher.launch(intent)
        }
    }

    private fun initRcView() = with(binding) {
        adapter = MessageAdapter()
        messageRecyclerView.layoutManager = LinearLayoutManager(this@DialogActivity)
        messageRecyclerView.adapter = adapter
    }

    private fun checkPermissionForImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                && (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            ) {
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                val permissionCoarse = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

                requestPermissions(permission, 1001)
                requestPermissions(permissionCoarse, 1002)
            }
        }
    }
}