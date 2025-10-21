package com.example.pr21_voroshilov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.w3c.dom.Text

class OutputFragment(val list: ArrayList<CaseClass>) : Fragment() {

    lateinit var spinnerCases: Spinner
    lateinit var name:TextView
    lateinit var date:TextView
    lateinit var category: TextView
    lateinit var important: TextView
    lateinit var time:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_output, container, false)

        name = view.findViewById(R.id.name)
        date = view.findViewById(R.id.date)
        category = view.findViewById(R.id.category)
        important = view.findViewById(R.id.important)
        spinnerCases = view.findViewById(R.id.spinner_cases)
        time = view.findViewById(R.id.time)

        val list2 = arrayListOf<String>()

        var i = 0
        while( i < list.size){
            list2.add(list[i].name)
            i++
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list2)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCases.adapter = adapter

        spinnerCases.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ){
                name.text = list[position].name
                date.text = list[position].date.toString()
                category.text = list[position].category
                time.text = "${list[position].time.hour} ${list[position].time.minute}"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()
    }
}