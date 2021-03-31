package com.iprokopyuk.worldnews.views.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.iprokopyuk.worldnews.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.content_main.*

abstract class BaseActivity<T : ViewDataBinding> : DaggerAppCompatActivity() {
    @LayoutRes
    abstract fun getLayoutResId(): Int

    abstract fun getLiveDataInternetConnection(): LiveData<Boolean?>

    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayoutResId())
    }

    fun showSnackbar(text: String, textAction: String, action: () -> Unit) {

        val snackbar = Snackbar.make(
            linearLayout,
            text,
            Snackbar.LENGTH_LONG
        )
            .setAction(textAction, View.OnClickListener { action() })

        snackbar.show()
    }

    fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    fun onObserveTointernetConnection(action: () -> Unit) {

        getLiveDataInternetConnection().observe(this, Observer { internetConnection ->
            internetConnection.let {
                when (it) {
                    true -> {
                        showSnackbar(
                            resources.getString(R.string.snackbar_text),
                            resources.getString(R.string.snackbar_action_text),
                            { action() }
                        )
                    }
                    false -> {
                        showToast(resources.getString(R.string.info_no_connection))
                    }
                }
            }
        })
    }
}