package io.bechitra.currencyanalyzer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import io.bechitra.currencyanalyzer.R
import io.bechitra.currencyanalyzer.fragment.ExchangeFragment
import io.bechitra.currencyanalyzer.network.Currency

class CurrencyCardAdapter(private val context:Context,listener: Itemclicklistener ): RecyclerView.Adapter<CurrencyCardAdapter.CurrencyCardViewHolder>() {
    private var dataSet :MutableList<io.bechitra.currencyanalyzer.network.Currency>?=null
    private var params : RelativeLayout.LayoutParams? = null
    lateinit var currencydata: MutableList<io.bechitra.currencyanalyzer.network.Currency>
    var itemClickListerner: Itemclicklistener? = null
    init {
        params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        dataSet = mutableListOf<io.bechitra.currencyanalyzer.network.Currency>()
        this.itemClickListerner = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyCardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.currency_card,  null)
        view.layoutParams = params

        return CurrencyCardViewHolder(view)
    }

    override fun getItemCount(): Int {
        if(dataSet == null)
            return 0
        return dataSet!!.size
    }

    public fun setData(data: MutableList<io.bechitra.currencyanalyzer.network.Currency>){
        dataSet = data
//        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CurrencyCardViewHolder, position: Int) {
        val currency = dataSet?.elementAt(position)
//        holder.currencyRate.setText("$"+currency?.rate)
        holder.currencyShort.setText("$"+currency?.source)
        holder.currencyLong.setText("$"+currency?.destination)
        holder.currencyRate.setText("$"+currency?.rate)
        holder.itemView.setOnClickListener {
            currency?.let { it1 -> itemClickListerner!!.onClickItemClick(it, it1) }

            val fragmentTransaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.mainFrameLayout, ExchangeFragment())
            fragmentTransaction.commit()
        }

    }

    fun setDatas(mutableList: MutableList<Currency>) {
        dataSet!!.addAll(mutableList)
    }


//    fun deleteall(it: MutableList<io.bechitra.currencyanalyzer.network.Currency>) {
//        it.clear()
//    }


    class CurrencyCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var imageIcon: ImageView
        lateinit var currencyShort:TextView
        lateinit var currencyLong:TextView
        lateinit var currencyRate:TextView

        init {
            imageIcon = itemView.findViewById(R.id.flagImage)
            currencyShort = itemView.findViewById(R.id.currencyShort)
            currencyLong = itemView.findViewById(R.id.currencyLong)
            currencyRate = itemView.findViewById(R.id.amountText)
        }
    }

    interface Itemclicklistener{
        fun onClickItemClick(view:View, data: io.bechitra.currencyanalyzer.network.Currency)
    }
}


