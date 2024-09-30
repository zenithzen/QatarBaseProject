package com.gco.gco.ui.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.gco.gco.MainActivity
import com.gco.gco.R
import com.gco.gco.TypefaceLoader.setInterBold
import com.gco.gco.TypefaceLoader.setInterRegular
import com.gco.gco.TypefaceLoader.setSemiBold
import com.gco.gco.databinding.FragmentActivitiesBinding
import com.gco.gco.ui.activities.adapter.ActivityListAdapter
import com.gco.gco.ui.activities.bottomsheet.ActivityFilterBottomsheet
import com.gco.gco.ui.activities.bottomsheet.SortByBottomSheet
import com.gco.gco.ui.main.MainFragment
import com.gco.gco.ui.main.MainFragmentDirections
import com.gco.gco.utils.dp
import com.gco.gco.utils.setCornerRadius
import com.gco.gco.utils.setOnSafeClickListener
import com.gco.gco.utils.viewVisibility
import com.mbt.localization.LocalizedApp

class ActivitiesFragment : com.gco.gco.utils.BaseFragment() {
    private lateinit var binding: FragmentActivitiesBinding
    private val activityViewModel: ActivityViewModel by viewModels()
    private val activityAdapter = ActivityListAdapter()
    private val localizationDelegate = LocalizationDelegate()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActivitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LocalizedApp.localeLiveData.observe(viewLifecycleOwner, localizationDelegate)
        setUpViews()
        setUpWindowInsects()
        activityViewModel.liveData.observe(viewLifecycleOwner) { listItem ->
            activityAdapter.apply {
                binding.apply {
                    rvActivityList.viewVisibility(!listItem.isNullOrEmpty())
                    clNoData.viewVisibility(listItem.isNullOrEmpty())
                }
                activityList = listItem
                notifyDataSetChanged()
            }


        }
        activityViewModel.setUpData()
    }

    private fun setUpWindowInsects() {
        (requireActivity() as MainActivity).windowInsetsLiveData.observe(viewLifecycleOwner) { insets ->
            val rect =
                insets.getInsets(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars() or WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            binding.apply {
                safeTopGuideLine.setGuidelineBegin(rect.top)
                // safeBottomGuideLine.setGuidelineEnd(rect.bottom)
            }


        }
    }

    private fun setUpViews() {
        binding.apply {
            clSearchLayout.apply {
                setCornerRadius(dp(8))
            }
            ivBackButton.setOnClickListener {
                MainFragment.shared?.get()?.menuSelected=true
                MainFragment.shared?.get()?.setStartDestination(R.id.dashboardFragment)
            }
            tvResultMatchingCategoryValue.setCornerRadius(dp(30))
            tvViewLatestEvents.setCornerRadius(dp(6))
            rvActivityList.adapter = activityAdapter.apply {
                onLoadMoreClick = {
                    activityViewModel.setUpData()

                }
            }
            ivFilterWithArrow.setOnSafeClickListener {
                SortByBottomSheet().show(childFragmentManager, "SortByBottomSheet")

            }
            ivFilter.setOnSafeClickListener {
                ActivityFilterBottomsheet().show(
                    childFragmentManager,
                    "ActivityFilterBottomsheet"
                )
            }

        }
    }

    private inner class LocalizationDelegate() : Observer<String> {
        override fun onChanged(locale: String) {
            binding.apply {
                root.layoutDirection = LocalizedApp.getLayoutDirection(locale)
                tvTitle.apply {
                    setInterBold(locale)
                    text = LocalizedApp.getString(R.string.txt_activities)
                }
                etSearch.apply {
                    setInterRegular(locale)
                    hint = LocalizedApp.getString(R.string.txt_search_activities)
                }
                tvResultMatchingText.apply {
                    text = LocalizedApp.getString(R.string.txt_result_matching)
                    setInterRegular(locale)
                }
                tvResultMatchingCategory.apply {
                    setInterBold(locale)
                }
                tvResultMatchingCategoryValue.apply {
                    setSemiBold(locale)
                }
            }
        }

    }
}