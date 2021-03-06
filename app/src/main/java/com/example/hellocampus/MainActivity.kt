package com.example.hellocampus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 设置底部导航栏
        val navController = findNavController(R.id.fragment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
        // 不在"首页" 或者 "我的"页面就隐藏底部导航栏
        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.loginFragment
                || destination.id == R.id.registerFragment
                || destination.id == R.id.profileDetailFragment
                || destination.id == R.id.addFragment
                || destination.id == R.id.setTagFragment
                || destination.id == R.id.eventDetailFragment
            ) {
                bottomNavigationView.visibility =
                    View.GONE
            } else {
                bottomNavigationView.visibility =
                    View.VISIBLE
            }
        }
    }

}