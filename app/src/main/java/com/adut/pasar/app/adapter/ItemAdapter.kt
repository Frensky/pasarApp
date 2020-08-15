package com.demo.img.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adut.pasar.app.R
import com.adut.pasar.app.util.FunctionUtil
import com.adut.pasar.domain.model.Item

class ItemAdapter(
    val context: Context,
    private val data: List<Item>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class PriceState {
        BELI,
        JUAL
    }

    private var type : PriceState = PriceState.BELI


    fun setPriceType(priceType:PriceState){
        type = priceType
        notifyDataSetChanged()
    }

    private var onItemClickListener : OnItemCLickListener ?=null

    fun onClickItem(onItemCLickListene: OnItemCLickListener){
        this.onItemClickListener = onItemCLickListene
    }

    interface OnItemCLickListener{
        fun onClickItem(data: Item, position: Int)
    }

    val THRESHOLD = 130.0

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.cell_item_pasar, viewGroup, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) {
            val entity = data.get(position)
            holder.itemTitle.text = entity.title
            var qty = entity.qty
            var qtyString:String = ""+qty

            if(!entity.qtyType.isNullOrEmpty()){
                qtyString = qtyString + " " + entity.qtyType
            }

            holder.itemQty.text = qtyString

            var price = 0.0

            if(type == PriceState.BELI){
                price = (entity.beli ?: 0).toDouble()
                holder.itemPrice.text = FunctionUtil.doubleToRupiahString(price)
                holder.itemPrice.setTextColor(context.resources.getColor(R.color.beli_text))
            }
            else{
                price = (entity.jual ?: 0).toDouble()
                holder.itemPrice.text = FunctionUtil.doubleToRupiahString(price)
                holder.itemPrice.setTextColor(context.resources.getColor(R.color.jual_text))
            }

            holder.itemLayout.setOnClickListener{
                onItemClickListener?.onClickItem(entity,position)
            }
        }
    }

    override fun getItemCount(): Int = data.size
}