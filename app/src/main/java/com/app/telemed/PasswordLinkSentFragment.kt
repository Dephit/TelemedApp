package com.app.telemed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.app.telemed.databinding.FragmentPasswordLinkSentBinding

class PasswordLinkSentFragment : Fragment() {

    lateinit var binding: FragmentPasswordLinkSentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentPasswordLinkSentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.okButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}