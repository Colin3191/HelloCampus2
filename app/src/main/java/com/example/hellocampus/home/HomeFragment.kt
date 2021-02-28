package com.example.hellocampus.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import cn.leancloud.AVUser
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
            if (AVUser.currentUser() != null) {
                findNavController()
                    .navigate(R.id.action_homeFragment_to_addFragment)
            } else {
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show()
            }

        }
    }


}