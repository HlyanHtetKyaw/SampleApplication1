package com.example.sampleapplication1.base

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class ViewBindingActivity<T : ViewBinding> : AppCompatActivity() {

    private lateinit var _binding: T

    // This property is only valid after onCreate
    val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        onCreateWindowTransition(window)
        super.onCreate(savedInstanceState)
        _binding = onCreateViewBinding()
        setContentView(_binding.root)
    }

    /**
     * @deprecated don't inflate layoutResID. Use binding property instead.
     */
    @Deprecated(
        "don't inflate layoutResID. Use binding property instead.",
        level = DeprecationLevel.ERROR
    )
    final override fun setContentView(layoutResID: Int) {
    }

    protected open fun onCreateWindowTransition(window: Window) {}

    protected abstract fun onCreateViewBinding(): T

}