package com.gco.gco.ui.activities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gco.gco.TypefaceLoader.setSemiBold
import com.gco.gco.databinding.CellChipBinding
import com.gco.gco.utils.dp
import com.gco.gco.utils.setCornerRadius
import com.mbt.localization.LocalizedApp
import com.mbt.localization.LocalizedApp.Companion.locale

class CommonChipAdapter : RecyclerView.Adapter<CommonChipAdapter.ChipViewHolder>() {
    var itemCounts :Int= 0

    inner class ChipViewHolder(val binding: CellChipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                root.layoutDirection = LocalizedApp.getLayoutDirection(locale)
                tvItem.apply {
                    setSemiBold(locale)
                    setCornerRadius(dp(30))
                    if (itemCounts > 3 && bindingAdapterPosition==2) {
                        text = "3+"
                    } else {
                        text = "MOFA"
                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipViewHolder =
        ChipViewHolder(
            CellChipBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = if (itemCounts > 3) 3 else itemCounts

    override fun onBindViewHolder(holder: ChipViewHolder, position: Int) {
        holder.bind()
    }
}