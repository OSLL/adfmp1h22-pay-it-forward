package com.example.payitforward

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.payitforward.databinding.ActivityCreateDeedBinding
import com.example.payitforward.pojo.Task
import com.example.payitforward.util.FirestoreUtil
import com.example.payitforward.util.StorageUtil
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class CreateDeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateDeedBinding
    private var taskId: String? = null
    private lateinit var title: String
    private lateinit var description: String
    private var deadline: Timestamp? = null
    private var createdTime: Timestamp? = null
    private var photo: Uri? = null
    private var imagePath: String? = null
    private var coins: Int = 50
    private var edit: Boolean = false
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            binding.changePhoto.setImageURI(data?.data)
            photo = data?.data
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateDeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        taskId = intent.getStringExtra("taskId")
        if (taskId != null) {
            loadInfo(taskId!!)
        }

        binding.buttonCancel.setOnClickListener { cancelActivity() }

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            deadline = Timestamp(calendar.time)
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                coins = p1
                binding.coinsTextView.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        binding.buttonDone.setOnClickListener {
            title = binding.titleEditText.text.toString()
            description = binding.descriptionEditText.text.toString()
            addToDatabase()
        }

        binding.changePhoto.setOnClickListener {
            checkPermissionForImage()
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher.launch(intent)
        }
    }

    private fun loadInfo(id: String) {
        FirestoreUtil.getTask(id) { task ->
            if (task != null) {
                edit = true
                binding.titleEditText.setText(task.name)
                binding.descriptionEditText.setText(task.description)
                binding.coinsTextView.text = task.coins.toString()
                binding.seekBar.progress = task.coins
                binding.calendarView.date = task.deadlineDate.toDate().time
                createdTime = task.creationDate
                if (task.imageUrl != null) {
                    val photoRef = StorageUtil.pathToReference(task.imageUrl!!)
                    GlideApp.with(this).load(photoRef).into(binding.changePhoto)
                    imagePath = task.imageUrl
                }
            }
        }
    }

    private fun addToDatabase() {
        if (createdTime == null) {
            createdTime = Timestamp.now()
        }
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        if (taskId == null) {
            taskId = userId + createdTime!!.seconds
        }
        if (deadline == null) {
            deadline = createdTime
        }
        if (edit) {
            FirestoreUtil.deleteTask(taskId!!)
        }
        if (photo != null) {
            StorageUtil.uploadTaskImage(photo!!, taskId!!) { imagePath ->
                FirestoreUtil.addTask(Task(taskId!!, createdTime!!, deadline!!, null, userId, null, title, description, imagePath, coins, "new"))
            }
        } else {
            FirestoreUtil.addTask(Task(taskId!!, createdTime!!, deadline!!, null,  userId, null, title, description, imagePath, coins, "new"))
        }
        finish()
    }

    private fun cancelActivity() {
        finish()
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