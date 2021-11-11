package com.example.cleanweatherapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.cleanweatherapp.databinding.MainForecastItemBinding
import com.example.common.base.BaseRecyclerAdapter
import com.example.domain.qualifiers.ActivityScope
import com.example.presentation.models.current.Daily
import javax.inject.Inject

@ActivityScope
class DailyAdapter @Inject constructor(
    callback: DiffUtil.ItemCallback<Daily>
): BaseRecyclerAdapter<Daily, MainForecastItemBinding, DailyViewHolder> (callback = callback) {

    private var onItemClickListener: ((Daily) -> Unit)? = null
    fun setOnItemClickListener(listener: (Daily) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        return DailyViewHolder(
            binding = MainForecastItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.btnItemShowMore.setOnClickListener {
            onItemClickListener?.invoke(getItem(position))
        }
    }
}