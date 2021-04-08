package com.eit.stonks.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eit.stonks.R
import com.eit.stonks.model.StonksResponse

class StonksAdapter(private var stonksList: List<StonksResponse>,
                    private val stonksDelegate: StonksDelegate) :
        RecyclerView.Adapter<StonksAdapter.StonksViewHolder>() {

    interface StonksDelegate {
        fun showCompanyNames(weatherResult: StonksResponse)
    }

    class StonksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val companyName: TextView = itemView.findViewById(R.id.tv_stockName)
    }

    fun onBind(dataItem: StonksResponse, delegate: StonksDelegate) {
//        companyName.text = dataItem.
        itemView.setOnClickListener {
            delegate.showCompanyNames(dataItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StonksAdapter.StonksViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.stocks_item_layout, parent, false)
        return StonksViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StonksAdapter.StonksViewHolder, position: Int) {
        holder.onBind(stonksList[position], stonksDelegate)
    }

    override fun getItemCount(): Int = stonksList.size
}