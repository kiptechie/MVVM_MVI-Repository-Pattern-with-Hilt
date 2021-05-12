package com.poetcodes.mvvmhilt.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.poetcodes.mvvmhilt.R
import com.poetcodes.mvvmhilt.models.Blog
import com.poetcodes.mvvmhilt.utils.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG: String? = MainActivity::class.simpleName
    private var textView: TextView? = null
    private var progressBar: ProgressBar? = null
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.text)
        progressBar = findViewById(R.id.progress_circular)
        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogEvents)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, { dataState ->
            when (dataState) {
                is DataState.Success<List<Blog>> -> {
                    displayProgressBar(false)
                    appendBlogTitles(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun appendBlogTitles(blogs: List<Blog>) {
        val stringBuilder = StringBuilder()
        for (blog in blogs) {
            stringBuilder.append(blog.title + "\n")
        }
        textView?.text = stringBuilder.toString()
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progressBar?.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayError(message: String?) {
        if (message != null) {
            textView?.text = message
        } else {
            textView?.text = getString(R.string.unknown_error)
        }
    }
}