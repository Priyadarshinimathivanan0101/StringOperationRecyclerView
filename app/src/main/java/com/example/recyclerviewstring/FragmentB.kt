package com.example.stringoperationnew

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResult
import com.example.recyclerviewstring.MainActivity
import com.example.recyclerviewstring.R
import java.lang.Exception

class FragmentB : Fragment() {
    lateinit var string1: EditText
    lateinit var string2: EditText
    private lateinit var operationButton : Button
    var operation : String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val orientation = resources.configuration.orientation
        val view = inflater.inflate(R.layout.fragment_b, container, false)
        string1 = view.findViewById(R.id.string1)
        string2 = view.findViewById(R.id.string2)
        operationButton = view.findViewById(R.id.doneButton)
        string1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                MainActivity.isFragB = true
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        if(MainActivity.isFragB) {
            operation = arguments?.getString("operation").toString()
            string1.setText(arguments?.getString("string1").toString())
            string2.setText(arguments?.getString("string2").toString())
        }
        else {
            operation = arguments?.getString("operation").toString()
        }
        if (operation.equals("Append")) {
            string2.visibility = View.VISIBLE
        }
        if(savedInstanceState != null) {
            operation = savedInstanceState.getString("operation").toString()
            string1.setText(savedInstanceState.getString("string1"))
            if(operation == "Append") {
                string2.setText(savedInstanceState.getString("string2"))
            }
            if(parentFragmentManager.backStackEntryCount >= 1) {
                parentFragmentManager.popBackStack()
            }
            val bundle = Bundle()
            bundle.putString("operation", operation)
            bundle.putString("string1", string1.text.toString())
            bundle.putString("string2", string2.text.toString())
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            val fragmentB = FragmentB()
            fragmentB.arguments = bundle
            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
                fragmentTransaction.replace(R.id.fragmentB, fragmentB)
            }
            else if(orientation == Configuration.ORIENTATION_PORTRAIT) {
                fragmentTransaction.replace(R.id.fragment_container, fragmentB)
            }
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        operationButton.text = operation
        operationButton.setOnClickListener {
            when (operation.toString()) {
                "Append" -> {
                    sendToFragment1("${string1.text} ${string2.text}")
                }
                "Reverse" -> {
                    sendToFragment1((string1.text).toString().reversed())
                }
                "Split" -> {
                    val chars: List<Char> = string1.text.toList()
                    sendToFragment1(chars.joinToString("  "))
                }
                "Split by comma" -> {
                    val chars: List<Char> = string1.text.toList()
                    sendToFragment1(chars.joinToString(","))
                }
            }
        }
        return view
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(operation != "") {
            outState.putString("operation", operation)
            outState.putString("string1", string1.text.toString())
            if (operation == "append") {
                outState.putString("string2", string2.text.toString())
            }
        }
    }
    fun sendToFragment1(output: String) {
        MainActivity.isFragB = false
        MainActivity.isResultPage = true
        setFragmentResult("0", bundleOf("output" to output))

        parentFragmentManager.popBackStack()
    }
}