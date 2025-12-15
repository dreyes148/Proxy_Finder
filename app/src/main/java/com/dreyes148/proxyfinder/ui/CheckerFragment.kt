package com.dreyes148.proxyfinder.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dreyes148.proxyfinder.R
import com.dreyes148.proxyfinder.databinding.FragmentCheckerBinding
import com.dreyes148.proxyfinder.model.Proxy
import com.dreyes148.proxyfinder.utils.ProxyChecker

/**
 * Fragment for checking proxy validity and speed
 */
class CheckerFragment : Fragment() {
    
    private var _binding: FragmentCheckerBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: CheckerViewModel by viewModels()
    private lateinit var adapter: CheckerResultAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckerBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupUI()
        observeViewModel()
    }
    
    private fun setupRecyclerView() {
        adapter = CheckerResultAdapter { proxy ->
            // Copy single proxy to clipboard
            copySingleProxy(proxy)
        }
        binding.resultsRecyclerView.adapter = adapter
    }
    
    private fun setupUI() {
        binding.btnStartCheck.setOnClickListener {
            startCheck()
        }
        
        binding.btnStopCheck.setOnClickListener {
            stopCheck()
        }
        
        binding.btnCopyAll.setOnClickListener {
            copyAllVisibleProxies()
        }
        
        // Filter chip listeners
        binding.chipFilterAll.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.setFilter("all")
        }
        
        binding.chipFilterValid.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.setFilter("valid")
        }
        
        binding.chipFilterInvalid.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.setFilter("invalid")
        }
    }
    
    private fun observeViewModel() {
        // Observe filtered proxies instead of all checked proxies
        viewModel.filteredProxies.observe(viewLifecycleOwner) { proxies ->
            adapter.submitList(proxies.toList()) // Create new list to trigger update
        }
        
        viewModel.isChecking.observe(viewLifecycleOwner) { isChecking ->
            binding.btnStartCheck.isEnabled = !isChecking
            binding.btnStopCheck.isEnabled = isChecking
            binding.proxyInput.isEnabled = !isChecking
        }
        
        viewModel.progress.observe(viewLifecycleOwner) { (current, total) ->
            if (total > 0) {
                val percentage = (current * 100) / total
                binding.progressBar.progress = percentage
                
                if (current == total) {
                    // Checking complete
                    val validCount = viewModel.getValidProxies().size
                    val invalidCount = total - validCount
                    binding.statusText.text = getString(R.string.check_complete, validCount, invalidCount)
                } else {
                    binding.statusText.text = getString(R.string.checking_status, current, total)
                }
            }
        }
    }
    
    private fun startCheck() {
        val inputText = binding.proxyInput.text.toString()
        
        if (inputText.isBlank()) {
            Toast.makeText(context, getString(R.string.paste_proxies_first), Toast.LENGTH_SHORT).show()
            return
        }
        
        // Parse proxies from input
        val proxies = ProxyChecker.parseProxyList(inputText)
        
        if (proxies.isEmpty()) {
            Toast.makeText(context, getString(R.string.paste_proxies_first), Toast.LENGTH_SHORT).show()
            return
        }
        
        // Start checking
        viewModel.startChecking(proxies)
    }
    
    private fun stopCheck() {
        viewModel.stopChecking()
        Toast.makeText(context, "Checking stopped", Toast.LENGTH_SHORT).show()
    }
    
    private fun copyValidProxies() {
        val validProxies = viewModel.getValidProxies()
        
        if (validProxies.isEmpty()) {
            Toast.makeText(context, getString(R.string.no_valid_proxies), Toast.LENGTH_SHORT).show()
            return
        }
        
        val proxyText = validProxies.joinToString("\n") { it.toConnectionString() }
        copyToClipboard(proxyText)
        
        Toast.makeText(
            context,
            getString(R.string.valid_proxies_copied, validProxies.size),
            Toast.LENGTH_SHORT
        ).show()
    }
    
    private fun copyAllVisibleProxies() {
        val visibleProxies = viewModel.getVisibleProxies()
        
        if (visibleProxies.isEmpty()) {
            Toast.makeText(context, getString(R.string.no_valid_proxies), Toast.LENGTH_SHORT).show()
            return
        }
        
        val proxyText = visibleProxies.joinToString("\n") { it.toConnectionString() }
        copyToClipboard(proxyText)
        
        Toast.makeText(
            context,
            getString(R.string.all_proxies_copied, visibleProxies.size),
            Toast.LENGTH_SHORT
        ).show()
    }
    
    private fun copySingleProxy(proxy: Proxy) {
        copyToClipboard(proxy.toConnectionString())
        Toast.makeText(
            context,
            getString(R.string.proxy_copied_single),
            Toast.LENGTH_SHORT
        ).show()
    }
    
    private fun copyToClipboard(text: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Valid Proxies", text)
        clipboard.setPrimaryClip(clip)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
