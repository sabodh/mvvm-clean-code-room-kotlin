package com.contacts.people.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.contact.people.databinding.ItemRoomBinding
import com.contacts.people.data.local.entity.Room
import java.util.*

/**
 * Adapter for room listing
 */
class RoomAdapter(private val roomList: ArrayList<Room>) :
    RecyclerView.Adapter<RoomAdapter.DataViewHolder>() {

    class DataViewHolder(val itemBinding: ItemRoomBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        // data-binding to view
        fun bind(item: Room) {
            itemBinding.room = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinder = ItemRoomBinding.inflate(inflater, parent, false)
        return DataViewHolder(itemBinder)
    }
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(roomList[position])

    override fun getItemCount(): Int = roomList.size

    fun addData(list: List<Room>) {
        roomList.addAll(list)
    }
}