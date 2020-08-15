package com.demo.img.adapter

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adut.pasar.app.R

class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
     var itemLayout: LinearLayout
     var itemTitle: TextView
     var itemQty: TextView
     var itemPrice: TextView

    init {
        itemLayout = itemView.findViewById<View>(R.id.item_layout) as LinearLayout
        itemTitle = itemView.findViewById<View>(R.id.titleLbl) as TextView
        itemQty = itemView.findViewById<View>(R.id.qtyLbl) as TextView
        itemPrice = itemView.findViewById<View>(R.id.priceTagLbl) as TextView
    }
}