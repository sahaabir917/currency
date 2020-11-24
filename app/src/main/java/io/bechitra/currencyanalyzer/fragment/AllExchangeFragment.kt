package io.bechitra.currencyanalyzer.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.bechitra.currencyanalyzer.R
import io.bechitra.currencyanalyzer.adapter.HomePageAdapter
import io.bechitra.currencyanalyzer.databinding.FragmentAllExchangeBinding
import io.bechitra.currencyanalyzer.network.Currency
import io.bechitra.currencyanalyzer.sharedpreference.PreConfig
import io.bechitra.currencyanalyzer.viewmodel.ExchangeFragmentViewModel
import kotlinx.android.synthetic.main.fragment_all_exchange.*

class AllExchangeFragment : Fragment() {


    private lateinit var binding: FragmentAllExchangeBinding
    private lateinit var adapter : HomePageAdapter
    private lateinit var viewModel: ExchangeFragmentViewModel
    var pagenumber : Int = 1
    var isScrolling = false
    var currentItem: Int = 0
    var totalItem: Int = 0
    var scrolloutItems: Int = 0
    var count  : Int = 0
    var visible: Boolean = true
    private var mhandaler : Handler = Handler()
    lateinit var layoutManager: RecyclerView.LayoutManager

    var preConfig : PreConfig = PreConfig()
     var currencytask : io.bechitra.currencyanalyzer.network.Currency? =null
    var currencydata: ArrayList<io.bechitra.currencyanalyzer.network.Currency>? = null


    init {
        this.currencydata = mutableListOf<Currency>() as ArrayList<Currency>
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllExchangeBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(inflater.context)
        adapter = HomePageAdapter(inflater.context,object :HomePageAdapter.Itemclicklistener{

            override fun onClickItemClick(view: View, data: Currency) {
                val sharedpreferences: SharedPreferences = context!!.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedpreferences.edit()
                editor.putString("date", data.date)
                editor.putString("rate",data.rate.toString())
                editor.putString("source",data.source)
                editor.putString("destination",data.destination)
                editor.putString("high", data.high.toString())
                editor.putString("low",data.low.toString())
                editor.commit()
            }

            override fun onClickCalculatorClick(view: View, data: Currency) {

            }

        })
        binding.recyclerView.adapter = adapter
        viewModel = ViewModelProvider(requireActivity()).get(ExchangeFragmentViewModel::class.java)
//
//        viewModel.requestNetwork().observe(viewLifecycleOwner, Observer {it->
//            if(visible){
//                if (it != null)
//                    binding.predictionText.setText("  Updated : "+it[0].date)
//                adapter.setData(it as MutableList<Currency>)
//            }
//        })

        adapter.setDatas(createdata() as MutableList<Currency>)

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                Log.d("refreshed", "refreshed")
//
//                viewModel.requestNetwork()
//                val v = viewModel.getDataSet()
//                adapter.setDatas(v)


                handler.postDelayed(this, 5000)

            }
        }, 1000)




