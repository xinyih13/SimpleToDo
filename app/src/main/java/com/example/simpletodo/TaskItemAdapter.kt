package com.example.simpletodo

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import android.R

import android.view.LayoutInflater
import android.widget.TextView


class TaskItemAdapter(val listOfItems: List<String>, val longClickListener: OnLongClickListener, val ClickListener: OnClickListener):
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>(){

    interface OnLongClickListener{
        fun onItemLongClicked(position: Int)
    }

    interface OnClickListener{
        fun onItemClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val item = listOfItems.get(position)
        // Set item views based on your views and data model
        holder.testView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //store references to elements
        val testView: TextView

        init {
            testView = itemView.findViewById(android.R.id.text1)

            //itemview is a row
            itemView.setOnLongClickListener{
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }

            itemView.setOnClickListener{
                ClickListener.onItemClicked(adapterPosition)
                true
            }
        }
    }
}