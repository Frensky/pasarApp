package com.adut.pasar.app.util

import java.lang.Exception
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

    fun rupiahStringToDouble(input: String): Double {
        var result = 0.0
        var data = input.replace("Rp","")
        data = data.replace(".","")
        data = data.trim()

        try{
            result = data.toDouble()
        }
        catch(e:Exception){

        }

        return result
    }

    fun formatStringToRupiah(text: String): String? {
        val strPlainText: String = toIntRupiah(text) + ""
        return if (strPlainText.trim { it <= ' ' }.length > 0) {
            doubleToRupiahString(strPlainText.toDouble())
        } else {
            ""
        }
    }


    private fun toIntRupiah(rupiah: String): String? {
        return try {
            rupiah.replace("[.,Rp ]".toRegex(), "").toLong().toString() + ""
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            ""
        }
    }
}