//        fab.setBackgroundColor(R.color.colorPrimaryDark)




        layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager

        binding.recyclerView.setHasFixedSize(true)

        return binding.root
    }

    private fun createdata(): ArrayList<Currency>? {
            currencydata?.add(Currency("2020-01-20","USD",1.2,"afghani",2.0,0.6))
            currencydata?.add(Currency("2020-01-20","USD",79.04,"AED",89.2,19.0))
            currencydata?.add(Currency("2020-01-20","USD",93.74,"AFN",90.4,96.4))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"AMD",500.0,487.4))
            currencydata?.add(Currency("2020-01-20","USD",76.74,"ANG",78.12,72.5))
            currencydata?.add(Currency("2020-01-20","USD",87.74,"AOA",91.50,84.50))
            currencydata?.add(Currency("2020-01-20","USD",59.74,"ARS",57.4,61.3))
            currencydata?.add(Currency("2020-01-20","USD",42.74,"AUD",45.85,40.85))
            currencydata?.add(Currency("2020-01-20","USD",100.74,"AZN",105.3,79.3))
            currencydata?.add(Currency("2020-01-20","USD",404.74,"BAM",400.69,387.2))
            currencydata?.add(Currency("2020-01-20","USD",85.74,"BDT",88.0,80.0))
            currencydata?.add(Currency("2020-01-20","USD",76.84,"BBD",98.4,56.6))
            currencydata?.add(Currency("2020-01-20","USD",65.74,"BHD",56.32,87.32))
            currencydata?.add(Currency("2020-01-20","USD",56.74,"BIF",61.3,50.2))
            currencydata?.add(Currency("2020-01-20","USD",98.74,"BMD",97.00,90.0)) //high notification
            currencydata?.add(Currency("2020-01-20","USD",201.74,"BOB",205.2,203.9)) // low notification
            currencydata?.add(Currency("2020-01-20","USD",702.74,"BRL",729.8,705.3)) //low
            currencydata?.add(Currency("2020-01-20","USD",65.74,"BSD",70.0,20.6))
            currencydata?.add(Currency("2020-01-20","USD",43.74,"BTN",46.7,40.0))
            currencydata?.add(Currency("2020-01-20","USD",44.74,"BWP",50.8,42.2))
            currencydata?.add(Currency("2020-01-20","USD",652.74,"BYN",650.1,601.0))
            currencydata?.add(Currency("2020-01-20","USD",98.74,"BZD",100.90,96.5))
            currencydata?.add(Currency("2020-01-20","USD",107.74,"CAN",110.9,105.9))
            currencydata?.add(Currency("2020-01-20","USD",302.74,"CDF",301.8,287.9)) //high
            currencydata?.add(Currency("2020-01-20","USD",74.74,"CHF",84.2,70.4))
            currencydata?.add(Currency("2020-01-20","USD",653.74,"CLP",660.90,600.0))
            currencydata?.add(Currency("2020-01-20","USD",192.74,"CNY",200.0,170.8))
            currencydata?.add(Currency("2020-01-20","USD",284.74,"COP",290.9,280.4))
            currencydata?.add(Currency("2020-01-20","USD",187.74,"CRC",201.9,185.0))
            currencydata?.add(Currency("2020-01-20","USD",200.74,"CUC",205.10,194.2))
            currencydata?.add(Currency("2020-01-20","USD",13.74,"CUP",18.6,8.65))
            currencydata?.add(Currency("2020-01-20","USD",20.74,"CVE",25.0,15.9))
            currencydata?.add(Currency("2020-01-20","USD",30.74,"CZK",34.6,25.0))
            currencydata?.add(Currency("2020-01-20","USD",34.74,"DFJ",39.8,30.2))
            currencydata?.add(Currency("2020-01-20","USD",32.74,"DKK",38.9,30.2))
            currencydata?.add(Currency("2020-01-20","USD",53.74,"DOP",55.3,40.8))
            currencydata?.add(Currency("2020-01-20","USD",28.74,"DZD",30.8,25.4))
            currencydata?.add(Currency("2020-01-20","USD",76.74,"EGP",81.2,70.4))
            currencydata?.add(Currency("2020-01-20","USD",3.74,"ERN",5.2,3.0))
            currencydata?.add(Currency("2020-01-20","USD",51.74,"ETB",55.6,50.2))
            currencydata?.add(Currency("2020-01-20","USD",37.74,"EUR",40.5,35.5))
            currencydata?.add(Currency("2020-01-20","USD",43.74,"FJD",48.5,39.88))
            currencydata?.add(Currency("2020-01-20","USD",44.74,"FKP",50.10,40.56))
            currencydata?.add(Currency("2020-01-20","USD",453.74,"GBP",460.9,440.2))
            currencydata?.add(Currency("2020-01-20","USD",463.74,"GEL",469.9,460.9))
            currencydata?.add(Currency("2020-01-20","USD",123.74,"GGP",125.2,120.98))
            currencydata?.add(Currency("2020-01-20","USD",35.74,"GIP",39.8,30.2))
            currencydata?.add(Currency("2020-01-20","USD",341.74,"GMD",345.28,335.65))
            currencydata?.add(Currency("2020-01-20","USD",982.74,"GNF",986.29,980.3))
            currencydata?.add(Currency("2020-01-20","USD",412.74,"GTQ",420.7,400.82))
            currencydata?.add(Currency("2020-01-20","USD",410.74,"GYD",400.5,382.98)) //high
            currencydata?.add(Currency("2020-01-20","USD",204.74,"HKD",256.8,200.5))
            currencydata?.add(Currency("2020-01-20","USD",302.74,"HNL",308.7,300.2))
            currencydata?.add(Currency("2020-01-20","USD",394.74,"HRK",400.8,324.1))
            currencydata?.add(Currency("2020-01-20","USD",439.74,"HTG",450.7,430.8))
            currencydata?.add(Currency("2020-01-20","USD",436.74,"HUF",456.2,416.3))

        return currencydata
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fab.setOnClickListener { view ->
//             Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                     .setAction("Action", null)
//                     .show()


        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        (lifecycle as? LifecycleRegistry)?.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    override fun onPause() {
        super.onPause()
        visible =false
    }

    override fun onResume() {
        super.onResume()
        visible = true
    }

//         fun fetchfirst(){
//         count++
//         d("count",count.toString())
//         viewModel.requestNetwork().observe(viewLifecycleOwner, Observer {it->
//             if (it != null)
//                 binding.predictionText.setText("Success : "+it[0].date)
//             adapter.setData(it as MutableList<Currency>)
//             fetch()
//         })
//     }
//
//     fun fetch(){
//         Handler().postDelayed({
//             fetchfirst()
//         },5000)
//     }



}