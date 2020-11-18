package io.bechitra.currencyanalyzer.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.bechitra.currencyanalyzer.R
import io.bechitra.currencyanalyzer.adapter.HomePageAdapter
import io.bechitra.currencyanalyzer.databinding.FragmentExchangeBinding
import io.bechitra.currencyanalyzer.network.Currency
import io.bechitra.currencyanalyzer.sharedpreference.PreConfig
import io.bechitra.currencyanalyzer.viewmodel.ExchangeFragmentViewModel
import kotlinx.android.synthetic.main.fragment_exchange.*
import kotlinx.android.synthetic.main.notifications.view.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.reflect.Type


class ExchangeFragment : Fragment() {
    private lateinit var binding: FragmentExchangeBinding
    private lateinit var adapter: HomePageAdapter
    private lateinit var viewModel: ExchangeFragmentViewModel
    private var dialogBuilder: AlertDialog.Builder? = null
    var pagenumber: Int = 1
    var isScrolling = false
    var currentItem: Int = 0
    var totalItem: Int = 0
    var scrolloutItems: Int = 0
    var counts: Int = 0
    var visible: Boolean = true
    var count: Int = 0
    var removingposition: Int = -1
    var counting: Int = 1
    lateinit var sharedpreferences: SharedPreferences
    private var mhandaler: Handler = Handler()
    lateinit var layoutManager: RecyclerView.LayoutManager
    var currencydata: ArrayList<io.bechitra.currencyanalyzer.network.Currency>? = null
    var currencydatas: ArrayList<io.bechitra.currencyanalyzer.data.Currency>? = null
    var preConfig: PreConfig = PreConfig()
    lateinit var currencytask: io.bechitra.currencyanalyzer.network.Currency
    lateinit var currencytasks: io.bechitra.currencyanalyzer.data.Currency
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var dialog: AlertDialog
    var sb = StringBuilder("All Notifications \n")


    init {
        this.currencydata = mutableListOf<Currency>() as ArrayList<Currency>
        this.currencydatas =
            mutableListOf<io.bechitra.currencyanalyzer.data.Currency>() as ArrayList<io.bechitra.currencyanalyzer.data.Currency>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var currentsize = currencydata!!.size -1




        binding = FragmentExchangeBinding.inflate(inflater, container, false)
        binding.recyclerView1.layoutManager = LinearLayoutManager(inflater.context)
        adapter = HomePageAdapter(inflater.context, object : HomePageAdapter.Itemclicklistener {
            override fun onClickItemClick(view: View, data: Currency) {
                TODO("Not yet implemented")
            }


            override fun onClickCalculatorClick(view: View, data: Currency) {
//
                TODO("Not yet implemented")
            }


        })


        binding.recyclerView1.adapter = adapter

        binding.imageview2.setOnClickListener {
            tvInput.text = ""
            linear1.visibility = View.VISIBLE
            recyclerView1.visibility = View.GONE

            var size = currencydata!!.size - 1
            sharedpreferences = context!!.getSharedPreferences("calculate", Context.MODE_PRIVATE)
            val newrate = sharedpreferences.getString("result", "null")
            if (counting == 1) {
                for (i in 0..size) {
                    currencydata!![i].rate = currencydata!![i].rate / 1
                    d("aftermulti", currencydata.toString())
//
                }
                adapter.setData(currencydata!! as MutableList<Currency>)
            } else {

                for (i in 0..size) {
                    currencydata!![i].rate = currencydata!![i].rate / newrate!!.toDouble()
                    d("aftermulti", currencydata.toString())
                }
                adapter.setData(currencydata!! as MutableList<Currency>)
            }

            counting++

            done.setOnClickListener {
                calculateComplete()
            }
            btn0.setOnClickListener { appendOnClick(true, "0") }
            btn1.setOnClickListener { appendOnClick(true, "1") }
            btn2.setOnClickListener { appendOnClick(true, "2") }
            btn3.setOnClickListener { appendOnClick(true, "3") }
            btn4.setOnClickListener { appendOnClick(true, "4") }
            btn5.setOnClickListener { appendOnClick(true, "5") }
            btn6.setOnClickListener { appendOnClick(true, "6") }
            btn7.setOnClickListener { appendOnClick(true, "7") }
            btn8.setOnClickListener { appendOnClick(true, "8") }
            btn9.setOnClickListener { appendOnClick(true, "9") }
            btnDot.setOnClickListener { appendOnClick(true, ".") }


            //Operator Listeners
            btnPlus.setOnClickListener { appendOnClick(false, "+") }
            btnMinus.setOnClickListener { appendOnClick(false, "-") }
            btnMultiply.setOnClickListener { appendOnClick(false, "*") }
            btnDivide.setOnClickListener { appendOnClick(false, "/") }
            btnLeftB.setOnClickListener { appendOnClick(false, "(") }
            btnRightB.setOnClickListener { appendOnClick(false, ")") }


            btnClear.setOnClickListener {
                clearcalculator()
            }

            btnEqual.setOnClickListener {
                calculate()
            }
        }


        sharedpreferences = context!!.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE)
        val date = sharedpreferences.getString("date", null)
        val rate = sharedpreferences.getString("rate", null)
        val source = sharedpreferences.getString("source", null)
        val destinations = sharedpreferences.getString("destination", null)
        val high = sharedpreferences.getString("high", null)
        val low = sharedpreferences.getString("low", null)
//        d("date",date)
//        d("rate",rate)
//        d("source",source.toString())
//        d("destination",destinations.toString())

