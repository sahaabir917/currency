package io.bechitra.currencyanalyzer.fragment

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.renderer.XAxisRenderer
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Transformer
import com.github.mikephil.charting.utils.Utils
import com.github.mikephil.charting.utils.ViewPortHandler
import io.bechitra.currencyanalyzer.databinding.FragmentGrowthBinding
import io.bechitra.currencyanalyzer.network.Currency
import io.bechitra.currencyanalyzer.viewmodel.GrowthFragmentViewModel
import kotlin.math.roundToInt


class GrowthFragment : Fragment(){
    private lateinit var bind: FragmentGrowthBinding
    private lateinit var viewModel: GrowthFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentGrowthBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(GrowthFragmentViewModel::class.java)

        viewModel.getData.observe(viewLifecycleOwner, Observer {
            reloadGraph(it)
        })

        var dataList = listOf(
            Entry(0f, 60f),
            Entry(1f, 70f),
            Entry(2f, 30f),
            Entry(3f, 20f),
            Entry(4f, 80f),
            Entry(5f, 10f)
        )

        var color = arrayOf(
            Color.rgb(137, 230, 81),
            Color.rgb(240, 240, 30),
            Color.rgb(89, 199, 250),
            Color.rgb(250, 104, 104)
        )
        //val drawable = ContextCompat.getDrawable(inflater.context, io.bechitra.currencyanalyzer.R.drawable.gradient_color)
        var lineData = LineDataSet(dataList, "Set1")
        lineData.setDrawFilled(true)
        var iLineDataSet = listOf<ILineDataSet>(lineData)
        var lineDataSet = LineData(iLineDataSet)
       // lineData.colors = color.toMutableList()
        bind.lineChart.data = lineDataSet
        bind.lineChart.invalidate()

        return bind.root
    }

    private fun reloadGraph(list: List<Currency>?) {
        if(list != null) {
            val gData = getGraphData(list)
            val yAxis = getListOfDate(list)

            val lData = LineDataSet(gData, "API Service")

            val xAxis = bind.lineChart.xAxis
            xAxis.valueFormatter = IndexValueFormatter(yAxis)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setLabelCount(yAxis.size)
            xAxis.labelRotationAngle = -70f
            bind.lineChart.setXAxisRenderer(
                CustomXAxisRenderer(
                    bind.lineChart.viewPortHandler,
                    bind.lineChart.xAxis,
                    bind.lineChart.getTransformer(YAxis.AxisDependency.LEFT)
                )
            )
            val iLData = listOf<ILineDataSet>(lData)
            val linData = LineData(iLData)
            bind.lineChart.data = linData
            bind.lineChart.notifyDataSetChanged()
            bind.lineChart.invalidate()
        }
    }

    private fun getListOfDate(currency: List<Currency>):List<String> {
        val list = mutableListOf<String>()

        for (value in currency)
            list.add(value.date)

        return list
    }

    private fun getGraphData(currency: List<Currency>): List<Entry> {
        val list = mutableListOf<Entry>()
        var i = 0

        for (value in currency) {
//            list.add(Entry(i.toFloat(), value.rate.toFloat()))
            i += 1
        }

        return list
    }

    class IndexValueFormatter(private val axisValue:List<String>) : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            val index = value.roundToInt()
            return axisValue.get(index)
        }
    }

    class CustomXAxisRenderer(viewPortHandler: ViewPortHandler, xAxis: XAxis, transformer: Transformer): XAxisRenderer(viewPortHandler, xAxis, transformer) {
        override fun drawLabel(
            c: Canvas?,
            formattedLabel: String?,
            x: Float,
            y: Float,
            anchor: MPPointF?,
            angleDegrees: Float
        ) {
            val line = formattedLabel?.split("\n")

            if(line?.size!! > 1) {
                Utils.drawXAxisValue(c, line[0], x, y, mAxisLabelPaint, anchor, angleDegrees)
                Utils.drawXAxisValue(c, line[1], x +mAxisLabelPaint.textSize, y, mAxisLabelPaint, anchor, angleDegrees)
                //Utils.drawXAxisValue(c, line[1], x + mAxisLabelPaint.getTextSize(), y + mAxisLabelPaint.getTextSize(), mAxisLabelPaint, anchor, angleDegrees);
            } else
                Utils.drawXAxisValue(c, formattedLabel, x, y, mAxisLabelPaint, anchor, angleDegrees)
        }
    }
}