package com.example.sampleapplication1.activities

import android.content.Intent
import android.os.Bundle
import com.example.sampleapplication1.base.ViewBindingActivity
import com.example.sampleapplication1.databinding.ActivityLoginBinding

class LoginActivity : ViewBindingActivity<ActivityLoginBinding>() {

    override fun onCreateViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.cvCreateAccount.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}