package com.gco.gco.ui.activities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gco.gco.R
import com.gco.gco.TypefaceLoader.setInterMedium
import com.gco.gco.TypefaceLoader.setSemiBold
import com.gco.gco.databinding.CellActivitiesBinding
import com.gco.gco.databinding.CellLoadMoreBinding
import com.gco.gco.ui.activities.ActivityModel
import com.gco.gco.utils.dp
import com.gco.gco.utils.setCornerRadius
import com.gco.gco.utils.setOnSafeClickListener
import com.mbt.localization.LocalizedApp
import com.mbt.localization.LocalizedApp.Companion.locale

class ActivityListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var activityList: ArrayList<ActivityModel> = arrayListOf()
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

    inner class ActivityViewHolder(val binding: CellActivitiesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ActivityModel) {
            binding.apply {
                root.layoutDirection = LocalizedApp.getLayoutDirection(locale)
                tvTitle.apply {
                    setSemiBold(locale)
                    text = item.title
                }
                clEventType.apply {
                    setCornerRadius(dp(6))
                }
                tvEventType.apply {
                    setInterMedium(locale)
                    text = item.category
                }
                tvEventStatus.apply {
                    setInterMedium(locale)
                    text = item.status
                }
                dotView.setCornerRadius()
                clAttachment.apply {
                    setCornerRadius(dp(6))
                }
                tvAttachmentCount.apply {
                    setInterMedium(locale)
                    text = item.attachment.toString()
                }
                tvLocation.apply {
                    setInterMedium(locale)
                    text = LocalizedApp.getText(R.string.txt_location)
                }
                tvLocationValue.apply {
                    setInterMedium(locale)
                    text = item.locationData
                }
                tvDate.apply {
                    setInterMedium(locale)
                    text = LocalizedApp.getText(R.string.txt_date_time)
                }
                tvDateValue.apply {
                    setInterMedium(locale)
                    text = item.dateData
                }
                tvMinistryName.apply {
                    setInterMedium(locale)
                    text = LocalizedApp.getText(R.string.txt_ministry_of_interiors)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val binding =
                CellActivitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ActivityViewHolder(binding)
        } else {
            val binding =
                CellLoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LoadMoreViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int = if (activityList[position].pageMoreToLoad) {
        2
    } else {
        1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ActivityViewHolder) {
            holder.bind(activityList[position])
        } else if (holder is LoadMoreViewHolder) {
            holder.bind()
        }
    }

    override fun getItemCount() = activityList.size


}