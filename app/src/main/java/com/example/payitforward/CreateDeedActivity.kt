package com.example.payitforward


import android.os.Build
import android.os.Bundle
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.payitforward.databinding.ActivityCreateDeedBinding
import com.example.payitforward.pojo.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class CreateDeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateDeedBinding
    private lateinit var title: String
    private lateinit var description: String
    private lateinit var deadline: Timestamp
    private var coins: Int = 5

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
    }

    private fun addToDatabase() {
        val db = Firebase.firestore
        val collections = db.collection("task")
        val time = Timestamp.now()
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val userId: String = preferences.getString("user_id", "") ?: ""
        val taskId = userId + time.seconds
        collections.add(Task(taskId, time, deadline, userId, title, description, null, coins, 0))
        finish()
    }

    private fun cancelActivity() {
        finish()
    }
}