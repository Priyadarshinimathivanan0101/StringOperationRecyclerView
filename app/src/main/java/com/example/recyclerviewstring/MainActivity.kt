package com.example.recyclerviewstring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    companion object {
        var isResultPage = false
        var isFragB = false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            addFragmentA()
        }
    }
    private fun addFragmentA() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragmentA = FragmentA()
        fragmentTransaction.replace(R.id.fragment_container, fragmentA)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        if(!isResultPage) {
            super.onBackPressed()
        }
        else
        {
            supportFragmentManager.popBackStack()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, FragmentA())
            fragmentTransaction.commit()
            isResultPage = false
        }
    }
}