package com.gco.gco.ui.activities.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.gco.gco.MainActivity
import com.gco.gco.R
import com.gco.gco.TypefaceLoader.setInterBold
import com.gco.gco.databinding.ActivityFilterBottomsheetBinding
import com.gco.gco.databinding.FragmentSortByBottomsheetBinding
import com.gco.gco.ui.activities.adapter.CommonSortAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mbt.localization.LocalizedApp

class SortByBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSortByBottomsheetBinding
    private val localizationDelegate = LocalizationDelegate()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSortByBottomsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupWindowInsets() {
        (requireActivity() as MainActivity).windowInsetsLiveData.observe(viewLifecycleOwner) { insets ->
            val rect =
                insets.getInsets(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars() or WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            binding.safeBottomGuideline.setGuidelineEnd(rect.bottom)
            binding.rvSortList.setPadding(0, 0, 0, rect.bottom + 130)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWindowInsets()
        setUpViews()
    }

    private fun setUpViews() {
        binding.apply {
            LocalizedApp.localeLiveData.observe(viewLifecycleOwner, localizationDelegate)
            rvSortList.apply {
                adapter = CommonSortAdapter().apply {
                    notifyDataSetChanged()
                }
            }
        }
    }

    private inner class LocalizationDelegate() : Observer<String> {
        override fun onChanged(locale: String) {
            binding.apply {
                root.layoutDirection = LocalizedApp.getLayoutDirection(locale)
                tvTitle.apply {
                    setInterBold(locale)
                    text = LocalizedApp.getString(R.string.txt_sort_by)
                }
                rvSortList.apply {
                    adapter?.notifyDataSetChanged()
                }
            }
        }

    }
}