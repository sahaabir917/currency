package io.bechitra.currencyanalyzer.fragment

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.bechitra.currencyanalyzer.R
import io.bechitra.currencyanalyzer.adapter.CurrencyCardAdapter
import io.bechitra.currencyanalyzer.databinding.FragmentExchangeBinding
import io.bechitra.currencyanalyzer.network.Currency
import io.bechitra.currencyanalyzer.sharedpreference.PreConfig
import io.bechitra.currencyanalyzer.viewmodel.ExchangeFragmentViewModel
import kotlinx.android.synthetic.main.fragment_exchange.*
import java.lang.reflect.Type


class ExchangeFragment : Fragment() {
    private lateinit var binding:FragmentExchangeBinding
    private lateinit var adapter : CurrencyCardAdapter
    private lateinit var viewModel: ExchangeFragmentViewModel
    var pagenumber : Int = 1
    var isScrolling = false
    var currentItem: Int = 0
    var totalItem: Int = 0
    var scrolloutItems: Int = 0
     var counts  : Int = 0
     var visible: Boolean = true
    var count : Int = 0
    lateinit var sharedpreferences: SharedPreferences
    private var mhandaler : Handler = Handler()
    lateinit var layoutManager: RecyclerView.LayoutManager
      var currencydata: ArrayList<io.bechitra.currencyanalyzer.network.Currency>? = null
    var preConfig : PreConfig = PreConfig()
    lateinit var currencytask : io.bechitra.currencyanalyzer.network.Currency


    init {
        this.currencydata = mutableListOf<Currency>() as ArrayList<Currency>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {






        binding = FragmentExchangeBinding.inflate(inflater, container, false)
        binding.recyclerView1.layoutManager = LinearLayoutManager(inflater.context)
        adapter = CurrencyCardAdapter(inflater.context,object :CurrencyCardAdapter.Itemclicklistener{


            override fun onClickItemClick(view: View, data: Currency) {
                d("not","nothing to do")
            }

        })


        binding.recyclerView1.adapter = adapter
//        currency data will come here from the

//        currencydata = preConfig.readListFromPref(context!!)

//        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences("shared preferences", MODE_PRIVATE)
//        val gson = Gson()
//        val json = sharedPreferences.getString("tasklist", null)
//        val type: Type = object : TypeToken<ArrayList<Currency>>() {}.type
//        currencydata = gson.fromJson(json, type)
//        if (currencydata == null) {
//            currencydata = ArrayList<Currency>()
//        }

         sharedpreferences = context!!.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE)
        val date = sharedpreferences.getString("date",null)
       val rate = sharedpreferences.getString("rate",null)
        val source = sharedpreferences.getString("source",null)
        val destinations = sharedpreferences.getString("destination",null)
//        d("date",date)
//        d("rate",rate)
//        d("source",source.toString())
//        d("destination",destinations.toString())

        d("currencydata", currencydata.toString())

        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences("shared preferences", MODE_PRIVATE)
        val gson = Gson()
        var json1 = sharedPreferences.getString("task list", null)
        val type: Type = object : TypeToken<ArrayList<Currency?>?>() {}.type
        currencydata = gson.fromJson(json1,type)
        if(currencydata == null){
            currencydata = ArrayList()
        }



        currencytask = Currency(date.toString(),source.toString(),rate!!.toDouble(),destinations.toString())
//                currencytask = Currency("2020-1-20","dhaka",5.4,"faridpur")
            var size = currencydata!!.size - 1
        for (i in 0..size){
            if(currencydata!![i]==currencytask){
                d("dublicate","dublicated data")
                counts = counts+1
            }
            else if(currencydata!![i]!= currencytask){
                d("notdublicated","not dublicated")

            }
        }

        if(counts<=1){
            currencydata?.add(currencytask)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            var json = gson.toJson(currencydata)
            editor.putString("task list",json)
            editor.commit()
        }




        var json2 = sharedPreferences.getString("task list", null)
        val type1: Type = object : TypeToken<ArrayList<Currency?>?>() {}.type
        currencydata = gson.fromJson(json2,type1)
        if(currencydata == null){
            currencydata = ArrayList()
        }

//        d("jsondata",json)



        d("retrivedata",currencydata.toString())

        d("currencydata", currencytask.toString())
        adapter.setData(currencydata!! as MutableList<Currency>)
        layoutManager = LinearLayoutManager(activity)
        binding.recyclerView1.layoutManager = layoutManager

        binding.recyclerView1.setHasFixedSize(true)

        return binding.root
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
         visible =false
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