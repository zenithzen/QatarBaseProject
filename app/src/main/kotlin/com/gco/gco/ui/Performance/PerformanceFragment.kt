package com.gco.gco.ui.Performance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gco.gco.databinding.FragmentPerformanceBinding
import com.gco.gco.ui.main.MainFragment
import com.gco.gco.ui.main.MainFragmentDirections
import com.mbt.utils.BaseFragment

class PerformanceFragment : com.gco.gco.utils.BaseFragment() {
    private lateinit var binding: FragmentPerformanceBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentPerformanceBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

        }

    }

}