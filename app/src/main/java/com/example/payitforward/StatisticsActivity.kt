package com.example.payitforward

import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.scichart.charting.model.dataSeries.IXyDataSeries
import com.scichart.charting.visuals.SciChartSurface
import com.scichart.charting.visuals.axes.IAxis
import com.scichart.charting.visuals.renderableSeries.FastColumnRenderableSeries
import com.scichart.charting.visuals.renderableSeries.data.XSeriesRenderPassData
import com.scichart.charting.visuals.renderableSeries.paletteProviders.IFillPaletteProvider
import com.scichart.charting.visuals.renderableSeries.paletteProviders.PaletteProviderBase
import com.scichart.core.framework.UpdateSuspender
import com.scichart.core.model.IntegerValues
import com.scichart.drawing.utility.ColorUtil
import com.scichart.extensions.builders.SciChartBuilder
import java.util.*


class StatisticsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        initChart()
    }

    private fun initChart() {
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


        val surface: SciChartSurface = findViewById(R.id.surface)
        SciChartBuilder.init(this)
        val sciChartBuilder = SciChartBuilder.instance()

        val xAxis: IAxis = sciChartBuilder.newNumericAxis().withGrowBy(0.1, 0.1).build()
        val yAxis: IAxis = sciChartBuilder.newNumericAxis().withGrowBy(0.0, 0.1).build()

        val dataSeries: IXyDataSeries<Int, Int> = sciChartBuilder.newXyDataSeries(
            Int::class.java,
            Int::class.java
        ).build()
        val yValues = intArrayOf(50, 35, 61, 58, 50, 50, 40, 53, 55, 23, 45, 12, 59, 60)

        for (i in yValues.indices) {
            dataSeries.append(i, yValues[i])
        }

        val rSeries = sciChartBuilder.newColumnSeries()
            .withStrokeStyle(-0xdcdcdd, 0.4f)
            .withDataPointWidth(0.7)
            .withLinearGradientColors(ColorUtil.LightSteelBlue, ColorUtil.SteelBlue)
            .withDataSeries(dataSeries)
            .withPaletteProvider(ColumnsPaletteProvider())
            .build()

        UpdateSuspender.using(surface) {
            Collections.addAll(surface.xAxes, xAxis)
            Collections.addAll(surface.yAxes, yAxis)
            Collections.addAll(surface.renderableSeries, rSeries)
            Collections.addAll(
                surface.chartModifiers,
                sciChartBuilder.newModifierGroupWithDefaultModifiers().build()
            )
            sciChartBuilder.newAnimator(rSeries).withWaveTransformation()
                .withInterpolator(DecelerateInterpolator())
                .withDuration(3000).withStartDelay(350).start()
        }
    }
}

private class ColumnsPaletteProvider:
    PaletteProviderBase<FastColumnRenderableSeries>(FastColumnRenderableSeries::class.java),
    IFillPaletteProvider {

    private val colors = IntegerValues()
    private val desiredColors = longArrayOf(0xFFa9d34f, 0xFFfc9930, 0xFFd63b3f)
    override fun update() {
        val currentRenderPassData = renderableSeries!!.currentRenderPassData as XSeriesRenderPassData

        val size = currentRenderPassData.pointsCount()
        colors.setSize(size)

        val colorsArray = colors.itemsArray
        val indices = currentRenderPassData.indices.itemsArray
        for (i in 0 until size) {
            val index = indices[i]
            colorsArray[i] = desiredColors[index % 3].toInt()
        }
    }

    override fun getFillColors(): IntegerValues = colors

}
