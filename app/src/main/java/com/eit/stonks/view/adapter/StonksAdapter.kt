package com.eit.stonks.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eit.stonks.R
import com.eit.stonks.model.StonksResponse

class StonksAdapter(private var stonksList: List<StonksResponse>,
                    private val stonksDelegate: StonksDelegate) :
        RecyclerView.Adapter<StonksAdapter.StonksViewHolder>(), Filterable {

    interface StonksDelegate {
        fun showCompanyNames(weatherResult: StonksResponse)
    }
    private var filterList = listOf<StonksResponse>()

    fun updateList(fetched: List<StonksResponse>){
        stonksList = fetched
        notifyDataSetChanged()
    }

    class StonksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val companyName: TextView = itemView.findViewById(R.id.tv_stockName)

        fun onBind(dataItem: StonksResponse, delegate: StonksDelegate) {
        companyName.text = dataItem.Name
            itemView.setOnClickListener {
                delegate.showCompanyNames(dataItem)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StonksAdapter.StonksViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.stocks_item_layout, parent, false)
        return StonksViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StonksAdapter.StonksViewHolder, position: Int) {
        holder.onBind(filterList[position], stonksDelegate)
    }

    override fun getItemCount(): Int = filterList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filterList = if (charSearch.isEmpty()) {
                    stonksList
                } else {
                    val resultList = mutableListOf<StonksResponse>()
                    for (row in stonksList) {
                        if (row.Name.toLowerCase().contains(charSearch.toLowerCase()) ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<StonksResponse>
                notifyDataSetChanged()
            }

        }
    }
}

