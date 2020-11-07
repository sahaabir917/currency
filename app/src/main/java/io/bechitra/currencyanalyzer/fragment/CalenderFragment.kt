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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBind = FragmentCalenderBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(CalenderFragmentViewModel::class.java)

        viewModel.getResponse.observe(viewLifecycleOwner, Observer {

            viewBind.calendarView.setDayFormatter(TextAdder(it))
        })

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