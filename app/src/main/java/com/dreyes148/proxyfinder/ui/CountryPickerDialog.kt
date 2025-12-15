package com.dreyes148.proxyfinder.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import com.dreyes148.proxyfinder.databinding.DialogCountryPickerBinding
import com.dreyes148.proxyfinder.model.Country
import com.dreyes148.proxyfinder.model.CountryList

/**
 * Custom dialog for selecting one or multiple countries with search functionality
 */
class CountryPickerDialog(
    context: Context,
    private val selectedCountries: Set<Country>,
    private val onCountriesSelected: (Set<Country>) -> Unit
) : Dialog(context) {
    
    private lateinit var binding: DialogCountryPickerBinding
    private lateinit var adapter: CountryAdapter
    private var allCountries = CountryList.ALL_COUNTRIES
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        
        binding = DialogCountryPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupRecyclerView()
        setupSearch()
        setupButtons()
        
        // Set dialog size
        window?.setLayout(
            (context.resources.displayMetrics.widthPixels * 0.9).toInt(),
            (context.resources.displayMetrics.heightPixels * 0.8).toInt()
        )
    }
    
    private fun setupRecyclerView() {
        adapter = CountryAdapter { country ->
            // Country clicked - checkbox toggled in adapter
        }
        
        binding.countriesRecyclerView.adapter = adapter
        adapter.submitList(allCountries)
        adapter.setSelectedCountries(selectedCountries)
    }
    
    private fun setupSearch() {
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterCountries(s.toString())
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
    }
    
    private fun setupButtons() {
        binding.btnOk.setOnClickListener {
            onCountriesSelected(adapter.getSelectedCountries())
            dismiss()
        }
        
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
    
    private fun filterCountries(query: String) {
        val filtered = CountryList.search(query)
        adapter.submitList(filtered)
    }
}
