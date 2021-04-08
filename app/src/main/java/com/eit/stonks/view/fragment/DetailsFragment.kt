package com.eit.stonks.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.eit.stonks.R
import com.eit.stonks.model.StonksResponse

class DetailsFragment: Fragment() {

    private lateinit var backButton: ImageView
    private lateinit var companyName: TextView
    private lateinit var stockPrize: TextView
    private lateinit var priceChange: TextView
    private lateinit var percentChange: TextView
    private lateinit var companySymbol: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.details_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton = view.findViewById(R.id.iv_back)

            backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        companyName = view.findViewById(R.id.tv_companyName)
        stockPrize = view.findViewById(R.id.tv_stockPrize)
        priceChange = view.findViewById(R.id.tv_change)
        percentChange = view.findViewById(R.id.tv_percent)
        companySymbol = view.findViewById(R.id.tv_symbol)

        arguments?.let { bundle ->
            val stonk:StonksResponse? = bundle.getParcelable(KEY_STOCKS_DATA)
            stonk?.let {
                companyName.text = it.Name
                stockPrize.text = ("$" + it.LastPrice.toString())
                priceChange.text = it.Change.toString()
                percentChange.text = ("(" + it.ChangePercent.toString() + "% )")
                companySymbol.text = ("(" + it.Symbol + ")")

                if(it.Change > 0 )
                {
                    priceChange.setTextColor(Color.parseColor("#4CAF50"))
                    percentChange.setTextColor(Color.parseColor("#4CAF50"))
                }
                else {
                    priceChange.setTextColor(Color.parseColor("#f44336"))
                    percentChange.setTextColor(Color.parseColor("#f44336"))
                }
            }
        }

    }

    companion object {
        const val KEY_STOCKS_DATA = "WeatherListFragment_KEY_DATA"
        fun newInstance(stonksResponse:StonksResponse): DetailsFragment {
            return DetailsFragment().also {
                val bundle = Bundle().also { bundle ->
                    bundle.putParcelable(KEY_STOCKS_DATA, stonksResponse)
                }
                it.arguments = bundle
            }
        }
    }


}