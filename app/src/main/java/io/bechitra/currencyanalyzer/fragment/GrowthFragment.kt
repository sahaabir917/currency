package io.bechitra.currencyanalyzer.fragment

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.rgb
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
    private var sizes1: Int = 0
    private var colors: Array<Int>? = null
    private lateinit var  datasets: ArrayList<Entry>
    private lateinit var bind: FragmentGrowthBinding
    private lateinit var viewModel: GrowthFragmentViewModel
    private lateinit var currencydata : ArrayList<Currency>

    init {
        this.currencydata = mutableListOf<Currency>() as ArrayList<Currency>
        this.datasets = mutableListOf<Entry>() as ArrayList<Entry>

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentGrowthBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(GrowthFragmentViewModel::class.java)

        viewModel.getData.observe(viewLifecycleOwner, Observer {


        })

        currencydata?.add(Currency("2020-01-20","USD",10.0,"afghani"))
        currencydata?.add(Currency("2020-01-21","USD",14.1,"afghani"))
        currencydata?.add(Currency("2020-01-22","USD",13.02,"afghani"))
        currencydata?.add(Currency("2020-01-23","USD",13.98,"afghani"))
        currencydata?.add(Currency("2020-01-24","USD",12.1,"afghani"))
        currencydata?.add(Currency("2020-01-25","USD",14.6,"afghani"))
        currencydata?.add(Currency("2020-01-26","USD",17.2,"afghani"))
        currencydata?.add(Currency("2020-01-27","USD",10.5,"afghani"))
        currencydata?.add(Currency("2020-01-28","USD",20.0,"afghani"))

        reloadGraph(currencydata as List<Currency>)

        var sizes : Int = currencydata.size -1

        for(i in 0..sizes){
            datasets.add(Entry(i.toFloat(),currencydata[i].rate.toFloat()))
        }

        var dataList = datasets as List<Entry>

//        sizes1 = currencydata.size - 3
//        for(i in 0..sizes1){
//             colors.add( Color.rgb(137, 230, 81))
//        }


        var color = arrayOf(
            Color.rgb(62, 133, 27),
            Color.rgb(62, 133, 27),
            Color.rgb(62, 133, 27),
            Color.rgb(62, 133, 27),
            Color.rgb(62, 133, 27),
            Color.rgb(137, 230, 81),
            Color.rgb(137, 230, 81),
            Color.rgb(137, 230, 81),
            Color.rgb(137, 230, 81)
        )




        val drawable = ContextCompat.getDrawable(inflater.context, io.bechitra.currencyanalyzer.R.drawable.gradient_color)
        var lineData = LineDataSet(dataList, "Set1")
        lineData.setDrawFilled(true)
        var iLineDataSet = listOf<ILineDataSet>(lineData)
        var lineDataSet = LineData(iLineDataSet)
        lineData.colors = color.toMutableList()
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

        for (value in currencydata)
            list.add(value.date)

        return list
    }

    private fun getGraphData(currency: List<Currency>): List<Entry> {
        val list = mutableListOf<Entry>()
        var i = 0

        for (value in currency) {
            list.add(Entry(i.toFloat(), value.rate.toFloat()))
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
                Utils.drawXAxisValue(c, line[1], x + mAxisLabelPaint.getTextSize(), y + mAxisLabelPaint.getTextSize(), mAxisLabelPaint, anchor, angleDegrees);
            } else
                Utils.drawXAxisValue(c, formattedLabel, x, y, mAxisLabelPaint, anchor, angleDegrees)
        }
    }
}