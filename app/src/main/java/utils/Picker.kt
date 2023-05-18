package utils

import android.content.Context
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class Picker(private var fragmentManager: FragmentManager,private var editText: EditText) {
    init {
        makeDatPicker()
    }

    fun makeTimePicker() {
        val timePicker: MaterialTimePicker =
            MaterialTimePicker.Builder().setTitleText("Select Time")
                .setTimeFormat(TimeFormat.CLOCK_24H).build()
        timePicker.show(fragmentManager, "time Picker")
        timePicker.addOnPositiveButtonClickListener {
            hour = timePicker.hour
            minute = timePicker.minute
            val result = "$fullDate , $hour : $minute"
            editText.setText(result)
        }
    }

    private fun makeDatPicker() {
        val DatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("select date").build()
        DatePicker.show(fragmentManager, "date Picker")

        DatePicker.addOnPositiveButtonClickListener{
            val outputDateFormat = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault())
            outputDateFormat.timeZone = TimeZone.getTimeZone("UTC")
            fullDate = outputDateFormat.format(it)
            makeTimePicker()

        }
    }
}