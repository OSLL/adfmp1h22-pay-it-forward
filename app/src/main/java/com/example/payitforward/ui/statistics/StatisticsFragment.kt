package com.example.payitforward.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.payitforward.R
import com.example.payitforward.adapters.TasksAdapter
import com.example.payitforward.databinding.FragmentStatisticsBinding
import com.example.payitforward.pojo.Task
import com.example.payitforward.pojo.User
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.Timestamp

class StatisticsFragment : Fragment() {

    data class Score(
        val name: String,
        val score: Int,
    )

    private var scoreList = ArrayList<Score>()

    var tasksList: MutableList<Task> = java.util.ArrayList()
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var binding: FragmentStatisticsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val root: View = binding.root

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

        return root
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
        val lst: MutableList<Task> = java.util.ArrayList<Task>()
        for (i in 1..10) {
            lst.add(
                Task(
                    "1",
                    Timestamp.now(),
                    Timestamp.now(),
                    "1",
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
        binding.history.layoutManager = LinearLayoutManager(this.context)
        tasksAdapter = TasksAdapter()
        tasksAdapter.setOnTaskClickListener(object : TasksAdapter.onTaskClickListener{
            override fun onTaskClick(position: Int) {
                //Toast.makeText(this, "You clicked on item on $position", Toast.LENGTH_LONG).show()
            }

        })
        binding.history.adapter = tasksAdapter
    }
}