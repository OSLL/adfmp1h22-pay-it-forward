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
    private lateinit var title: String
    private lateinit var description: String
    private var deadline: Timestamp? = null
    private var photo: Uri? = null
    private var coins: Int = 50
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
        binding.buttonCancel.setOnClickListener { cancelActivity() }

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            deadline = Timestamp(calendar.time)
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                coins = p1
                binding.cointsTextView.text = p1.toString()
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

    private fun addToDatabase() {
        val time = Timestamp.now()
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val taskId = userId + time.seconds
        if (deadline == null) {
            deadline = time
        }
        FirestoreUtil.addTask(Task(taskId, time, deadline!!, userId, title, description, null, coins, 0))
        if (photo != null) {
            StorageUtil.uploadTaskImage(photo!!, taskId)
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