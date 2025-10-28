package com.example.pr21_voroshilov

import android.content.Context
import android.content.SharedPreferences
import android.icu.text.DateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Space
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatSpinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Calendar
import kotlin.collections.ArrayList

class InputFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var calendar: CalendarView
    lateinit var timepicker: TimePicker
    lateinit var date: AppCompatButton
    lateinit var mainTextView: TextView
    lateinit var time: AppCompatButton
    lateinit var moveButton: AppCompatButton
    lateinit var spinner: Spinner
    lateinit var saveButton: AppCompatButton
    lateinit var inputName:EditText
    lateinit var checkBox: CheckBox
    lateinit var category: AppCompatSpinner
    lateinit var pref: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_input, container, false)

        calendar = view.findViewById(R.id.calendar)
        timepicker = view.findViewById(R.id.timepicker)
        mainTextView = view.findViewById(R.id.mainTextView)
        date = view.findViewById(R.id.date)
        time = view.findViewById(R.id.time)
        spinner = view.findViewById(R.id.category)
        timepicker.isVisible = false
        moveButton = view.findViewById(R.id.move_button)
        saveButton = view.findViewById(R.id.save_button)
        inputName = view.findViewById(R.id.input_name)
        checkBox = view.findViewById(R.id.checkBox)
        category = view.findViewById(R.id.category)

        pref = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)

        val textJsonList = "${pref.getString("email","")}_list"

        val jsonGet = pref.getString(textJsonList, "") ?: ""
        var listcases = arrayListOf<CaseClass>()
        if (jsonGet.isNotEmpty()) listcases = Gson().fromJson(jsonGet, object : TypeToken<List<CaseClass>>() {}.type ) ?: arrayListOf()

        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.categories, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        var dateText = ""

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            dateText = "${dayOfMonth}.${month}.${year}"
        }

        saveButton.setOnClickListener {

            val timeText = "${timepicker.hour}:${timepicker.minute}"

            if (inputName.text.toString().isNotEmpty() && dateText.isNotEmpty() && timeText.isNotEmpty()) {
                val case = CaseClass(
                    inputName.text.toString(),
                    dateText,
                    timeText,
                    checkBox.isChecked,
                    category.selectedItem.toString()
                )
                listcases.add(case)
            }
            else{
                Snackbar.make(view, R.string.message_reg_task, Snackbar.LENGTH_LONG).show()
            }
        }

        moveButton.setOnClickListener {

            val json = Gson().toJson(listcases)
            pref.edit().putString(textJsonList, json).apply()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OutputFragment())
                .commit()
        }



        return view
    }

    override fun onStart() {
        super.onStart()
        date.setOnClickListener{
            calendar.isVisible = true
            timepicker.isVisible = false
            val params = mainTextView.layoutParams as ConstraintLayout.LayoutParams
            params.topToBottom = R.id.calendar
            mainTextView.layoutParams = params
        }

        time.setOnClickListener{
            timepicker.isVisible = true
            calendar.isVisible = false
            val params = mainTextView.layoutParams as ConstraintLayout.LayoutParams
            params.topToBottom = R.id.timepicker
            mainTextView.layoutParams = params
        }
    }
}