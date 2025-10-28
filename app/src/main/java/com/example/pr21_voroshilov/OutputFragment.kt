package com.example.pr21_voroshilov

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.w3c.dom.Text
import java.util.ArrayList

class OutputFragment : Fragment() {

    lateinit var rcView: RecyclerView
    lateinit var adapter: CaseAdapter
    lateinit var pref:SharedPreferences
    lateinit var backButton: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_output, container, false)
        pref = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        backButton = view.findViewById(R.id.back_btn)

        val textJsonList = "${pref.getString("email","")}_list"

        val jsonGet = pref.getString(textJsonList, "")
        val listcases = Gson().fromJson<ArrayList<CaseClass>>(jsonGet, object : TypeToken<List<CaseClass>>() {}.type )

        adapter = CaseAdapter(listcases)

        rcView = view.findViewById(R.id.recyclerView)

        rcView.adapter = adapter

        backButton.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, InputFragment())
                .commit()
        }


        return view
    }

    override fun onStart() {
        super.onStart()
    }
}