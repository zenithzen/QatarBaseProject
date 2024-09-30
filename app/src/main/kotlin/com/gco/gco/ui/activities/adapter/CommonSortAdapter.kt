package com.gco.gco.ui.activities.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gco.gco.R
import com.gco.gco.TypefaceLoader.setInterMedium
import com.gco.gco.TypefaceLoader.setSemiBold
import com.gco.gco.databinding.CellChipBinding
import com.gco.gco.databinding.CellSortByItemBinding
import com.gco.gco.utils.dp
import com.gco.gco.utils.setCornerRadius
import com.mbt.localization.LocalizedApp
import com.mbt.localization.LocalizedApp.Companion.locale

class CommonSortAdapter : RecyclerView.Adapter<CommonSortAdapter.ChipViewHolder>() {
    var itemCounts: Int = 20

    inner class ChipViewHolder(val binding: CellSortByItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                root.layoutDirection = LocalizedApp.getLayoutDirection(locale)
                tvTitle.apply {
                    setInterMedium(locale)
                    text = "Oldest to Newest"
                }
                if (bindingAdapterPosition % 2 != 0) {
                    root.setBackgroundColor(LocalizedApp.getColor(R.color.light_text_color_8))
                    ivTick.imageTintList =
                        ColorStateList.valueOf(LocalizedApp.getColor(R.color.borderGreen))
                } else {
                    root.setBackgroundColor(LocalizedApp.getColor(R.color.white))
                    ivTick.imageTintList =
                        ColorStateList.valueOf(LocalizedApp.getColor(R.color.borderColor))
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipViewHolder =
        ChipViewHolder(
            CellSortByItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = itemCounts

    override fun onBindViewHolder(holder: ChipViewHolder, position: Int) {
        holder.bind()
    }
}