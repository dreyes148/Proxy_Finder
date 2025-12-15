package com.dreyes148.proxyfinder.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dreyes148.proxyfinder.R
import com.dreyes148.proxyfinder.databinding.ItemProxyBinding
import com.dreyes148.proxyfinder.model.Proxy

/**
 * RecyclerView adapter for displaying proxy list with DiffUtil for efficiency
 */
class ProxyAdapter : ListAdapter<Proxy, ProxyAdapter.ProxyViewHolder>(ProxyDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProxyViewHolder {
        val binding = ItemProxyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProxyViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ProxyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class ProxyViewHolder(private val binding: ItemProxyBinding) : 
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(proxy: Proxy) {
            binding.proxyText.text = proxy.toConnectionString()
            binding.countryText.text = proxy.country
            binding.protocolText.text = proxy.protocol
            binding.anonymityText.text = proxy.anonymity
            
            binding.btnCopy.setOnClickListener {
                copyToClipboard(binding.root.context, proxy.toConnectionString())
            }
        }
        
        private fun copyToClipboard(context: Context, text: String) {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Proxy", text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, context.getString(R.string.proxy_copied), Toast.LENGTH_SHORT).show()
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
            return oldItem == newItem
        }
    }
}
