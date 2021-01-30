package com.example.hellocampus.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cn.leancloud.AVUser
import com.example.hellocampus.R
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val buttonGoToLogin = requireActivity().findViewById<Button>(R.id.buttonGoToLogin)
        val buttonRegister = requireActivity().findViewById<Button>(R.id.buttonRegister)
        val editTextAcc =
            requireActivity().findViewById<EditText>(R.id.editTextRegisterAccount)
        val editTextPaw =
            requireActivity().findViewById<EditText>(R.id.editTextRegisterPassword)
        val editTextRePaw =
            requireActivity().findViewById<EditText>(R.id.editTextRegisterRePassword)

//设置监听
        buttonGoToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        buttonRegister.setOnClickListener {
            val acc = editTextAcc.text.toString().trim()
            val pwd = editTextPaw.text.toString().trim()
            if (validate(editTextAcc, editTextPaw, editTextRePaw)) register(acc, pwd)
        }
    }

    //注册
    private fun register(acc: String, pwd: String) {
        val user = AVUser()
        user.username = acc
        user.password = pwd
        user.signUpInBackground().subscribe(object : Observer<AVUser> {
            override fun onSubscribe(disposable: Disposable) {}
            override fun onNext(user: AVUser) {
                // 注册成功
                Toast.makeText(activity, "注册成功", Toast.LENGTH_SHORT).show()
//注册成功后登录
                AVUser.logIn(acc, pwd).subscribe(object : Observer<AVUser?> {
                    override fun onSubscribe(disposable: Disposable) {}
                    override fun onNext(user: AVUser) {
                        //跳转到"我的"页面
                        findNavController().navigate(R.id.action_registerFragment_to_profileFragment)
                    }

                    override fun onError(throwable: Throwable) {
                        // 登录失败
                        Toast.makeText(activity, "$throwable", Toast.LENGTH_SHORT).show()
                    }

                    override fun onComplete() {}
                })

            }

            override fun onError(throwable: Throwable) {
                // 注册失败
                Toast.makeText(activity, "注册失败:${throwable}", Toast.LENGTH_SHORT).show()
            }

            override fun onComplete() {}
        })

    }

    private fun validate(acc: EditText, pwd: EditText, rePwd: EditText): Boolean {
        val account = acc.text.toString().trim()
        val password = pwd.text.toString().trim()
        val rePassword = rePwd.text.toString().trim()

        if (account == "") {
            acc.error = "账号不能为空"
            return false
        }
        if (password == "") {
            pwd.error = "密码不能为空"
            return false
        }
        if (password != rePassword) {
            rePwd.error = "两次密码不一致"
            return false
        }
        return true
    }

}