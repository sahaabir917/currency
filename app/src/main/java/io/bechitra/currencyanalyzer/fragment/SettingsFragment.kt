package io.bechitra.currencyanalyzer.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import io.bechitra.currencyanalyzer.R
import io.bechitra.currencyanalyzer.network.Currency
import io.bechitra.currencyanalyzer.room.appDb
import io.bechitra.currencyanalyzer.viewmodel.ExchangeFragmentViewModel
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment() {
    private lateinit var db: appDb
    private var itemposition3: Int =0
    var itemposition1 = 0
    var itemposition2 = 0
    private var startPoint1: Int = 0
    var endPoint1 = 0
    var currencydata: ArrayList<io.bechitra.currencyanalyzer.network.Currency>? = null
    lateinit var sharedpreferences: SharedPreferences
    private var options: MutableList<String>
    var startPoint = 0
    var endPoint = 0
    init {
        this.currencydata = mutableListOf<Currency>() as ArrayList<Currency>
        this.options = mutableListOf<String>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)


        createdata()
         db = Room.databaseBuilder(
            activity!!,
            appDb::class.java,
            "currencysettings"
        ).build()




        sharedpreferences = context!!.getSharedPreferences("notification settings", Context.MODE_PRIVATE)
        var ischecked = sharedpreferences.getString("ischecked", null)

        if(ischecked == "yes"){
            notificationswitch.isChecked = true
            abc.text = "Notification (on)"
        }

        else if(ischecked == "no" ){
            notificationswitch.isChecked = false
            abc.text = "Notification (off)"
        }

        //saved the setting of notification is on or not

            notificationswitch.setOnCheckedChangeListener{CompoundButton, onSwitch->
            if(notificationswitch.isChecked){
                abc.text = "Notification (on)"
                val sharedpreferences: SharedPreferences = context!!.getSharedPreferences("notification settings", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedpreferences.edit()
                editor.putString("ischecked", "yes")
                editor.commit()
            }
            else if(!notificationswitch.isChecked){
                abc.text = "Notification (off)"
                val sharedpreferences: SharedPreferences = context!!.getSharedPreferences("notification settings", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedpreferences.edit()
                editor.putString("ischecked", "no")
                editor.commit()
            }
      }

        // end of saved the setting of notification is on or not


        var currencyDatasize = currencydata!!.size - 1


        for(i in 0..currencyDatasize){
            options.add(i,currencydata!![i].destination)
        }

//        sp_option3.adapter = ArrayAdapter<String>(activity!!,android.R.layout.simple_list_item_1,options)
        sp_option1.adapter = ArrayAdapter<String>(activity!!,android.R.layout.simple_list_item_1,options)
        sharedpreferences = context!!.getSharedPreferences("1stcurrency",Context.MODE_PRIVATE)
        val positions1 = sharedpreferences.getString("destination1position","1")
        val position2 = sharedpreferences.getString("destination2position","1")
        val position3 = sharedpreferences.getString("homecurrencyposition","1")
        val range1 =  sharedpreferences.getString("destion1range","0")
        val range2 =  sharedpreferences.getString("destination2range","0")
        seekbarresult1.text = range1
        seekbarresult2.text = range2
        sp_option1.setSelection(positions1!!.toInt())
//        sp_option3.setSelection(position3!!.toInt())
//        sp_option3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                sp_result3!!.text  = options.get(position).toString()
//                itemposition3 = position
//            }
//        }


        sp_option1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sp_result1!!.text  = options.get(position).toString()
                itemposition1 = position
            }
        }

        sp_option2.adapter = ArrayAdapter<String>(activity!!,android.R.layout.simple_list_item_1,options)
        sp_option2.setSelection(position2!!.toInt())
        sp_option2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sp_result2!!.text  = options.get(position).toString()
                itemposition2 = position
            }
        }



        seekbar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekbarresult1.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
               if(seekBar!=null){
                   startPoint = seekBar.progress
               }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if(seekBar != null){
                    endPoint = seekBar.progress
                }
            }
        })

        seekbar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekbarresult2.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if(seekBar!=null){
                    startPoint1 = seekBar.progress
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if(seekBar != null){
                    endPoint1 = seekBar.progress
                }
            }
        })


        saveButton.setOnClickListener {
            var sharedpreferences: SharedPreferences = context!!.getSharedPreferences("1stcurrency", Context.MODE_PRIVATE)
            var editor: SharedPreferences.Editor = sharedpreferences.edit()
            editor.putString("destination1", sp_result1.text.toString())

            editor.putString("destion1range",seekbarresult1.text.toString())
            editor.putString("destination1position",itemposition1.toString())
            editor.putString("destination2",sp_result2.text.toString())
            editor.putString("destination2range",seekbarresult2.text.toString())
            editor.putString("destination2position",itemposition2.toString())
//            editor.putString("homecurrency",sp_result3.toString())
            editor.putString("homecurrencyposition",itemposition3.toString())
            editor.commit()
        }



    }



    private fun createdata() {
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
    }
}