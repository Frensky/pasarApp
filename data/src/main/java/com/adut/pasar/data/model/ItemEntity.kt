package com.adut.pasar.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adut.pasar.domain.model.Item
import com.google.gson.annotations.SerializedName

@Entity
data class ItemEntity(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @SerializedName("title")
    var title: String? = "",

    @SerializedName("quantity")
    var quantity: Int? = 1,

    @SerializedName("quantity_type")
    var quantityType: String? = "",

    @SerializedName("buy_price")
    var buyPrice: Long? = 0,

    @SerializedName("sell_price")
    var sellPrice: Long? = 0,

    @SerializedName("barCode_id")
    var barCodeId: String? = null

) : ModelEntity<Item> {
    override fun mapToEntity() = Item(id, title ?: "", quantity ?:0,quantityType ?:"",buyPrice ?: 0, sellPrice ?: 0,barCodeId ?: "")
}

