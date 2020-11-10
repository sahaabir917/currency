
package io.bechitra.currencyanalyzer.adapter

import android.content.Context
import android.util.Log.d
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

class HomePageAdapter(private val context:Context, listener: Itemclicklistener ): RecyclerView.Adapter<HomePageAdapter.CurrencyCardViewHolder>() {
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

        else{
            return dataSet!!.size
        }
    }

    public fun setData(data: MutableList<io.bechitra.currencyanalyzer.network.Currency>){
        dataSet = data
//        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CurrencyCardViewHolder, position: Int) {
        val currency = dataSet?.elementAt(position)


//       if(currency?.destination == "ALL"){
//            holder.imageIcon.setImageResource(R.drawable.ALL)
//        }
         if(currency?.destination == "AED"){
           holder.imageIcon.setImageResource(R.drawable.aed)
       }

        else if(currency?.destination == "AFN"){
           holder.imageIcon.setImageResource(R.drawable.afn)
       }


       else if(currency?.destination == "AMD"){
           holder.imageIcon.setImageResource(R.drawable.amd)
       }

       else if(currency?.destination == "AUD"){
             holder.imageIcon.setImageResource(R.drawable.aud)

         }

       else if(currency?.destination == "ANG"){
           holder.imageIcon.setImageResource(R.drawable.ang)
       }

       else if(currency?.destination == "AOA"){
           holder.imageIcon.setImageResource(R.drawable.aoa)
       }

       else if(currency?.destination == "ARS"){
           holder.imageIcon.setImageResource(R.drawable.ars)
       }

       else if(currency?.destination == "AZN"){
           holder.imageIcon.setImageResource(R.drawable.azn)
       }

       else if(currency?.destination == "BAM"){
           holder.imageIcon.setImageResource(R.drawable.bam)
       }

       else if(currency?.destination == "BBD"){
           holder.imageIcon.setImageResource(R.drawable.bbd)
       }

       else if(currency?.destination == "BHD"){
           holder.imageIcon.setImageResource(R.drawable.bhd)
       }

       else if(currency?.destination == "BIF"){
           holder.imageIcon.setImageResource(R.drawable.bif)
       }

       else if(currency?.destination == "BMD"){
           holder.imageIcon.setImageResource(R.drawable.bmd)
       }

       else if(currency?.destination == "BOB"){
           holder.imageIcon.setImageResource(R.drawable.bob)
       }

       else if(currency?.destination == "BRL"){
           holder.imageIcon.setImageResource(R.drawable.brl)
       }

       else if(currency?.destination == "BSD"){
           holder.imageIcon.setImageResource(R.drawable.bsd)
       }

       else if(currency?.destination == "BTN"){
           holder.imageIcon.setImageResource(R.drawable.btn)
       }

       else if(currency?.destination == "BWP"){
           holder.imageIcon.setImageResource(R.drawable.bwp)
       }

       else if(currency?.destination == "BYN"){
           holder.imageIcon.setImageResource(R.drawable.byn)
       }

       else if(currency?.destination == "BZD"){
           holder.imageIcon.setImageResource(R.drawable.bzd)
       }

       else if(currency?.destination == "CAN"){
           holder.imageIcon.setImageResource(R.drawable.can)
       }
       else if(currency?.destination == "CDF"){
           holder.imageIcon.setImageResource(R.drawable.cdf)
       }
       else if(currency?.destination == "CHF"){
           holder.imageIcon.setImageResource(R.drawable.chf)
       }

       else if(currency?.destination == "CLP"){
           holder.imageIcon.setImageResource(R.drawable.clp)
       }

       else if(currency?.destination == "CNY"){
           holder.imageIcon.setImageResource(R.drawable.cny)
       }

       else if(currency?.destination == "COP"){
           holder.imageIcon.setImageResource(R.drawable.cop)
       }

       else if(currency?.destination == "CRC"){
           holder.imageIcon.setImageResource(R.drawable.crc)
       }

       else if(currency?.destination == "CUC"){
           holder.imageIcon.setImageResource(R.drawable.cuc)
       }

       else if(currency?.destination == "CUP"){
           holder.imageIcon.setImageResource(R.drawable.cup)
       }

       else if(currency?.destination == "CVE"){
           holder.imageIcon.setImageResource(R.drawable.cve)
       }
       else if(currency?.destination == "CZK"){
           holder.imageIcon.setImageResource(R.drawable.czk)
       }

       else if(currency?.destination == "DFJ"){
           holder.imageIcon.setImageResource(R.drawable.dfj)
       }

       else if(currency?.destination == "DKK"){
           holder.imageIcon.setImageResource(R.drawable.dkk)
       }
       else if(currency?.destination == "DOP"){
           holder.imageIcon.setImageResource(R.drawable.dop)
       }
       else if(currency?.destination == "DZD"){
           holder.imageIcon.setImageResource(R.drawable.dzd)
       }
       else if(currency?.destination == "EGP"){
           holder.imageIcon.setImageResource(R.drawable.egp)
       }
       else if(currency?.destination == "ERN"){
           holder.imageIcon.setImageResource(R.drawable.ern)
       }
       else if(currency?.destination == "ETB"){
           holder.imageIcon.setImageResource(R.drawable.etb)
       }
       else if(currency?.destination == "EUR"){
           holder.imageIcon.setImageResource(R.drawable.eur)
       }
       else if(currency?.destination == "FJD"){
           holder.imageIcon.setImageResource(R.drawable.fjd)
       }

       else if(currency?.destination == "FKP"){
           holder.imageIcon.setImageResource(R.drawable.fkp)
       }

       else if(currency?.destination == "GBP"){
           holder.imageIcon.setImageResource(R.drawable.gbp)
       }
       else if(currency?.destination == "GEL"){
           holder.imageIcon.setImageResource(R.drawable.gel)
       }
       else if(currency?.destination == "GGP"){
           holder.imageIcon.setImageResource(R.drawable.ggp)
       }
       else if(currency?.destination == "GIP"){
           holder.imageIcon.setImageResource(R.drawable.gip)
       }
       else if(currency?.destination == "GMD"){
           holder.imageIcon.setImageResource(R.drawable.gmd)
       }
       else if(currency?.destination == "GNF"){
           holder.imageIcon.setImageResource(R.drawable.gnf)
       }

       else if(currency?.destination == "GTQ"){
           holder.imageIcon.setImageResource(R.drawable.gtq)
       }


       else if(currency?.destination == "GYD"){
           holder.imageIcon.setImageResource(R.drawable.gyd)
       }

       else if(currency?.destination == "HKD"){
           holder.imageIcon.setImageResource(R.drawable.hkd)
       }

       else if(currency?.destination == "HNL"){
           holder.imageIcon.setImageResource(R.drawable.hnl)
       }

       else if(currency?.destination == "HTG"){
           holder.imageIcon.setImageResource(R.drawable.htg)
       }

       else if(currency?.destination == "HUF"){
           holder.imageIcon.setImageResource(R.drawable.huf)
       }




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
        lateinit var calculatorImage : ImageView

        init {
            imageIcon = itemView.findViewById(R.id.flagImage)
            currencyShort = itemView.findViewById(R.id.currencyShort)
            currencyLong = itemView.findViewById(R.id.currencyLong)
            currencyRate = itemView.findViewById(R.id.amountText)
            calculatorImage = itemView.findViewById(R.id.imageview1)
        }
    }

    interface Itemclicklistener{
        fun onClickItemClick(view:View, data: io.bechitra.currencyanalyzer.network.Currency)
        fun onClickCalculatorClick(view:View, data : io.bechitra.currencyanalyzer.network.Currency)
    }
}