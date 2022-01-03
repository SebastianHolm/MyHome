package com.example.edu.seho.myhome.fragments

import android.widget.DatePicker

/** @author Sebastian Holm
 *  Helper class used by the classes handling the date and bought variables
 *  contained in the storage class which is stored in the database.
 */

class SpinnerDate {

    private val month = listOf("Jan", "Feb", "Mar", "Apr", "Maj", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec")

    // Checks if the best-by date isn't earlier then the bought date.
    fun checkDate(datePicker: DatePicker, boughtPicker: DatePicker) : Boolean{
        if (datePicker.year < boughtPicker.year){
            return false
        } else if (datePicker.year == boughtPicker.year){
            if (datePicker.month < boughtPicker.month){
                return false
            } else if (datePicker.month == boughtPicker.month){
                if (datePicker.dayOfMonth < boughtPicker.dayOfMonth){
                    return false
                }
            }
        }
        return true
    }

    // Sets the best-by date based on the amount of lasting days in an already existing item
    // with the same name in the database.
    fun setDateCorrespondingToSaved(datePicker : DatePicker, boughtPicker: DatePicker, date : String, bought : String){
        val dates = date.split(" ")
        val boughtDates = bought.split(" ")

        val ydiff = dates[0].toInt() - boughtDates[0].toInt()
        val mdiff = month.indexOf(dates[1]) - month.indexOf(boughtDates[1])
        val ddiff = dates[2].toInt() - boughtDates[2].toInt()

        val yCurr = boughtPicker.year
        val mCurr = boughtPicker.month
        val dCurr = boughtPicker.dayOfMonth

        val newY = yCurr + ydiff
        val newM = mCurr + mdiff
        val newD = dCurr + ddiff

        datePicker.updateDate(newY, newM, newD)
    }

    // Returns the month matching the input int
    private fun getMonth(m : Int) : String{
        return when (m){
            0 -> "Jan"
            1 -> "Feb"
            2 -> "Mar"
            3 -> "Apr"
            4 -> "Maj"
            5 -> "Jun"
            6 -> "Jul"
            7 -> "Aug"
            8 -> "Sep"
            9 -> "Okt"
            10 -> "Nov"
            else -> "Dec"
        }
    }

    // Sets the datePickers to display the best-by date and bought date for the item.
    fun setCurrentDate(datePicker : DatePicker, boughtPicker : DatePicker, date : String, bought : String){
        var dates = date.split(" ")
        var boughtDates = bought.split(" ")

        datePicker.updateDate(dates[0].toInt(), month.indexOf(dates[1]), dates[2].toInt())
        boughtPicker.updateDate(boughtDates[0].toInt(), month.indexOf(boughtDates[1]), boughtDates[2].toInt())
    }

    // Turns the datePicker value into an int used for storing in the database.
    fun makeInt(datePicker : DatePicker) : Int{
        return datePicker.year * 10000 + datePicker.month * 100 + datePicker.dayOfMonth
    }

    // Turns the int for the date stored in the database into a string for displaying.
    fun makeString(i : Int) : String{
        var curr = i
        val d = curr % 100
        curr -= d
        var m = curr % 10000
        curr -= m
        m /= 100
        val y = curr / 10000

        return "$y ${getMonth(m)} $d"
    }
}