        d("currencydata", currencydata.toString())

        val sharedPreferences: SharedPreferences =
            context!!.getSharedPreferences("shared preferences", MODE_PRIVATE)
        val gson = Gson()
        var json1 = sharedPreferences.getString("task list", null)
        val type: Type =
            object : TypeToken<ArrayList<io.bechitra.currencyanalyzer.network.Currency?>?>() {}.type
        currencydata = gson.fromJson(json1, type)
        if (currencydata == null) {
            currencydata = ArrayList()
        }


//        currencytask = io.bechitra.currencyanalyzer.data.Currency(rate!!.to)

        currencytask = Currency(
            date.toString(),
            source.toString(),
            rate!!.toDouble(),
            destinations.toString(),
            high!!.toDouble(),
            low!!.toDouble()
        )


//                currencytask = Currency("2020-1-20","dhaka",5.4,"faridpur",7.3,3.6)

        counts = 0
        var size = currencydata!!.size - 1
        for (i in 0..size) {
            if (currencydata!![i] == currencytask) {
                d("dublicate", "dublicated data")
                counts += 1

            } else if (currencydata!![i] != currencytask) {
                d("notdublicated", "not dublicated")
//                remove korte hole position dhoro ei khan thake then json e position tai change kore dau
            }
        }


        if (counts <= 0) {

            currencydata?.add(currencytask)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            var json = gson.toJson(currencydata)
            editor.putString("task list", json)
//            editor.remove("asda",json[i])
            editor.commit()
            counts = 0
        }



        var json2 = sharedPreferences.getString("task list", null)
        val type1: Type = object : TypeToken<ArrayList<Currency?>?>() {}.type
        currencydata = gson.fromJson(json2, type1)
        if (currencydata == null) {
            currencydata = ArrayList()
        }

//        d("jsondata",json)



        //write here
        var highlow = 0
        var notno = 0

        for (i in 0..size) {
            if (currencydata!![i].rate >= currencydata!![i].high) {
                d("currencydatahigh", currencydata!![i].destination)
                highlow++
                notno++
                sb?.append( notno.toString() +". " + currencydata!![i].destination + " rate is high today")
                sb?.append("\n \n")
                binding.notificationnumber.text = highlow.toString()
            } else if ( currencydata!![i].rate <= currencydata!![i].low) {
                d("currencydatalow", currencydata!![i].destination)
                highlow++
                notno++
                sb?.append( notno.toString() + ". " + currencydata!![i].destination + " rate is low today")
                sb?.append("\n\n")
                binding.notificationnumber.text = highlow.toString()
            }
        }

