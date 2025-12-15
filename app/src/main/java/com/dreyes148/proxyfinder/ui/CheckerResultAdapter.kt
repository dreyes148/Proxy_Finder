package com.dreyes148.proxyfinder.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dreyes148.proxyfinder.R
import com.dreyes148.proxyfinder.databinding.ItemCheckerResultBinding
import com.dreyes148.proxyfinder.model.Proxy

/**
 * RecyclerView adapter for displaying proxy check results
 */
class CheckerResultAdapter : ListAdapter<Proxy, CheckerResultAdapter.ResultViewHolder>(ProxyDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = ItemCheckerResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class ResultViewHolder(private val binding: ItemCheckerResultBinding) : 
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(proxy: Proxy) {
            binding.proxyText.text = proxy.toConnectionString()
            
            when {
                proxy.isValid == null -> {
                    binding.statusText.text = binding.root.context.getString(R.string.status_checking)
                    binding.statusText.setTextColor(Color.GRAY)
                }
                proxy.isValid == true -> {
                    val statusText = binding.root.context.getString(R.string.status_valid, proxy.responseTime ?: 0)
                    binding.statusText.text = statusText
                    binding.statusText.setTextColor(Color.parseColor("#4CAF50")) // Green
                }
                else -> {
                    binding.statusText.text = binding.root.context.getString(R.string.status_invalid)
                    binding.statusText.setTextColor(Color.parseColor("#F44336")) // Red
                }
            }
        }
    }
    
    /**
     * DiffUtil callback for efficient list updates
     */
    class ProxyDiffCallback : DiffUtil.ItemCallback<Proxy>() {
        override fun areItemsTheSame(oldItem: Proxy, newItem: Proxy): Boolean {
            return oldItem.toConnectionString() == newItem.toConnectionString()
        }
        
        override fun areContentsTheSame(oldItem: Proxy, newItem: Proxy): Boolean {
            return oldItem == newItem && oldItem.isValid == newItem.isValid && 
                   oldItem.responseTime == newItem.responseTime
        }
    }
}
