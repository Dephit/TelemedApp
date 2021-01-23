package com.app.telemed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.telemed.databinding.FragmentLoadingBinding

class LoadingFragment : Fragment() {


    lateinit var binding: FragmentLoadingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return FragmentLoadingBinding.inflate(inflater).run {
            binding = this
            root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.swiperefresh.setColorSchemeColors(getColor(requireContext(),R.color.primary_button_color))
        binding.swiperefresh.isRefreshing = true
    }

}