        binding.bellicon.setOnClickListener {
            highlow = 0
            binding.notificationnumber.text = highlow.toString()
            alertDialog = AlertDialog.Builder(context)
            var view1 = layoutInflater.inflate(R.layout.notifications,null)
            view1.messages.text = sb.toString()
            view1.clearall.setOnClickListener {
                sb.clear()
                sb.append("No notifications")
                view1.messages.text = sb.toString()
            }
            alertDialog.setView(view1)
            dialog = alertDialog.create()
            dialog.show()
        }




        d("retrivedata", currencydata.toString())

        d("currencydata", currencytask.toString())
        adapter.setData(currencydata!! as MutableList<Currency>)
        layoutManager = LinearLayoutManager(activity)
        binding.recyclerView1.layoutManager = layoutManager

        binding.recyclerView1.setHasFixedSize(true)

        return binding.root
    }

    private fun calculateComplete() {

        var sharedpreferences: SharedPreferences =
            context!!.getSharedPreferences("calculate", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedpreferences.edit()
        if (tvInput.text.toString().toDouble() > 0) {
            editor.putString("result", tvInput.text.toString())
            d("results", tvInput.text.toString())
            editor.commit()
        }
//        else if(view!!.tvOutput.text.toString() == null){
//          Toast.makeText(context,"please press the done button",Toast.LENGTH_LONG).show()
//          editor.putString("result","1")
//          d("results",view1.tvOutput.text.toString())
//          editor.commit()
//      }

        else {
            Toast.makeText(context, "you cannot use 0", Toast.LENGTH_LONG).show()
            editor.putString("result", "1")
            d("results", tvInput.text.toString())
            editor.commit()
        }

        recyclerView1.visibility = View.VISIBLE
        linear1.visibility = View.GONE

        sharedpreferences = context!!.getSharedPreferences("calculate", Context.MODE_PRIVATE)
        val newrate = sharedpreferences.getString("result", null)

        var size = currencydata!!.size - 1
        for (i in 0..size) {
            currencydata!![i].rate = currencydata!![i].rate * newrate!!.toDouble()
            d("aftermulti", currencydata.toString())
        }
        adapter.setData(currencydata!! as MutableList<Currency>)


    }

    private fun clearcalculator() {
        tvInput.text = ""
        tvOutput.text = ""
    }

    private fun calculate() {

        try {

            val input = ExpressionBuilder(tvInput.text.toString()).build()
            val output = input.evaluate()
            val longOutput = output.toLong()
            if (output == longOutput.toDouble()) {
                tvInput.text = longOutput.toString()
            } else {
                tvInput.text = output.toString()
            }

        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun appendOnClick(clear: Boolean, string: String) {
        if (clear) {
            tvOutput.text = ""
            tvInput.append(string)
        } else {
            tvInput.append(tvOutput.text)
            tvInput.append(string)
            tvOutput.text = ""
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fab1.setOnClickListener { view ->
            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()

            val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.mainFrameLayout, AllExchangeFragment())
            fragmentTransaction.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (lifecycle as? LifecycleRegistry)?.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    override fun onPause() {
        super.onPause()
        visible = false
    }

    override fun onResume() {
        super.onResume()
        visible = true
    }
//
//          fun fetchfirst(){
//         count++
//       d("count",count.toString())
//        viewModel.requestNetwork().observe(viewLifecycleOwner, Observer {it->
//           if (it != null)
//                binding.predictionText.setText("Success : "+it[0].date)
//            adapter.setData(it as MutableList<Currency>)
//           fetch()
//       })
//    }
//
//    fun fetch(){
//         Handler().postDelayed({
//             fetchfirst()
//         },5000)
//     }
}