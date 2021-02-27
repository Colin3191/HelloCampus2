package com.example.hellocampus.home

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.hellocampus.R
import com.example.hellocampus.matisse.GifSizeFilter
import com.permissionx.guolindev.PermissionX
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.filter.Filter
import java.text.ParseException
import java.util.*
import kotlin.collections.ArrayList


class AddFragment : Fragment() {
    private var startTime: String? = null
    private var endTime: String? = null
    private var selected: ArrayList<Uri> = arrayListOf()
    private var addImage1: ImageView? = null
    private var addImage2: ImageView? = null
    private var addImage3: ImageView? = null
    private var imageRemove1: ImageView? = null
    private var imageRemove2: ImageView? = null
    private var imageRemove3: ImageView? = null
    private var images: ArrayList<ImageView> = arrayListOf()
    private var imageRemoves: ArrayList<ImageView> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val editTextTitle = requireActivity().findViewById<EditText>(R.id.editTextTitle)
        val editTextLocation = requireActivity().findViewById<EditText>(R.id.editTextLocation)
        val setTag = requireActivity().findViewById<ConstraintLayout>(R.id.setTag)
        val setStartTime = requireActivity().findViewById<TextView>(R.id.startTime)
        val setEndTime = requireActivity().findViewById<TextView>(R.id.endTime)
        addImage1 = requireActivity().findViewById(R.id.image1)
        addImage2 = requireActivity().findViewById(R.id.image2)
        addImage3 = requireActivity().findViewById(R.id.image3)
        imageRemove1 = requireActivity().findViewById(R.id.imageRemove1)
        imageRemove2 = requireActivity().findViewById(R.id.imageRemove2)
        imageRemove3 = requireActivity().findViewById(R.id.imageRemove3)
        images.add(addImage1!!)
        images.add(addImage2!!)
        images.add(addImage3!!)
        imageRemoves.add(imageRemove1!!)
        imageRemoves.add(imageRemove2!!)
        imageRemoves.add(imageRemove3!!)

        setStartTime.setOnClickListener {

            val datePickerDialog = DatePickerDialog(requireContext())
            val rightNow = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"))
            val hour = rightNow[Calendar.HOUR_OF_DAY]
            val minute = rightNow[Calendar.MINUTE]
            val timePicker = TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    val h = hourOfDay.toString().padStart(2, '0')
                    val m = minute.toString().padStart(2, '0')
                    startTime += "${h}:${m}"
                    setStartTime.text = startTime
                    if (parseStringTime(startTime)?.time!! <= System.currentTimeMillis()) {
                        Toast.makeText(requireContext(), "开始时间不能早于当前时间", Toast.LENGTH_SHORT).show()
                    }

                },
                hour,
                minute,
                true
            )
            datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
                // 保存日期，然后显示时间选择器
                // 日期格式：2021-01-01 01:01
                val m = month.toString().padStart(2, '0')
                val d = dayOfMonth.toString().padStart(2, '0')
                startTime = "${year}-${m}-${d} "
                timePicker.show()

            }
            datePickerDialog.show()

        }


        setEndTime.setOnClickListener {

            val datePickerDialog = DatePickerDialog(requireContext())
            val rightNow = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"))
            val hour = rightNow[Calendar.HOUR_OF_DAY]
            val minute = rightNow[Calendar.MINUTE]
            val timePicker = TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    val h = hourOfDay.toString().padStart(2, '0')
                    val m = minute.toString().padStart(2, '0')
                    endTime += "${h}:${m}"
                    setEndTime.text = endTime
                },
                hour,
                minute,
                true
            )
            datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
                // 保存日期，然后显示时间选择器
                // 日期格式：2021-01-01 01:01
                val m = month.toString().padStart(2, '0')
                val d = dayOfMonth.toString().padStart(2, '0')
                endTime = "${year}-${m}-${d} "
                timePicker.show()

            }
            datePickerDialog.show()

        }

        addImage1?.setOnClickListener {

            PermissionX.init(activity)
                .permissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .request { allGranted, _, deniedList ->
                    if (allGranted) {
                        selectPics()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "These permissions are denied: $deniedList",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

        }

        setTag.setOnClickListener {
            findNavController()
                .navigate(R.id.action_addFragment_to_setTagFragment)
        }
    }

    private fun parseStringTime(time: String?): Date? {
        val sdf = android.icu.text.SimpleDateFormat("yyyy-MM-dd HH:mm")
        sdf.timeZone = TimeZone.getTimeZone("GMT+8:00")
        var date: Date? = Date()
        try {
            date = sdf.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    private fun format(date: Date?): String? {
        val date1 = Date()
        val sdf1 = SimpleDateFormat("yyyy-MM-dd HH:mm")
        sdf1.timeZone = TimeZone.getTimeZone("GMT+8:00")
        return sdf1.format(date1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == -1) {
            // 保存返回的图片uri
            selected.addAll(Matisse.obtainResult(data))
            // 显示图片
            showPic()
        }
    }

    // 加载图片
    private fun showPic() {
        images.forEach {
            Glide.with(this)
                .clear(it)
        }
        setNullListener()
        selected.forEachIndexed { index, uri ->
            images[index].visibility = View.VISIBLE
            imageRemoves[index].visibility = View.VISIBLE
            Glide.with(this)
                .load(uri)
                .into(images[index])
            if ((index == selected.size - 1) && index != 2) {
                images[index + 1].visibility = View.VISIBLE
                Glide.with(this)
                    .load(R.drawable.ic_baseline_add_24)
                    .into(images[index + 1])
                images[index + 1].setOnClickListener {
                    selectPics()
                }
            }

        }
        setRemovePicListener()
        if (selected.size == 0) {
            addImage1?.visibility = View.VISIBLE
            addImage1?.setOnClickListener { selectPics() }
            Glide.with(this)
                .load(R.drawable.ic_baseline_add_24)
                .into(addImage1!!)
        }
    }

    // 初始化
    private fun setNullListener() {
        images.forEach { it.visibility = View.INVISIBLE }
        imageRemoves.forEach { it.visibility = View.INVISIBLE }
        images.forEach { it.setOnClickListener(null) }
        imageRemoves.forEach { it.setOnClickListener(null) }
    }

    // 选择图片
    private fun selectPics() {
        Matisse.from(this)
            .choose(MimeType.ofAll())
            .countable(true)
            .maxSelectable(3 - selected.size)
            .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
            .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            .thumbnailScale(0.85f)
            .imageEngine(GlideEngine())
            .showPreview(false) // Default is `true`
            .forResult(1)
    }

    // 设置删除图片监听
    private fun setRemovePicListener() {
        imageRemove1?.setOnClickListener {
            selected.removeAt(0)
            showPic()

        }
        imageRemove2?.setOnClickListener {
            selected.removeAt(1)
            showPic()

        }
        imageRemove3?.setOnClickListener {
            selected.removeAt(2)
            showPic()

        }
    }

}