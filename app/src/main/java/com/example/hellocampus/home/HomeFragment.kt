package com.example.hellocampus.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.leancloud.AVObject
import cn.leancloud.AVQuery
import cn.leancloud.AVUser
import com.example.hellocampus.EventListAdapter
import com.example.hellocampus.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlin.math.log

class HomeFragment : Fragment() {
    var events = listOf<AVObject>()
    lateinit var eventListAdapter:EventListAdapter

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
        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.recyclerView)
        eventListAdapter = EventListAdapter()
        buttonAdd.setOnClickListener {
            if (AVUser.currentUser() != null) {
                findNavController()
                    .navigate(R.id.action_homeFragment_to_addFragment)
            } else {
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show()
            }

        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = eventListAdapter
        getAllEvent()
    }

    // 获取所有活动
    private fun getAllEvent() {
        val query = AVQuery<AVObject>("Event")
        query.findInBackground().subscribe(object : Observer<List<AVObject>> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: List<AVObject>) {
                eventListAdapter.submitList(t)
                if (t.isNotEmpty()){
                    Log.d("qqq", "onNext: ${t[0].getString("title")}")
                }


            }

            override fun onError(e: Throwable) {
                Log.d("qqq", "onError: $e")
            }

            override fun onComplete() {

            }

        })
    }


}