package com.example.weather.framework.receivers.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.ItemHistoryListBinding
import com.example.weather.model.enitities.Weather

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {
    private var data: List<Weather> = arrayListOf()

    fun setData(data: List<Weather>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            ItemHistoryListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }
    inner class RecyclerItemViewHolder(private val binding: ItemHistoryListBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Weather) = with(binding) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                recyclerViewItem.text =
                    String.format("%s %d %s", data.city.city, data.temperature, data.condition)
                root.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "on click: ${data.city.city}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}