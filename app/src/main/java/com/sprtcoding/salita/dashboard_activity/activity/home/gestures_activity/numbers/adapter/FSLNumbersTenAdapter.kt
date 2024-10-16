package com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.numbers.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.sprtcoding.salita.R
import com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.ViewFSLGestures
import com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.model.FSLItem

class FSLNumbersTenAdapter(private val items: List<FSLItem>, private val context: Context) : RecyclerView.Adapter<FSLNumbersTenAdapter.ViewHolder>() {
    // ViewHolder class to bind the views
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val anim: LottieAnimationView = itemView.findViewById(R.id.anim)
        val textView: TextView = itemView.findViewById(R.id.tv_title)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.number_10_1000, parent, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.anim.setAnimation(item.img)
        holder.anim.playAnimation() // Start the animation
        val displayText = item.title.let { sign -> holder.textView.context.getString(sign) }
        holder.textView.text = displayText

        holder.itemView.setOnClickListener{
            context.startActivity(Intent(context, ViewFSLGestures::class.java)
                .putExtra("fsl_type", "numbers")
                .putExtra("sign", displayText)
                .putExtra("anim", item.img))
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size
}