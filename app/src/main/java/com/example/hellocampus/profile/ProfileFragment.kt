package com.example.hellocampus.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cn.leancloud.AVUser
import com.example.hellocampus.R


class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val frameLayoutLogin = requireActivity().findViewById<FrameLayout>(R.id.frameLayoutLogin)
        val nickName = requireActivity().findViewById<TextView>(R.id.nickName)
        val userIcon = requireActivity().findViewById<ImageView>(R.id.icon2)
        frameLayoutLogin.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_profileFragment_to_loginFragment)
        }

        val currentUser = AVUser.getCurrentUser()
        if (currentUser != null) {
            nickName.text = currentUser.username
            userIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_launcher_foreground))
            frameLayoutLogin.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_profileDetailFragment)
            }
        }
    }

}