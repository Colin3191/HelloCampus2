package com.example.hellocampus.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import cn.leancloud.AVUser
import com.example.hellocampus.R
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class LoginFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val buttonGoToRegister = requireActivity().findViewById<Button>(R.id.buttonGoToRegister)
        val buttonLogin = requireActivity().findViewById<Button>(R.id.buttonLogin)
        buttonGoToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        buttonLogin.setOnClickListener {
            val acc =
                requireActivity().findViewById<EditText>(R.id.editTextAccount).text.toString()
                    .trim()
            val pwd =
                requireActivity().findViewById<EditText>(R.id.editTextPassword).text.toString()
                    .trim()
            if (acc != "" && pwd != "") {
                AVUser.logIn(acc, pwd).subscribe(object : Observer<AVUser> {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onNext(t: AVUser) {
//                    登录成功
                        Toast.makeText(activity, "登录成功", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
                    }

                    override fun onError(e: Throwable) {
                        //登录失败
                        Toast.makeText(activity, "登陆失败：$e", Toast.LENGTH_SHORT).show()
                    }

                    override fun onComplete() {}

                })

            } else {
                Toast.makeText(activity, "账号或密码不能为空", Toast.LENGTH_SHORT).show()
            }

        }
    }

}