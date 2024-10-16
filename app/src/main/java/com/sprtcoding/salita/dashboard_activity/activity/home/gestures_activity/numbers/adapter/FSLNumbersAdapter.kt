package com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.numbers.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sprtcoding.salita.R
import com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.model.FSLItem

class FSLNumbersAdapter(private val items: List<FSLItem>) : RecyclerView.Adapter<FSLNumbersAdapter.ViewHolder>() {
    // ViewHolder class to bind the views
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img)
        val textView: TextView = itemView.findViewById(R.id.tv_title)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.number_0_9, parent, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.imageView.setImageResource(item.img)
        val displayText = item.title.let { sign -> holder.textView.context.getString(sign) }
        holder.textView.text = displayText
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size
}