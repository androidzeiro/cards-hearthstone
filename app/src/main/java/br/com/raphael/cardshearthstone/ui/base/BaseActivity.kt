package br.com.raphael.cardshearthstone.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

open class BaseActivity<T: ViewBinding>(
    private val inflateMethod: (LayoutInflater) -> T
) : AppCompatActivity() {

    private var _binding: T? = null
    val binding: T get() = _binding!!

    open fun T.initialize(savedInstanceState: Bundle?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = inflateMethod.invoke(layoutInflater).apply {
            setContentView(root)
        }
        binding.initialize(savedInstanceState)
    }
}