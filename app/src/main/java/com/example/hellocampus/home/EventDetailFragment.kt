package com.example.hellocampus.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import cn.leancloud.AVObject
import cn.leancloud.AVQuery
import com.bumptech.glide.Glide
import com.example.hellocampus.JoinInfoDialogFragment
import com.example.hellocampus.NoticeDialogFragment
import com.example.hellocampus.R
import com.kongzue.dialogx.dialogs.CustomDialog
import com.kongzue.dialogx.interfaces.OnBindView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlin.math.log

class EventDetailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val button = requireActivity().findViewById<Button>(R.id.buttonJoin)
        var images: java.util.ArrayList<ImageView> = arrayListOf()
        val textViewTime = requireActivity().findViewById<TextView>(R.id.textViewTime)
        val textViewTitle = requireActivity().findViewById<TextView>(R.id.textViewDetailTitle)
        val textViewContent = requireActivity().findViewById<TextView>(R.id.textViewDetailContent)
        val textViewLocation =
            requireActivity().findViewById<TextView>(R.id.textViewDetailLocation)
        val textViewCount = requireActivity().findViewById<TextView>(R.id.textViewCount)
        val image1 = requireActivity().findViewById<ImageView>(R.id.imageView1)
        val image2 = requireActivity().findViewById<ImageView>(R.id.imageView2)
        val image3 = requireActivity().findViewById<ImageView>(R.id.imageView3)
        images.add(image1)
        images.add(image2)
        images.add(image3)
        // 获取首页传过来的id
        val id = requireArguments().getString("id")
        val query = AVQuery<AVObject>("Event")
        query.getInBackground(id).subscribe(object : Observer<AVObject> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: AVObject) {
                var startTime = ""
                t.getString("startTime")?.let {
                    startTime = it
                }
                var endTime = ""
                t.getString("endTime")?.let {
                    endTime = it
                }
                var time = "$startTime 至 $endTime"
                if (startTime == "" || endTime == "") {
                    time = ""
                }
                var pics: ArrayList<String>
                // 图片加载
                t.get("pics")?.let {
                    pics = it as ArrayList<String>
                    pics.forEachIndexed { index, s ->
                        images[index].visibility = View.VISIBLE
                        Glide.with(this@EventDetailFragment)
                            .load(s)
                            .into(images[index])
                    }
                }
                textViewTitle.text = t.getString("title")
                textViewContent.text = t.getString("content")
                textViewLocation.text = t.getString("location")
                textViewTime.text = time

            }

            override fun onError(e: Throwable) {
                Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onComplete() {}

        })
        // 参加活动
        button.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
//        val dialog = JoinInfoDialogFragment()
//        dialog.show(requireActivity().supportFragmentManager, "joinInfo")
        CustomDialog.build()
            .setAlign(CustomDialog.ALIGN.CENTER)
            .setCustomView(object : OnBindView<CustomDialog>(R.layout.join_info) {
                override fun onBind(dialog: CustomDialog?, v: View) {
                    val btnClose = v.findViewById<Button>(R.id.btnCancel)
                    val name = v.findViewById<EditText>(R.id.editTextName).text
                    btnClose.setOnClickListener {
                        Log.d("qqq", "onBind: $name")
                    }
                }
            }).show()
    }

}