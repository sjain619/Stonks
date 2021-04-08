package com.eit.stonks.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.eit.stonks.R

class DetailsFragment: Fragment() {

    private lateinit var backButton: ImageView
    private lateinit var companyName: TextView
    private lateinit var stockPrize: TextView
    private lateinit var priceChange: TextView
    private lateinit var percentChange: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.details_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }


}