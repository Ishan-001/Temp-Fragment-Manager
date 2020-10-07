package com.example.navigationdrawerfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val a = inflater.inflate(R.layout.home_fragment,container,false)
        return inflater.inflate(R.layout.home_fragment,container,false)
//        a.findViewById<>()
//        return a
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
////        view.findViewById<>()
//
//        val b = view.findViewById<Button>(R.id.shareButton)
//        b.setOnClickListener(new)
//    }
}