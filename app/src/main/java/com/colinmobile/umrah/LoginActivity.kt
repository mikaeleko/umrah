package com.colinmobile.umrah

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.constraintlayout.widget.ConstraintLayout

class LoginActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername = findViewById(R.id.et_username)as AppCompatEditText
        val etPassword = findViewById(R.id.et_password)as AppCompatEditText

        val btnLogin = findViewById(R.id.btn_login)as AppCompatButton
        btnLogin.setOnClickListener(View.OnClickListener {
            if(etUsername.equals("10001")&&etPassword.equals("UmHajj"+etUsername.text)){
                val i = Intent(this@LoginActivity, GuideActivity::class.java)
                startActivity(i)
            }else{
                Toast.makeText(
                    this@LoginActivity,
                    "username dan password tidak sesuai",
                    Toast.LENGTH_SHORT)
            }

        })
    }
}