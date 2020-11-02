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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.bechitra.currencyanalyzer.adapter.CurrencyCardAdapter
import io.bechitra.currencyanalyzer.databinding.FragmentAllExchangeBinding
import io.bechitra.currencyanalyzer.network.Currency
import io.bechitra.currencyanalyzer.sharedpreference.PreConfig
import io.bechitra.currencyanalyzer.viewmodel.ExchangeFragmentViewModel
import kotlinx.android.synthetic.main.fragment_all_exchange.*

class AllExchangeFragment : Fragment() {


    private lateinit var binding: FragmentAllExchangeBinding
    private lateinit var adapter : CurrencyCardAdapter
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
     var currencydata: ArrayList<io.bechitra.currencyanalyzer.network.Currency>? = null
    var preConfig : PreConfig = PreConfig()
     var currencytask : io.bechitra.currencyanalyzer.network.Currency? =null




    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllExchangeBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(inflater.context)
        adapter = CurrencyCardAdapter(inflater.context,object :CurrencyCardAdapter.Itemclicklistener{
            override fun onClickItemClick(view: View, data: Currency) {

//                currencytask = Currency(data.date,data.source,data.rate,data.destination)
//                currencydata?.add(currencytask!!)
//                d("currencydata", currencytask.toString())
//                val sharedPreferences: SharedPreferences = context!!.getSharedPreferences("shared preferences", MODE_PRIVATE)
//                val editor: SharedPreferences.Editor = sharedPreferences.edit()
//                val gson = Gson()
//                val json = gson.toJson(currencydata)
//                editor.putString("tasklist",json)
//                editor.apply()
//                editor.commit()
//                d("saved",json)

                val sharedpreferences: SharedPreferences = context!!.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedpreferences.edit()
                editor.putString("date", data.date)
                editor.putString("rate",data.rate.toString())
                editor.putString("source",data.source)
                editor.putString("destination",data.destination)
                editor.commit()

//                preConfig.writeListInPref(context!!.applicationContext,currencydata)
            }

        })
        binding.recyclerView.adapter = adapter
        viewModel = ViewModelProvider(requireActivity()).get(ExchangeFragmentViewModel::class.java)

        viewModel.requestNetwork().observe(viewLifecycleOwner, Observer {it->
            if(visible){
                if (it != null)
                    binding.predictionText.setText("  Updated : "+it[0].date)
                adapter.setData(it as MutableList<Currency>)
            }
        })
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                Log.d("refreshed", "refreshed")
                viewModel.requestNetwork()
                handler.postDelayed(this, 5000)

            }
        }, 1000)




//        fab.setBackgroundColor(R.color.colorPrimaryDark)




        layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager

        binding.recyclerView.setHasFixedSize(true)

        return binding.root
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