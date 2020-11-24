package io.bechitra.currencyanalyzer.fragment

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.format.DayFormatter
import io.bechitra.currencyanalyzer.R
import io.bechitra.currencyanalyzer.databinding.FragmentCalenderBinding
import io.bechitra.currencyanalyzer.network.Currency
import io.bechitra.currencyanalyzer.viewmodel.CalenderFragmentViewModel


class CalenderFragment : Fragment(){

    private lateinit var viewBind:FragmentCalenderBinding
    private lateinit var viewModel:CalenderFragmentViewModel
    var currencydata: ArrayList<io.bechitra.currencyanalyzer.network.Currency>? = null


    init {
        this.currencydata = mutableListOf<Currency>() as ArrayList<Currency>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBind = FragmentCalenderBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(CalenderFragmentViewModel::class.java)

//        viewModel.getResponse.observe(viewLifecycleOwner, Observer {
//
//            viewBind.calendarView.setDayFormatter(TextAdder(it))
//        })
//
        currencydata?.add(Currency("2020-01-20","USD",10.0,"afghani",15.0,5.0))
        currencydata?.add(Currency("2020-01-21","USD",14.1,"afghani",19.8,10.8))
        currencydata?.add(Currency("2020-01-22","USD",13.02,"afghani",20.4,10.2))
        currencydata?.add(Currency("2020-01-23","USD",13.98,"afghani",16.8,10.8))
        currencydata?.add(Currency("2020-01-24","USD",12.1,"afghani",13.5,11.4))
        currencydata?.add(Currency("2020-01-25","USD",14.6,"afghani",19.6,12.5))
        currencydata?.add(Currency("2020-01-26","USD",21.4,"afghani",26.7,19.2))
        currencydata?.add(Currency("2020-01-27","USD",10.5,"afghani",15.5,4.5))

        viewBind.calendarView.setDayFormatter(TextAdder(currencydata as List<Currency>))

        return viewBind.root
    }

    private inner class TextAdder(currency: List<Currency>) :
        DayFormatter {

        private var currency: List<Currency> = currency

        override fun format(day: CalendarDay): String {
            for(days in currency) {
                val array = days.date.split("-")

                if( array[0].toInt() == day.year && array[1].toInt() == day.month && array[2].toInt() == day.day) {
                    return "      "+day.day.toString()+"\n$"+days.rate
                }
            }
            return day.day.toString()
        }

    }
}