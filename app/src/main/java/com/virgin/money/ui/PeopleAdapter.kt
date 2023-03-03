package com.virgin.money.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.virgin.money.data.database.entity.People
import com.virgin.money.databinding.ItemPeopleBinding

/**
 * Adapter for people listing
 */
class PeopleAdapter(private val peopleList: ArrayList<People>,
                    private var onItemClicked: ((people: People) -> Unit)
) :
    RecyclerView.Adapter<PeopleAdapter.DataViewHolder>(),
    Filterable {
    var filteredList: MutableList<People> = peopleList
    inner class DataViewHolder(val binding: ItemPeopleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: People) {
            // data-binding to view
            binding.profile = item
            itemView.setOnClickListener {
                onItemClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :DataViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPeopleBinding.inflate(inflater, parent, false)
        return DataViewHolder(binding)
    }



    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount(): Int = filteredList.size

    fun addData(list: List<People>) {
        filteredList.addAll(list)
    }

    /**
     * Search feature for people
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString() ?: ""
                filteredList = if (query.isEmpty()) {
                    peopleList
                } else {
                    peopleList.filter {
                        it.firstName!!.startsWith(query, true)
                    }.toMutableList()
                }
                return FilterResults().apply {
                    values = filteredList
                }
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as MutableList<People>
                notifyDataSetChanged()
            }
        }
    }

}