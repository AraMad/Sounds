package com.orynastarkina.sounds.flows.main.fragments.adapters

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.orynastarkina.sounds.R
import com.orynastarkina.sounds.flows.main.dataLayer.utils.DefaultSounds
import com.orynastarkina.sounds.flows.main.dataLayer.utils.Sound
import com.orynastarkina.sounds.utils.isDefayultSoundsContainsValue


/**
 * Created by Oryna Starkina on 10.01.2019.
 */
class AddSoundsAdapter(
    private val items: ArrayList<Sound>,
    val listener: (position: Int) -> Unit
) :
    RecyclerView.Adapter<AddSoundsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_sound_list_item, parent, false) as ConstraintLayout

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (isDefayultSoundsContainsValue(items[position].coverPath)) {
            holder.itemView.findViewById<ImageView>(R.id.itemImage)
                .setImageResource(
                    DefaultSounds.valueOf(items[position].coverPath).getCoverId()
                )
        } else {
            // todo: load image
        }


        if (isDefayultSoundsContainsValue(items[position].title)) {
            holder.itemView.findViewById<TextView>(R.id.itemText).text = holder.itemView.context
                .getString(DefaultSounds.valueOf(items[position].title).getDescriptionId())
        } else {
            holder.itemView.findViewById<TextView>(R.id.itemText).text = items[position].title
        }

        holder.itemView.setOnClickListener {
            listener(position)
        }
    }

    class ViewHolder(view: ConstraintLayout) : RecyclerView.ViewHolder(view)
}