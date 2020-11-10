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
            currencydata?.add(Currency("2020-01-20","USD",1.2,"afghani "))
            currencydata?.add(Currency("2020-01-20","USD",79.04,"AED"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"AFN"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"AMD"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"ANG"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"AOA"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"ARS"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"AUD"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"AZN"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"BAM"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"BDT"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"BBD"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"BHD"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"BIF"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"BMD"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"BOB"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"BRL"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"BSD"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"BTN"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"BWP"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"BYN"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"BZD"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"CAN"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"CDF"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"CHF"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"CLP"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"CNY"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"COP"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"CRC"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"CUC"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"CUP"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"CVE"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"CZK"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"DFJ"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"DKK"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"DOP"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"DZD"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"EGP"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"ERN"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"ETB"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"EUR"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"FJD"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"FKP"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"GBP"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"GEL"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"GGP"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"GIP"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"GMD"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"GNF"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"GTQ"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"GYD"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"HKD"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"HNL"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"HRK"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"HTG"))
            currencydata?.add(Currency("2020-01-20","USD",493.74,"HUF"))





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