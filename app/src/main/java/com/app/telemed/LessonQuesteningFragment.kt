package com.app.telemed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class LessonQuesteningFragment : Fragment() {

    companion object {
        fun newInstance() = LessonQuesteningFragment()
    }

    private lateinit var viewModel: LessonQuesteningViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lesson_questening_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LessonQuesteningViewModel::class.java)
        // TODO: Use the ViewModel
    }

}