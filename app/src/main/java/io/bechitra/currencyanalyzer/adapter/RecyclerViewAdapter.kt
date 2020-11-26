package io.bechitra.currencyanalyzer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.bechitra.currencyanalyzer.R
import io.bechitra.currencyanalyzer.network.Currency
import io.bechitra.currencyanalyzer.room.entity.currencysettings
import kotlinx.android.synthetic.main.fragment_settings.view.*
import kotlinx.android.synthetic.main.recyclerview_row.view.*

class RecyclerViewAdapter(val listener : RowClickListener) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    var items = ArrayList<currencysettings>()

    fun setListData(data:ArrayList<currencysettings>){
        this.items = data
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerViewAdapter.MyViewHolder {
        val inflater  = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row,parent,false)
        return MyViewHolder(inflater,listener)
    }

    override fun getItemCount(): Int {
      return items.size
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            listener.onItemClick(items[position])
        }
        holder.bind(items[position])

    }




    class MyViewHolder(view: View, val listener: RowClickListener) : RecyclerView.ViewHolder(view) {
        val countryname = view.countryname
        val destinationcountry = view.desitinationnname
        val avoberange = view.countrycurrencyhigh
        val type = view.currencytype1
        val deletebtn = view.deletebutton

        fun bind(currencysettings: currencysettings){
            countryname.text = currencysettings.country
            avoberange.text =   currencysettings.highrate.toString()
            destinationcountry.text = currencysettings.destinationcountry
            type.text = currencysettings.type
            deletebtn.setOnClickListener {
                listener.onDelete(currencysettings)
            }
        }

    }

    interface RowClickListener{
        fun onDelete(currencysettings: currencysettings)
        fun onItemClick(currencysettings: currencysettings)
    }

}


//
//package io.bechitra.currencyanalyzer.adapter
//
//import android.content.Context
//import android.util.Log.d
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.RelativeLayout
//import android.widget.TextView
//import androidx.fragment.app.FragmentActivity
//import androidx.recyclerview.widget.RecyclerView
//import io.bechitra.currencyanalyzer.R
//import io.bechitra.currencyanalyzer.fragment.ExchangeFragment
//import io.bechitra.currencyanalyzer.network.Currency
//import io.bechitra.currencyanalyzer.room.entity.currencysettings
//
//
//class RecyclerViewAdapter(private val context:Context, listener: Itemclicklistener ): RecyclerView.Adapter<RecyclerViewAdapter.CurrencyCardViewHolder>() {
//    private var dataSet :MutableList<currencysettings>?=null
//    private var params : RelativeLayout.LayoutParams? = null
//    lateinit var currencydata: MutableList<io.bechitra.currencyanalyzer.network.Currency>
//    var itemClickListerner: Itemclicklistener? = null
//    init {
//        params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
//        dataSet = mutableListOf<currencysettings>()
//        this.itemClickListerner = listener
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyCardViewHolder {
//        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row, null)
//        view.layoutParams = params
//
//        return CurrencyCardViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        if(dataSet == null)
//            return 0
//
//        else{
//            return dataSet!!.size
//        }
//    }
//
//    public fun setData(data: MutableList<currencysettings>){
//        dataSet = data
////        dataSet.addAll(data)
//        notifyDataSetChanged()
//    }
//
////    override fun onBindViewHolder(holder: CurrencyCardViewHolder, position: Int) {
////        val currency = dataSet?.elementAt(position)
////        holder.currencyShort.setText("$"+currency?.source)
////
////    }
//
//    fun setDatas(mutableList: MutableList<currencysettings>) {
//        dataSet!!.addAll(mutableList)
//    }
//
//
////    fun deleteall(it: MutableList<io.bechitra.currencyanalyzer.network.Currency>) {
////        it.clear()
////    }
//
//
//    class CurrencyCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        lateinit var countryname:TextView
//        lateinit var countryhigh:TextView
//        lateinit var countrylow:TextView
//        lateinit var deletebutton : Button
//
//        init {
//            countryname = itemView.findViewById(R.id.countryname)
//            countryhigh = itemView.findViewById(R.id.countrycurrencyhigh)
//            countrylow = itemView.findViewById(R.id.countrycurrencylow)
//            deletebutton = itemView.findViewById(R.id.deletebutton)
//        }
//    }
//
//    interface Itemclicklistener{
//        fun ondelete(view:View, data: io.bechitra.currencyanalyzer.network.Currency)
//        fun onclick(view:View, data : io.bechitra.currencyanalyzer.network.Currency)
//    }
//
//    override fun onBindViewHolder(holder: RecyclerViewAdapter.CurrencyCardViewHolder, position: Int) {
//                val currency = dataSet?.elementAt(position)
//                   holder.countryname.text = currency!!.country
//                    holder.countryhigh.text = currency!!.highrate.toString()
//                    holder.countrylow.text =currency!!.lowrate.toString()
//    }
//}
//
//
