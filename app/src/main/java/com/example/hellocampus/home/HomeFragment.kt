package com.example.hellocampus.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hellocampus.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val buttonAdd = requireActivity().findViewById<FloatingActionButton>(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            findNavController()
                .navigate(R.id.action_homeFragment_to_addFragment)
        }
    }


}