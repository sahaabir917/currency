package io.bechitra.currencyanalyzer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import io.bechitra.currencyanalyzer.databinding.ActivityMainBinding
import io.bechitra.currencyanalyzer.fragment.AllExchangeFragment
import io.bechitra.currencyanalyzer.fragment.CalenderFragment
import io.bechitra.currencyanalyzer.fragment.ExchangeFragment
import io.bechitra.currencyanalyzer.fragment.GrowthFragment
import io.bechitra.currencyanalyzer.viewmodel.MainActivityViewModel
import io.bechitra.mlkit.model.LinearRegression
import io.bechitra.mlkit.preprocessing.Builder
import io.bechitra.mlkit.preprocessing.Encoder

class MainActivity : AppCompatActivity() {
    private lateinit var viewBind : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        viewBind.lifecycleOwner = this
        viewBind.viewModel = MainActivityViewModel()
        attachFragment(ExchangeFragment())

        viewBind.growthTab.setOnClickListener {
            attachFragment(GrowthFragment())

        }

        viewBind.exchangeTab.setOnClickListener{
            attachFragment(AllExchangeFragment())
        }

        viewBind.calenderTab.setOnClickListener{
            attachFragment(CalenderFragment())
        }

        val x = Array(4){ DoubleArray(1)}
        x[0][0] = 1.0
        x[1][0] = 2.0
        x[2][0] = 3.0
        x[3][0] = 4.0

        val y = doubleArrayOf(8.0, 11.0, 14.0, 17.0)

        val encoder = Encoder(Builder().transformMatrixWithInitial(x), 1.0, 0.0)
        val features = encoder.getNormalizeMatrix(true)

        val test = encoder.getNormalizeArray(doubleArrayOf(1.0, 5.0), true)

        val regression = LinearRegression()
        regression.fit(features, y);
        Log.d("predict", "predicted output : "+regression.predict(test))
        print("hello world")

    }

    fun attachFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFrameLayout, fragment)
        fragmentTransaction.commit()
    }
}
