package com.example.payitforward

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate


class StatisticsActivity : AppCompatActivity() {

    data class Score(
        val name:String,
        val score: Int,
    )

    private lateinit var barChart: BarChart
    private var scoreList = ArrayList<Score>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        supportActionBar?.hide()

        initSelector()

        barChart = findViewById(R.id.barChart)
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
        barChart.data = data

        barChart.invalidate()
    }

    private fun initSelector() {
        val day: TextView = findViewById(R.id.Day)
        val week: TextView = findViewById(R.id.Week)
        val month: TextView = findViewById(R.id.Month)
        val year: TextView = findViewById(R.id.Year)
        val sep_1: View = findViewById(R.id.sep_1)
        val sep_2: View = findViewById(R.id.sep_2)
        val sep_3: View = findViewById(R.id.sep_3)
        var lastTime = week

        day.setOnClickListener {
            sep_1.visibility = View.GONE
            sep_2.visibility = View.VISIBLE
            sep_3.visibility = View.VISIBLE
            day.background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_button_pink, null)
            lastTime.background = null
            lastTime = day
        }

        week.setOnClickListener {
            sep_1.visibility = View.GONE
            sep_2.visibility = View.GONE
            sep_3.visibility = View.VISIBLE
            week.background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_button_pink, null)
            lastTime.background = null
            lastTime = week
        }

        month.setOnClickListener {
            sep_1.visibility = View.VISIBLE
            sep_2.visibility = View.GONE
            sep_3.visibility = View.GONE
            month.background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_button_pink, null)
            lastTime.background = null
            lastTime = month
        }

        year.setOnClickListener {
            sep_1.visibility = View.VISIBLE
            sep_2.visibility = View.VISIBLE
            sep_3.visibility = View.GONE
            year.background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_button_pink, null)
            lastTime.background = null
            lastTime = year
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
        barChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = barChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        barChart.axisRight.isEnabled = false
        barChart.legend.isEnabled = false
        barChart.description.isEnabled = false

        barChart.animateY(3000)

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

}


