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
import androidx.fragment.app.activityViewModels
import com.dreyes148.proxyfinder.R
import com.dreyes148.proxyfinder.databinding.FragmentListBinding

/**
 * Fragment for displaying the list of fetched proxies
 */
class ListFragment : Fragment() {
    
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ProxyViewModel by activityViewModels()
    private lateinit var adapter: ProxyAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupUI()
        observeViewModel()
    }
    
    private fun setupRecyclerView() {
        adapter = ProxyAdapter()
        binding.recyclerView.adapter = adapter
    }
    
    private fun setupUI() {
        binding.btnCopySelected.setOnClickListener {
            copyAllProxies()
        }
    }
    
    private fun observeViewModel() {
        viewModel.proxyList.observe(viewLifecycleOwner) { proxies ->
            if (proxies.isEmpty()) {
                showEmptyState()
            } else {
                showProxyList(proxies)
            }
        }
    }
    
    private fun showProxyList(proxies: List<com.dreyes148.proxyfinder.model.Proxy>) {
        binding.recyclerView.visibility = View.VISIBLE
        binding.emptyText.visibility = View.GONE
        
        adapter.submitList(proxies)
        binding.proxyCountText.text = getString(R.string.proxy_count, proxies.size)
    }
    
    private fun showEmptyState() {
        binding.recyclerView.visibility = View.GONE
        binding.emptyText.visibility = View.VISIBLE
        binding.proxyCountText.text = getString(R.string.proxy_count, 0)
    }
    
    private fun copyAllProxies() {
        val proxies = viewModel.getCurrentProxies()
        
        if (proxies.isEmpty()) {
            Toast.makeText(context, getString(R.string.select_proxies_first), Toast.LENGTH_SHORT).show()
            return
        }
        
        val proxyText = proxies.joinToString("\n") { it.toConnectionString() }
        copyToClipboard(proxyText)
        
        Toast.makeText(
            context,
            getString(R.string.copied_to_clipboard, proxies.size),
            Toast.LENGTH_SHORT
        ).show()
    }
    
    private fun copyToClipboard(text: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Proxies", text)
        clipboard.setPrimaryClip(clip)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
