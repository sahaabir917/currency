package io.bechitra.currencyanalyzer.fragment

import android.app.*
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.telephony.SubscriptionManager.from
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Log.d
import android.util.Range
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.media.app.NotificationCompat
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
import java.util.Date.from
import kotlin.random.Random


class ExchangeFragment : Fragment() {

    private var range1: String? = null
    private var range2: String? = null

    private var counting1: Int = 1
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
    lateinit var countrycurrency1: io.bechitra.currencyanalyzer.network.Currency
    lateinit var countrycurrency2: io.bechitra.currencyanalyzer.network.Currency
    lateinit var currencytasks: io.bechitra.currencyanalyzer.data.Currency
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var dialog: AlertDialog
    var sb = StringBuilder("All Notifications \n")
    lateinit var builder : Notification.Builder
    private var description = "Test Notification"
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    private val channelId = "com.example.currency"




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

        setHasOptionsMenu(true)
        binding.recyclerView1.adapter = adapter

        binding.userinput.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var size = currencydata!!.size - 1
                var charecter : String = ""
                if(start == 0){
                    charecter = "1"
                }
                else{
                 charecter = s.toString()
                }
                var rate = charecter.toDouble()
                var currencyData2=  ArrayList<Currency>()


                for(i in 0..size){
                    var c = currencydata!![i].copy(currencydata!![i].date,currencydata!![i].source,currencydata!![i].rate,currencydata!![i].destination,currencydata!![i].high,currencydata!![i].low)

                    c.rate = c.rate * rate
                    currencyData2.add(c)
                }
                adapter.setData(currencyData2!! as MutableList<Currency>)

            }
        })



        // After calculator clicked

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

        // End of calculator clicked


//        after select new currency data retrive the data for making a new data of Currency


        sharedpreferences = context!!.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE)
        val date = sharedpreferences.getString("date", "29-1-19")
        val rate = sharedpreferences.getString("rate", "10.9")
        val source = sharedpreferences.getString("source", "USD")
        val destinations = sharedpreferences.getString("destination", "BANGLADESH")
        val high = sharedpreferences.getString("high", "11.90")
        val low = sharedpreferences.getString("low", "8.3")

        sharedpreferences = context!!.getSharedPreferences("1stcurrency",Context.MODE_PRIVATE)
        val mycurrency1source = sharedpreferences.getString("destination1","BDT")

        val mycurrency2source = sharedpreferences.getString("destination2","AED")
         range1 = sharedpreferences.getString("destion1range","20")
         range2 = sharedpreferences.getString("destination2range","30")
        var homecurrency = sharedpreferences.getString("homecurrency","Europe")

//        binding.currencyLong.text = homecurrency.toString()

        // shared preference e jei currency gulo age nawa hoice seita retrive kore currencydata nam er variable er vitor rakhlam

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

        // making new currency data

        //get request from server
        countrycurrency1 = Currency("2020-1-19","USD",83.3,mycurrency1source.toString(),21.9,19.9)
        countrycurrency2 = Currency("2020-1-19","USD",20.9,mycurrency2source.toString(),21.9,19.9)


        currencytask = Currency(
            date.toString(),
            source.toString(),
            rate!!.toDouble(),
            destinations.toString(),
            high!!.toDouble(),
            low!!.toDouble()
        )

// there is an exception when the project is installed from first
//                currencytask = Currency("2020-1-20","dhaka",5.4,"faridpur",7.3,3.6)


        // check korci j ager dublicate data ache kina jdi pai thole r add korbo na

       CheckDataDublicateOrNot(currencytask)
        CheckDataDublicateOrNot(countrycurrency1)
        CheckDataDublicateOrNot(countrycurrency2)

        var sizeofcurrency = currencydata!!.size - 1
        for (i in 0..sizeofcurrency){
            if(currencydata!![i].destination ==  countrycurrency1.destination){
                d("countryfromlist",currencydata!![i].destination.toString())
                d("countryfromsharedpref",countrycurrency2.destination)
                d("compare",currencydata!![i].rate.toString())
                d("rate",range1.toString())
                if( range1!!.toDouble() <= currencydata!![i].rate){
                    d("expected_highest",currencydata!![i].destination + " is high")
                }
            }
        }


        for(i in 0..sizeofcurrency){
            if(currencydata!![i].destination == countrycurrency2.destination){
                d("countryfromlist",currencydata!![i].destination.toString())
                d("countryfromsharedpref",countrycurrency2.destination)
                d("compare",currencydata!![i].rate.toString())
                d("rate",range1.toString())

                if(range2!!.toDouble() <= currencydata!![i].rate ){
                    d("expected_highest",currencydata!![i].destination + " is high")
                }
            }
        }


        //get request call by using the parameter source and destination and call it continously after 1 mint. Not completed
                //request call program


