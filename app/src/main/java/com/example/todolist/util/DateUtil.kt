package com.example.todolist.util

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*


private val Format_TIME = "dd/MM/yyyy hh:mm:ss"

fun dateToString(date: Long?) : String{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date!!
    return calendar[Calendar.DAY_OF_MONTH].toString() + "/" +
            (calendar[Calendar.MONTH] + 1 ).toString() + "/" +
            calendar[Calendar.YEAR].toString()
}

fun dateToLong (date: String) : Long {
    val dateFormat = SimpleDateFormat(Format_TIME, Locale.getDefault())
    return dateFormat.parse("$date 00:00:00").time
}

fun dateToday(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()
    return calendar[Calendar.DAY_OF_MONTH].toString() + "/" +
            (calendar[Calendar.MONTH] + 1).toString() + "/" +
            calendar[Calendar.YEAR].toString()

}

fun dateToDialog(context: Context, datePicker: DatePickerDialog.OnDateSetListener?) : DatePickerDialog{
    val calendar = Calendar.getInstance()
    return DatePickerDialog(
        context,
        datePicker,
        calendar[Calendar.YEAR],
        calendar[Calendar.MONTH],
        calendar[Calendar.DAY_OF_MONTH],
    )
}

fun dateToString(year: Int, monthOfYear: Int, dayOfMonth: Int): String? {
    return dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
}
