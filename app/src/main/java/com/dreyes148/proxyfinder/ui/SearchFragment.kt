package com.dreyes148.proxyfinder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dreyes148.proxyfinder.R
import com.dreyes148.proxyfinder.databinding.FragmentSearchBinding
import com.dreyes148.proxyfinder.model.Resource

/**
 * Fragment for searching and fetching proxies with filters
 */
class SearchFragment : Fragment() {
    
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ProxyViewModel by activityViewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        observeViewModel()
    }
    
    private fun setupUI() {
        // Set default country
        binding.countryInput.setText(getString(R.string.all_countries))
        
        // Get Proxies button click
        binding.btnGetProxies.setOnClickListener {
            fetchProxies()
        }
    }
    
    private fun observeViewModel() {
        viewModel.proxies.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    showLoading(true)
                    binding.statusText.text = getString(R.string.loading)
                }
                is Resource.Success -> {
                    showLoading(false)
                    val count = resource.data?.size ?: 0
                    binding.statusText.text = getString(R.string.proxy_count, count)
                    Toast.makeText(
                        context,
                        getString(R.string.proxy_count, count),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Error -> {
                    showLoading(false)
                    binding.statusText.text = getString(R.string.error_loading, resource.message ?: "Unknown")
                    Toast.makeText(
                        context,
                        getString(R.string.error_loading, resource.message ?: "Unknown"),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    
    private fun fetchProxies() {
        // Get selected country
        val country = binding.countryInput.text.toString().takeIf { 
            it.isNotBlank() && it != getString(R.string.all_countries) 
        }
        
        // Get selected protocols
        val protocols = mutableListOf<String>()
        if (binding.chipHttp.isChecked) protocols.add("HTTP")
        if (binding.chipSocks4.isChecked) protocols.add("SOCKS4")
        if (binding.chipSocks5.isChecked) protocols.add("SOCKS5")
        
        // Get selected anonymity levels
        val anonymity = mutableListOf<String>()
        if (binding.chipTransparent.isChecked) anonymity.add("Transparent")
        if (binding.chipAnonymous.isChecked) anonymity.add("Anonymous")
        if (binding.chipElite.isChecked) anonymity.add("Elite")
        
        // Fetch proxies with filters
        viewModel.fetchProxies(country, protocols, anonymity)
    }
    
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnGetProxies.isEnabled = !isLoading
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
