package com.adut.pasar.app.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object FunctionUtil {
    fun doubleToRupiahString(number: Double): String? {
        val currencySymbol = "Rp"
        val locale = Locale("in", "ID")
        val rupiahFormat = NumberFormat.getCurrencyInstance(locale) as DecimalFormat
        rupiahFormat.positivePrefix = "$currencySymbol "
        rupiahFormat.negativePrefix = "$currencySymbol -"
        rupiahFormat.maximumFractionDigits = 0
        return rupiahFormat.format(number)
    }
}