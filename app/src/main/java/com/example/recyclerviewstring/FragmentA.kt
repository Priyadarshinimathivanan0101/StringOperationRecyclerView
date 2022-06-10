package com.example.recyclerviewstring

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.multipleviews.RecyclerViewAdapter
import com.example.stringoperationnew.FragmentB
import java.lang.Exception

class FragmentA : Fragment(), RecyclerViewInterface {
    private lateinit var recyclerView: RecyclerView
    val dataList = ArrayList<Data>()
    lateinit var result: String
    var adapter = activity?.let { RecyclerViewAdapter(it,dataList,this) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        var view = inflater.inflate(R.layout.fragment_a, container, false)
        setHomeScreen()
        if(savedInstanceState != null) {
            result = savedInstanceState.getString("result").toString()
            MainActivity.isResultPage = savedInstanceState.getBoolean("isResultPage")
            if(MainActivity.isResultPage){
                setResultScreen(result)
            }
            else {
                setHomeScreen()
            }
        }
        else {
            setHomeScreen()
        }
        adapter = activity?.let { RecyclerViewAdapter(it,dataList,this) }
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        setFragmentResultListener("0"){ requestKey, bundle ->
            result = bundle.getString("output").toString()
            setResultScreen(result)
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isResultPage", MainActivity.isResultPage)
        try {
            outState.putString("result", result)
        }
        catch(e: Exception) {
            outState.putString("result", "")
        }
    }

    private fun passOperation(operation : String) {
        MainActivity.isFragB = false
        val orientation = resources.configuration.orientation
        val bundle = Bundle()
        bundle.putString("operation", operation)
        val transaction = parentFragmentManager.beginTransaction()
        val fragmentB = FragmentB()
        fragmentB.arguments = bundle
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.replace(R.id.fragmentB, fragmentB)
        } else if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            transaction.replace(R.id.fragment_container, fragmentB )
        }
        transaction.addToBackStack(null)
        transaction.commit()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setHomeScreen() {
        dataList.clear()
        adapter?.notifyDataSetChanged()
        val homePage = resources.getStringArray(R.array.home_page)
        val limit = homePage.size
        dataList.add(Data(RecyclerViewAdapter.VIEW_TYPE_ONE, "String Operation"))
        for(i in 0 until limit) {
                dataList.add(Data(RecyclerViewAdapter.VIEW_TYPE_TWO, homePage[i]))
        }
        adapter?.notifyDataSetChanged()
    }

    fun setResultScreen(result: String) {
        dataList.clear()
        adapter?.notifyDataSetChanged()
        dataList.add(Data(RecyclerViewAdapter.VIEW_TYPE_ONE, "RESULT"))
        dataList.add(Data(RecyclerViewAdapter.VIEW_TYPE_ONE, "$result"))
        dataList.add(Data(RecyclerViewAdapter.VIEW_TYPE_TWO, "Show Option"))
        adapter?.notifyDataSetChanged()
    }

    override fun onItemClick(position: Int) {
        val clickedValue: String = dataList.get(position).textData
        if(clickedValue in resources.getStringArray(R.array.home_page)) {
            passOperation(clickedValue)
        }
        else if(clickedValue.equals("Show Option")) {
            result = ""
            MainActivity.isResultPage = false
            setHomeScreen()
        }
    }
}

