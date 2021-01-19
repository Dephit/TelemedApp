package com.app.telemed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class LessonInProgressFragment : Fragment() {

    companion object {
        fun newInstance() = LessonInProgressFragment()
    }

    private lateinit var viewModel: LessonInProgressViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.lesson_in_progress_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LessonInProgressViewModel::class.java)
        // TODO: Use the ViewModel
    }

}