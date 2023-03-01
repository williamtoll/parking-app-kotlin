package com.melbourne.parking.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.melbourne.parking.databinding.FragmentItemBinding
import com.melbourne.parking.model.ParkingMeter

class ParkingRecyclerViewAdapter(
    private var values: List<ParkingMeter>, private val listener: OnItemClickListener
) : RecyclerView.Adapter<ParkingRecyclerViewAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: ParkingMeter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = if(item.hasCreditCard) "YES" else "NO"
        holder.contentView.text = item.streetName
        holder.itemView.setOnClickListener { listener?.onItemClick(item) }
    }


    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}



