package com.example.payitforward.ui.statistics

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.payitforward.ItemTaskActivity
import com.example.payitforward.R
import com.example.payitforward.adapters.TasksAdapter
import com.example.payitforward.databinding.FragmentStatisticsBinding
import com.example.payitforward.pojo.Task
import com.example.payitforward.util.FirestoreUtil
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class StatisticsFragment : Fragment() {

    data class Score(
        val name: String,
        val score: Int,
    )

    private lateinit var scoreList: MutableList<Score>

    var tasksList: List<Task> = java.util.ArrayList()
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var binding: FragmentStatisticsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initRecyclerView()
        loadTasks()
        initSelector()
        initBarChart()

        return root
    }

    private fun initSelector() {

        binding.apply {
            var lastTime = Week

            Week.setOnClickListener {
                Week.background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_button_pink, null)
                lastTime.background = null
                lastTime = Week
                scoreList = getScoreList(7)
                changeChart(scoreList)
            }

            Month.setOnClickListener {
                Month.background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_button_pink, null)
                lastTime.background = null
                lastTime = Month
                scoreList = getScoreList(30)
                changeChart(scoreList)
            }

        }

    }

    private fun changeChart(scoreList: MutableList<Score>) {
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

    @SuppressLint("SimpleDateFormat")
    private fun getScoreList(n: Int): MutableList<Score> {
        val sfd = SimpleDateFormat("dd.MM")
        val scoreL = mutableListOf<Score>()
        var current = Timestamp.now()
        for(i in 1..n) {
            val day = current.toDate()
            val dayStr = sfd.format(day)
            scoreL.add(Score(dayStr, getCoinsFromRange(Timestamp(atStartOfDay(day)), Timestamp(atEndOfDay(day)))))
            current = Timestamp(current.seconds - 86400, 0)
        }

        return scoreL.asReversed()
    }

    private fun atEndOfDay(date: Date): Date {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.time
    }

    private fun atStartOfDay(date: Date): Date {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

    private fun getCoinsFromRange(l: Timestamp, r: Timestamp) : Int {
        var sum = 0
        for (t in tasksList) {
            if (t.completionDate!! < r && t.completionDate!! >= l) {
                sum += t.coins
            }
        }
        return sum
    }

    private fun loadTasks() {
        FirestoreUtil.getCompletedTasks { tasks ->
            tasksList = tasks
            tasksAdapter.setItems(tasksList)
            scoreList = getScoreList(7)
            changeChart(scoreList)
        }
    }

    private fun initRecyclerView() {
        binding.history.layoutManager = LinearLayoutManager(this.context)
        tasksAdapter = TasksAdapter(context)
        tasksAdapter.setOnTaskClickListener(object : TasksAdapter.onTaskClickListener{
            override fun onTaskClick(position: Int) {
                //Toast.makeText(this, "You clicked on item on $position", Toast.LENGTH_LONG).show()
                val intent = Intent(view!!.context, ItemTaskActivity::class.java)
                intent.putExtra("taskId", tasksList[position].id)
                intent.putExtra("creationDate", getDate(tasksList[position].creationDate.seconds))
                intent.putExtra("deadlineDate", getDate(tasksList[position].deadlineDate.seconds))
                intent.putExtra("authorId", tasksList[position].authorId)
                intent.putExtra("executorId", tasksList[position].executorId)
                intent.putExtra("name", tasksList[position].name)
                intent.putExtra("description", tasksList[position].description)
                intent.putExtra("imageUrl", tasksList[position].imageUrl)
                intent.putExtra("coins", tasksList[position].coins.toString())
                intent.putExtra("taskType", tasksList[position].type)
                startActivity(intent)
            }

        })
        binding.history.adapter = tasksAdapter
    }


    fun getDate(timestamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        val date = DateFormat.format("dd-MM-yyyy", calendar).toString()
        return date
    }
}