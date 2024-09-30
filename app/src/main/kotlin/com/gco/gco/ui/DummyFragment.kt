package com.gco.gco.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.gco.gco.databinding.FragmentActivitiesBinding
import com.gco.gco.databinding.FragmentDummyBinding

class DummyFragment : com.gco.gco.utils.BaseFragment() {
    private lateinit var binding: FragmentDummyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDummyBinding.inflate(inflater, container, false)
        return binding.root
    }
    //kk
}