//                viewModel.requestNetwork().observe(viewLifecycleOwner, Observer {it->
//            if(visible){
//                if (it != null)
//                    binding.predictionText.setText("  Updated : "+it[0].date)
//                adapter.setData(it as MutableList<Currency>)
//            }
//        })

//
//        val handler = Handler()
//        handler.postDelayed(object : Runnable {
//            override fun run() {
//                Log.d("refreshed", "refreshed")
//                var currencydatasize = currencydata!!.size - 1
//                for(i in 0..size){
//                    viewModel.requestNetwork(currencydata!![i].source,currencydata!![i].destination)
//                }
//                handler.postDelayed(this, 5000)
//
//            }
//        }, 1000)





        //check here is the notification is on or not if on then take the required action


        sharedpreferences = context!!.getSharedPreferences("notification settings", Context.MODE_PRIVATE)
        var ischecked = sharedpreferences.getString("ischecked", null)
        notificationManager = activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(ischecked == "yes"){

            val intent = Intent(activity,LauncherActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(activity,0,intent,FLAG_UPDATE_CURRENT)
            var size = currencydata!!.size - 1
            for (i in 0..size) {
                var randomnotificationid = Random.nextInt(100,1000000)
                if (currencydata!![i].rate >= currencydata!![i].high) {

                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                        notificationChannel = NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
                        notificationChannel.enableLights(true)
                        notificationChannel.lightColor = Color.GREEN
                        notificationChannel.enableVibration(false)
                        notificationManager.createNotificationChannel(notificationChannel)

                        builder = Notification.Builder(activity,channelId)
                            .setContentTitle("Currency notification")
                            .setContentText(currencydata!![i].destination + " is high today")
                            .setSmallIcon(R.drawable.aed)
                            .setContentIntent(pendingIntent)
                    }

                    else{
                        builder = Notification.Builder(activity)
                            .setContentTitle("Currency notification")
                            .setContentText(currencydata!![i].destination + " is high today")
                            .setSmallIcon(R.drawable.aed)
                            .setContentIntent(pendingIntent)
                    }
                    notificationManager.notify(randomnotificationid,builder.build())


                }

                else if ( currencydata!![i].rate <= currencydata!![i].low) {

                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                        notificationChannel = NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
                        notificationChannel.enableLights(true)
                        notificationChannel.lightColor = Color.GREEN
                        notificationChannel.enableVibration(false)
                        notificationManager.createNotificationChannel(notificationChannel)

                        builder = Notification.Builder(activity,channelId)
                            .setContentTitle("Alert notification")
                            .setContentText(currencydata!![i].destination + " is low today")
                            .setSmallIcon(R.drawable.aed)
                            .setContentIntent(pendingIntent)

                    }
                    else{
                        builder = Notification.Builder(activity)
                            .setContentTitle("Alert notification")
                            .setContentText(currencydata!![i].destination + " is low today")
                            .setSmallIcon(R.drawable.aed)
                            .setContentIntent(pendingIntent)
                    }
                    notificationManager.notify(randomnotificationid ,builder.build())
                }
            }
        }


        //End of check here is the notification is on or not if on then take the required action


        d("retrivedata", currencydata.toString())
        d("currencydata", currencytask.toString())
        adapter.setData(currencydata!! as MutableList<Currency>)
        layoutManager = LinearLayoutManager(activity)
        binding.recyclerView1.layoutManager = layoutManager
        binding.recyclerView1.setHasFixedSize(true)

        return binding.root
    }

    private fun CheckDataDublicateOrNot(currencytask: Currency) {
        val sharedPreferences: SharedPreferences =
            context!!.getSharedPreferences("shared preferences", MODE_PRIVATE)
        val gson = Gson()
        var json1 = sharedPreferences.getString("task list", null)
        counts = 0
        var size = currencydata!!.size - 1
        for (i in 0..size) {
            if (currencydata!![i].source == currencytask.source && currencydata!![i].destination == currencytask.destination) {
                d("dublicate", "dublicated data")
                counts += 1

            } else if (currencydata!![i] != currencytask) {
                d("notdublicated", "not dublicated")
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
            fragmentTransaction.replace(R.id.mainFrameLayout, AllExchangeFragment(),null).addToBackStack(null)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.mymenu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
     when(item?.itemId){
         R.id.settingsFragment ->{
             val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
             fragmentTransaction.replace(R.id.mainFrameLayout, SettingsFragment(),null).addToBackStack(null)
             fragmentTransaction.commit()
         }
     }
        return super.onOptionsItemSelected(item)
    }


}