package com.example.multipleviews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewstring.Data
import com.example.recyclerviewstring.FragmentA
import com.example.recyclerviewstring.R
import com.example.recyclerviewstring.RecyclerViewInterface

class RecyclerViewAdapter(context: Context, list: ArrayList<Data>, var recyclerViewInterface: RecyclerViewInterface) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    private val context: Context = context
    var list: ArrayList<Data> = list

    private inner class View1ViewHolder(itemView: View, recyclerViewInterface: RecyclerViewInterface) :
        RecyclerView.ViewHolder(itemView) {
        var message: TextView = itemView.findViewById(R.id.textView)
        init {
            itemView.setOnClickListener {
                if(recyclerViewInterface != null) {
                    var pos: Int = adapterPosition

                    if(pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(pos)
                    }
                }
            }
        }
        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            message.text = recyclerViewModel.textData
        }
    }

    private inner class View2ViewHolder(itemView: View, recyclerViewInterface: RecyclerViewInterface) :
        RecyclerView.ViewHolder(itemView) {
        var message: TextView = itemView.findViewById(R.id.textView)
        init {
            itemView.setOnClickListener {
                if(recyclerViewInterface != null) {
                    var pos: Int = adapterPosition

                    if(pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(pos)
                    }
                }
            }
        }
        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            message.text = recyclerViewModel.textData
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ONE) {
            return View1ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_1, parent, false), recyclerViewInterface)
        }
        return View2ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_2, parent, false), recyclerViewInterface)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position].viewType == VIEW_TYPE_ONE) {
            (holder as View1ViewHolder).bind(position)
        } else {
            (holder as View2ViewHolder).bind(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].viewType
    }
}