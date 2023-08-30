package com.example.retrofitrxjavakotlincyrptoapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitrxjavakotlincyrptoapp.R
import com.example.retrofitrxjavakotlincyrptoapp.model.CryptoModel

class RecyclerViewAdapter(
    private val cryptoList: ArrayList<CryptoModel>,
    private val listener: Listener
) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }

    private val colors = arrayOf("#3F51B5", "#2196F3", "#009688", "#4CAF50", "#FF9800", "#795548", "#9C27B0")

    class RowHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(cryptoModel: CryptoModel, colors: Array<String>, position: Int) {
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))

            val textName = itemView.findViewById<TextView>(R.id.text_name)
            textName.text = cryptoModel.currency

            val textPrice = itemView.findViewById<TextView>(R.id.text_price)
            textPrice.text = cryptoModel.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)
        return RowHolder(view)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onItemClick(cryptoList[position])
        }
        holder.bind(cryptoList[position], colors, position)
    }
}
