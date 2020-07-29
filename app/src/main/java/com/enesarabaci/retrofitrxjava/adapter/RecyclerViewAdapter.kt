package com.enesarabaci.retrofitrxjava.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.enesarabaci.retrofitrxjava.R
import com.enesarabaci.retrofitrxjava.model.Model

class RecyclerViewAdapter(var models : ArrayList<Model>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.recycler_view_adapter_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.flightNumber?.text = models.get(position).flight
        holder.missionName?.text = models.get(position).mission
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var flightNumber : TextView? = null
        var missionName : TextView? = null
        init {
            flightNumber = view.findViewById(R.id.flightNumber)
            missionName = view.findViewById(R.id.missionName)
        }

    }

}