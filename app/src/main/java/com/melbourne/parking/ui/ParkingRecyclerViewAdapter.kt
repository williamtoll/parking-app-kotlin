package com.melbourne.parking.ui

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.melbourne.parking.R
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
//        holder.contentView.text = if(item.hasCreditCard) "Credit Card Accepted" else "NO Credit Card"

        if (item.hasCreditCard) {
            holder.iconView.setImageResource(R.drawable.ic_payment)
        } else {
            holder.iconView.setImageResource(R.drawable.ic_cash)
        }

        holder.contentView.text = "Street: ${item.streetName}"
        holder.itemView.setOnClickListener { listener?.onItemClick(item) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
        val iconView: ImageView = binding.imagePayment
        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}



