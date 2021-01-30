package com.example.hellocampus.profile

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import cn.leancloud.AVUser
import com.example.hellocampus.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val nickName = requireActivity().findViewById<TextView>(R.id.textViewNickName)
        val buttonLogout = requireActivity().findViewById<Button>(R.id.buttonLogout)

        val currentUser = AVUser.getCurrentUser()
        if (currentUser != null) {
            nickName.text = currentUser.username
        }
        buttonLogout.setOnClickListener {
            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle("退出当前账号")
                    .setMessage("是否要退出当前账号？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton(
                        "确认退出"
                    ) { _, _ ->
                        AVUser.logOut()
                        findNavController().navigate(R.id.action_profileDetailFragment_to_profileFragment)
                    }
                    .show()
            }

        }
    }

}