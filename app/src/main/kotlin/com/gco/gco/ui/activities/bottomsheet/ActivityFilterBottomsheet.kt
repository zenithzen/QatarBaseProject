package com.gco.gco.ui.activities.bottomsheet

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.gco.gco.MainActivity
import com.gco.gco.R
import com.gco.gco.TypefaceLoader.interSemiBold
import com.gco.gco.TypefaceLoader.setInterBold
import com.gco.gco.TypefaceLoader.setInterMedium
import com.gco.gco.TypefaceLoader.setInterRegular
import com.gco.gco.TypefaceLoader.setSemiBold
import com.gco.gco.databinding.ActivityFilterBottomsheetBinding
import com.gco.gco.ui.activities.adapter.CommonChipAdapter
import com.gco.gco.utils.dp
import com.gco.gco.utils.setCornerRadius
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mbt.localization.LocalizedApp
import java.util.Calendar

class ActivityFilterBottomsheet : BottomSheetDialogFragment() {
    private lateinit var binding: ActivityFilterBottomsheetBinding
    private var selectedRadio = 0
    private val localizationDelegate = LocalizationDelegate()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = ActivityFilterBottomsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LocalizedApp.localeLiveData.observe(viewLifecycleOwner, localizationDelegate)
        binding.apply {
            clAccepted.setOnClickListener {
                selectedRadio = 1
                setUpRadio()
            }
            clDeclined.setOnClickListener {
                selectedRadio = 2
                setUpRadio()
            }
            clPendingReview.setOnClickListener {
                selectedRadio = 3
                setUpRadio()
            }
            clPendingRevision.setOnClickListener {
                selectedRadio = 4
                setUpRadio()
            }
            rvEntity.apply {

                adapter = CommonChipAdapter().apply {
                    itemCounts = 3
                    notifyDataSetChanged()
                }

            }
            rvActivityType.apply {

                adapter = CommonChipAdapter().apply {
                    itemCounts = 4
                    notifyDataSetChanged()
                }

            }
            clStartDate.setOnClickListener {
                Calendar.getInstance().let { cal ->
                    DatePickerDialog(
                        requireContext(), { _, y, m, d ->
                            tvStartDate.text =
                                String.format("%02d/%02d/%04d", d, m + 1, y)
                        }, cal[Calendar.YEAR], cal[Calendar.MONTH], cal[Calendar.DAY_OF_MONTH]
                    ).apply {/*datePicker.minDate = Date().time*/ }.show()
                }
            }
            clEndDate.setOnClickListener {
                Calendar.getInstance().let { cal ->
                    DatePickerDialog(
                        requireContext(), { _, y, m, d ->
                            tvEndDate.text =
                                String.format("%02d/%02d/%04d", d, m + 1, y)
                        }, cal[Calendar.YEAR], cal[Calendar.MONTH], cal[Calendar.DAY_OF_MONTH]
                    ).apply {/*datePicker.minDate = Date().time*/ }.show()
                }
            }
        }
        setupWindowInsets()
    }

    private fun setupWindowInsets() {
        (requireActivity() as MainActivity).windowInsetsLiveData.observe(viewLifecycleOwner) { insets ->
            val rect =
                insets.getInsets(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars() or WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            binding.safeBottomGuideline.setGuidelineEnd(rect.bottom)
        }
    }

    private fun setUpRadio() {
        binding.apply {
            when (selectedRadio) {
                1 -> {
                    ivAccepted.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_radio_btn_selected))
                    tvAccepted.setTextColor(LocalizedApp.getColor(R.color.colorPrimary))
                    ivDeclined.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_radiobtn))
                    tvDeclined.setTextColor(LocalizedApp.getColor(R.color.light_text_color))
                    ivPendingReview.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_radiobtn))
                    tvPendingReview.setTextColor(LocalizedApp.getColor(R.color.light_text_color))
                    ivPendingRevision.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_radiobtn))
                    tvPendingRevision.setTextColor(LocalizedApp.getColor(R.color.light_text_color))

                }

                2 -> {
                    ivAccepted.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_radiobtn))
                    tvAccepted.setTextColor(LocalizedApp.getColor(R.color.light_text_color))
                    ivDeclined.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_radio_btn_selected))
                    tvDeclined.setTextColor(LocalizedApp.getColor(R.color.colorPrimary))
                    ivPendingReview.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_radiobtn))
                    tvPendingReview.setTextColor(LocalizedApp.getColor(R.color.light_text_color))
                    ivPendingRevision.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_radiobtn))
                    tvPendingRevision.setTextColor(LocalizedApp.getColor(R.color.light_text_color))

                }

                3 -> {
                    ivAccepted.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_radiobtn))
                    tvAccepted.setTextColor(LocalizedApp.getColor(R.color.light_text_color))
                    ivDeclined.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_radiobtn))
                    tvDeclined.setTextColor(LocalizedApp.getColor(R.color.light_text_color))
                    ivPendingReview.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_radio_btn_selected))
                    tvPendingReview.setTextColor(LocalizedApp.getColor(R.color.colorPrimary))
                    ivPendingRevision.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_radiobtn))
                    tvPendingRevision.setTextColor(LocalizedApp.getColor(R.color.light_text_color))

                }

                4 -> {
                    ivAccepted.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_radiobtn))
                    tvAccepted.setTextColor(LocalizedApp.getColor(R.color.light_text_color))
                    ivDeclined.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_radiobtn))
                    tvDeclined.setTextColor(LocalizedApp.getColor(R.color.light_text_color))
                    ivPendingReview.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_radiobtn))
                    tvPendingReview.setTextColor(LocalizedApp.getColor(R.color.light_text_color))
                    ivPendingRevision.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_radio_btn_selected))
                    tvPendingRevision.setTextColor(LocalizedApp.getColor(R.color.colorPrimary))

                }
            }
        }

    }

    override fun getTheme() = R.style.BottomSheetDialog
    inner class LocalizationDelegate() : Observer<String> {
        override fun onChanged(locale: String) {
            binding.apply {
                tvTitle.apply {
                    setInterBold(locale)
                    text = LocalizedApp.getString(R.string.txt_filters)
                }
                tvClearAll.apply {
                    setSemiBold(locale)
                    text = LocalizedApp.getString(R.string.txt_clear_all)
                }
                tvEntityTitle.apply {
                    setSemiBold(locale)
                    text = LocalizedApp.getString(R.string.txt_entity)
                }
                tvActivityTitle.apply {
                    setSemiBold(locale)
                    text = LocalizedApp.getString(R.string.txt_activity_type)
                }
                tvStatus.apply {
                    setSemiBold(locale)
                    text = LocalizedApp.getString(R.string.txt_status)
                }
                tvAccepted.apply {
                    setInterMedium(locale)
                    text = LocalizedApp.getString(R.string.txt_accepted)
                }
                tvDeclined.apply {
                    setInterMedium(locale)
                    text = LocalizedApp.getString(R.string.txt_declined)
                }
                tvPendingReview.apply {
                    setInterMedium(locale)
                    text = LocalizedApp.getString(R.string.txt_pending_review)
                }
                tvPendingRevision.apply {
                    setInterMedium(locale)
                    text = LocalizedApp.getString(R.string.txt_pending_revision)
                }
                tvDateRange.apply {
                    setInterBold(locale)
                    text = LocalizedApp.getString(R.string.txt_date_range)
                }
                tvStartDate.apply {
                    setInterRegular(locale)
                    text = LocalizedApp.getString(R.string.txt_start_date)
                }
                tvEndDate.apply {
                    setInterRegular(locale)
                    text = LocalizedApp.getString(R.string.txt_end_date)
                }
                btnSearch.apply {
                    setInterMedium(locale)
                    text = LocalizedApp.getString(R.string.txt_search)
                    setCornerRadius(dp(6))
                }
            }
        }

    }
}