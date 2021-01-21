package com.app.telemed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.app.telemed.databinding.EndOfQuestioningFragmentBinding

class EndOfQuestioningFragment : Fragment() {

    lateinit var binding: EndOfQuestioningFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return EndOfQuestioningFragmentBinding.inflate(inflater).run {
            binding = this
            root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toQuestions.setOnClickListener {
            findNavController().popBackStack(R.id.menu_navigation_xml, true)
            findNavController().navigate(R.id.action_global_scheduleFragment)
        }
    }

}