package com.example.hellocampus.home

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hellocampus.R
import com.permissionx.guolindev.PermissionX


class AddFragment : Fragment() {
    var startTime: String? = null
    var endTime: String? = null

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
        val addImage1 = requireActivity().findViewById<ImageView>(R.id.image1)
        val addImage2 = requireActivity().findViewById<ImageView>(R.id.image2)
        val addImage3 = requireActivity().findViewById<ImageView>(R.id.image3)


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
                    Log.d("startTime", "onActivityCreated: $startTime")
                },
                hour,
                minute,
                true
            )
            datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
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
                    Log.d("endTime", "onActivityCreated: $endTime")
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

        addImage1.setOnClickListener {

            PermissionX.init(activity)
                .permissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .request { allGranted, _, deniedList ->
                    if (allGranted) {
                        Toast.makeText(
                            requireContext(),
                            "All permissions are granted",
                            Toast.LENGTH_LONG
                        ).show()
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

}