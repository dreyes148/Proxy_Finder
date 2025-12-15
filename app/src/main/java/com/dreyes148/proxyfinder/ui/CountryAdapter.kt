package com.dreyes148.proxyfinder.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dreyes148.proxyfinder.databinding.ItemCountryBinding
import com.dreyes148.proxyfinder.model.Country

/**
 * RecyclerView adapter for country selection with checkboxes
 */
class CountryAdapter(
    private val onCountryClick: (Country) -> Unit
) : ListAdapter<Country, CountryAdapter.CountryViewHolder>(CountryDiffCallback()) {
    
    private val selectedCountries = mutableSetOf<Country>()
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = getItem(position)
        holder.bind(country, selectedCountries.contains(country))
    }
    
    inner class CountryViewHolder(private val binding: ItemCountryBinding) : 
        RecyclerView.ViewHolder(binding.root) {
        
        init {
            binding.root.setOnClickListener {
                val country = getItem(bindingAdapterPosition)
                toggleCountry(country)
                onCountryClick(country)
            }
        }
        
        fun bind(country: Country, isSelected: Boolean) {
            binding.countryText.text = country.getDisplayName()
            binding.checkbox.isChecked = isSelected
        }
    }
    
    /**
     * Toggle country selection
     */
    private fun toggleCountry(country: Country) {
        if (selectedCountries.contains(country)) {
            selectedCountries.remove(country)
        } else {
            selectedCountries.add(country)
        }
        notifyItemChanged(currentList.indexOf(country))
    }
    
    /**
     * Set selected countries
     */
    fun setSelectedCountries(countries: Set<Country>) {
        selectedCountries.clear()
        selectedCountries.addAll(countries)
        notifyDataSetChanged()
    }
    
    /**
     * Get currently selected countries
     */
    fun getSelectedCountries(): Set<Country> = selectedCountries.toSet()
    
    /**
     * Clear all selections
     */
    fun clearSelections() {
        selectedCountries.clear()
        notifyDataSetChanged()
    }
    
    class CountryDiffCallback : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.code == newItem.code
        }
        
        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }
    }
}
