package com.orynastarkina.sounds.flows.main.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.orynastarkina.sounds.R
import com.orynastarkina.sounds.flows.main.dataLayer.utils.DashboardSound
import com.orynastarkina.sounds.flows.main.dataLayer.utils.DefaultSounds
import com.orynastarkina.sounds.utils.isDefayultSoundsContainsValue


/**
 * Created by Oryna Starkina on 24.12.2018.
 */

class ImageAdapter(val context: Context, val items: ArrayList<DashboardSound>) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any? = null

    override fun getItemId(position: Int): Long = 0L

    // create a new ImageView for each item referenced by the Adapter
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        return if (convertView == null) {

            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val item = inflater.inflate(R.layout.sound_item_view, null)

            if (isDefayultSoundsContainsValue(items[position].sound.coverPath)) {
                item.findViewById<ImageView>(R.id.itemImage).setImageResource(
                    DefaultSounds.valueOf(items[position].sound.coverPath).getCoverId())
            } else {
                // todo: load image
            }

            if (isDefayultSoundsContainsValue(items[position].sound.title)) {
                item.findViewById<TextView>(R.id.itemText).text =
                    context.getString(DefaultSounds.valueOf(items[position].sound.title).getDescriptionId())
            } else {
                item.findViewById<TextView>(R.id.itemText).text =
                    items[position].sound.title
            }

            item as View

        } else {
            convertView
        }
    }
}