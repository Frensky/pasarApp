package com.adut.pasar.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adut.pasar.domain.model.Item
import com.google.gson.annotations.SerializedName

@Entity
data class ItemEntity(
    @SerializedName("id")
    @PrimaryKey
    var id: Long = -1,

    @SerializedName("title")
    var title: String? = "",

    @SerializedName("buy_price")
    var buyPrice: Long? = 0,

    @SerializedName("sell_price")
    var sellPrice: Long? = 0

) : ModelEntity<Item> {

    override fun mapToEntity() = Item(id, title ?: "", buyPrice ?: 0, sellPrice ?: 0)
}

