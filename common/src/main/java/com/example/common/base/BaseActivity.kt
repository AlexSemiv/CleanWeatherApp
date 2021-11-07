package com.example.common.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Base class for all activity
 * (in our case single activity)
 */
abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity() {

    abstract fun bindLayout(layoutInflater: LayoutInflater): VB

    private var _binding: VB? = null
    val binding: VB
        get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = bindLayout(layoutInflater)
        setContentView(requireNotNull(_binding).root)
    }
}