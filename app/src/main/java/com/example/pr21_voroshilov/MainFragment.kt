package com.example.pr21_voroshilov

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var button: Button
    lateinit var password: EditText
    lateinit var email: EditText
    lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        password = view.findViewById(R.id.password)
        email = view.findViewById(R.id.email)
        pref = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        button = view.findViewById(R.id.reg_button)

        email.setText(pref.getString("email",""))
        password.setText(pref.getString("password",""))


        button.setOnClickListener {

            if (email.text.isNotEmpty() && password.text.isNotEmpty()) {

                pref.edit().apply {
                    putString("password", password.text.toString())
                    putString("email", email.text.toString())
                    apply()
                }

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, InputFragment())
                    .commit()
            }
            else{
                Snackbar.make(view, R.string.message_reg, Snackbar.LENGTH_LONG).show()
            }
        }


        return view
    }

    override fun onStart() {
        super.onStart()

    }
}