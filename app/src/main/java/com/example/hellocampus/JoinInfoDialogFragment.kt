package com.example.hellocampus

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class JoinInfoDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val view = LayoutInflater.from(context).inflate(R.layout.join_info,null,false)
            val builder = AlertDialog.Builder(it).setView(view)
            builder.setMessage("填写报名信息")
                .setPositiveButton("确定"
                ) { dialog, id ->
                    // 确定
                }
                .setNegativeButton("取消"
                ) { dialog, id ->
                    // 取消
                  val name =  view.findViewById<EditText>(R.id.editTextName).text
                    Log.d("qqq", "onCreateDialog: $name")
                }
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")


    }
}