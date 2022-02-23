package com.example.payitforward

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.payitforward.adapters.TasksAdapter
import com.example.payitforward.databinding.ActivityStatisticsBinding
import com.example.payitforward.pojo.Task
import com.example.payitforward.pojo.User
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.database.FirebaseDatabase


class StatisticsActivity : AppCompatActivity() {

    data class Score(
        val name:String,
        val score: Int,
    )

    private var scoreList = ArrayList<Score>()

    var tasksList: MutableList<Task> = java.util.ArrayList()
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var binding: ActivityStatisticsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initSelector()

        scoreList = getScoreList()

        initBarChart()

        val entries: ArrayList<BarEntry> = ArrayList()

        for (i in scoreList.indices) {
            val score = scoreList[i]
            entries.add(BarEntry(i.toFloat(), score.score.toFloat()))
        }

        val barDataSet = BarDataSet(entries, "")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data = BarData(barDataSet)
        binding.barChart.data = data

        binding.barChart.invalidate()

        initRecyclerView()
        loadTasks()
    }

    private fun initSelector() {

        binding.apply {
            var lastTime = Week

            Day.setOnClickListener {
                sep1.visibility = View.GONE
                sep2.visibility = View.VISIBLE
                sep3.visibility = View.VISIBLE
                Day.background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_button_pink, null)
                lastTime.background = null
                lastTime = Day
            }

            Week.setOnClickListener {
                sep1.visibility = View.GONE
                sep2.visibility = View.GONE
                sep3.visibility = View.VISIBLE
                Week.background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_button_pink, null)
                lastTime.background = null
                lastTime = Week
            }

            Month.setOnClickListener {
                sep1.visibility = View.VISIBLE
                sep2.visibility = View.GONE
                sep3.visibility = View.GONE
                Month.background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_button_pink, null)
                lastTime.background = null
                lastTime = Month
            }

            Year.setOnClickListener {
                sep1.visibility = View.VISIBLE
                sep2.visibility = View.VISIBLE
                sep3.visibility = View.GONE
                Year.background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_button_pink, null)
                lastTime.background = null
                lastTime = Year
            }
        }

    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return if (index < scoreList.size) {
                scoreList[index].name
            } else {
                ""
            }
        }
    }

    private fun initBarChart() {
        binding.barChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = binding.barChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        binding.barChart.axisRight.isEnabled = false
        binding.barChart.legend.isEnabled = false
        binding.barChart.description.isEnabled = false

        binding.barChart.animateY(3000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
    }

    private fun getScoreList(): ArrayList<Score> {
        scoreList.add(Score("Mon", 56))
        scoreList.add(Score("Tue", 75))
        scoreList.add(Score("Web", 85))
        scoreList.add(Score("Thu", 45))
        scoreList.add(Score("Fri", 63))
        scoreList.add(Score("Sat", 13))
        scoreList.add(Score("Sun", 73))

        return scoreList
    }

    private fun loadTasks() {
        tasksList = getTasks()
        tasksAdapter.setItems(tasksList)
    }

    private fun getTasks(): MutableList<Task> {
        var lst: MutableList<Task> = java.util.ArrayList<Task>()
        for (i in 1..10) {
            lst.add(
                Task(
                    i.toLong(),
                    "21.02.2022",
                    "25.02.2022",
                    User(i.toLong(), "Maria", ""),
                    "Buy cake",
                    "Desiption",
                    "",
                    1,
                    1
                )
            )
        }
        return lst
    }

    private fun initRecyclerView() {
        binding.history.layoutManager = LinearLayoutManager(this)
        tasksAdapter = TasksAdapter()
        tasksAdapter.setOnTaskClickListener(object : TasksAdapter.onTaskClickListener{
            override fun onTaskClick(position: Int) {
                //Toast.makeText(this, "You clicked on item on $position", Toast.LENGTH_LONG).show()
            }

        })
        binding.history.adapter = tasksAdapter
    }

}


