package com.gco.gco.ui.announcements.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gco.gco.R
import com.gco.gco.TypefaceLoader.setInterMedium
import com.gco.gco.TypefaceLoader.setSemiBold
import com.gco.gco.databinding.CellAnnouncementsBinding
import com.gco.gco.databinding.CellLoadMoreBinding
import com.gco.gco.ui.announcements.AnnouncementModel
import com.gco.gco.utils.dp
import com.gco.gco.utils.setCornerRadius
import com.gco.gco.utils.setOnSafeClickListener
import com.mbt.localization.LocalizedApp
import com.mbt.localization.LocalizedApp.Companion.locale

class AnnouncementListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var announcementList: ArrayList<AnnouncementModel> = arrayListOf()
    var onLoadMoreClick:(()->Unit)?=null

    inner class LoadMoreViewHolder(val binding: CellLoadMoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                root.apply {
                    layoutDirection = LocalizedApp.getLayoutDirection(locale)
                    setOnSafeClickListener {
                        onLoadMoreClick?.invoke()
                    }
                }
                tvLoadMore.apply {
                    setInterMedium(locale)
                    text = LocalizedApp.getString(R.string.txt_load_more)
                    setCornerRadius(dp(6))


                }
            }
        }
    }

    inner class AnnouncementViewHolder(val binding: CellAnnouncementsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AnnouncementModel) {
            binding.apply {
                root.layoutDirection = LocalizedApp.getLayoutDirection(locale)
                tvTitle.apply {
                    setSemiBold(locale)
                    text = item.title
                }
                tvDate.apply {
                    setInterMedium(locale)
                    text = item.dateData
                }


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val binding =
                CellAnnouncementsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return AnnouncementViewHolder(binding)
        } else {
            val binding =
                CellLoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LoadMoreViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int = if (announcementList[position].pageMoreToLoad) {
        2
    } else {
        1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AnnouncementViewHolder) {
            holder.bind(announcementList[position])
        } else if (holder is LoadMoreViewHolder) {
            holder.bind()
        }
    }

    override fun getItemCount() = announcementList.size


}