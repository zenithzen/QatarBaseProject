package com.gco.gco.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gco.gco.databinding.FragmentDashboardBinding
import com.gco.gco.ui.main.MainFragment
import com.gco.gco.ui.main.MainFragmentDirections

class DashboardFragment : com.gco.gco.utils.BaseFragment() {
    private lateinit var binding: FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            title.setOnClickListener {
                MainFragment.shared?.get()?.findNavController()
                    ?.navigate(MainFragmentDirections.actionMainFragmentToDummyFragment())
            }
        }

    }

}