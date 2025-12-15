package com.dreyes148.proxyfinder.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreyes148.proxyfinder.data.ProxyRepository
import com.dreyes148.proxyfinder.model.Proxy
import com.dreyes148.proxyfinder.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Shared ViewModel for managing proxy data across fragments
 */
class ProxyViewModel : ViewModel() {
    
    private val repository = ProxyRepository.getInstance()
    
    // LiveData for proxy list
    private val _proxies = MutableLiveData<Resource<List<Proxy>>>()
    val proxies: LiveData<Resource<List<Proxy>>> = _proxies
    
    // LiveData for proxy list (shared between Search and List fragments)
    private val _proxyList = MutableLiveData<List<Proxy>>(emptyList())
    val proxyList: LiveData<List<Proxy>> = _proxyList
    
    /**
     * Fetch proxies from all sources with filters
     */
    fun fetchProxies(
        countries: List<String> = emptyList(),
        protocols: List<String> = emptyList(),
        anonymity: List<String> = emptyList()
    ) {
        viewModelScope.launch {
            _proxies.value = Resource.Loading()
            
            val result = withContext(Dispatchers.IO) {
                repository.fetchProxies(countries, protocols, anonymity)
            }
            
            _proxies.value = result
            
            // Update shared proxy list if successful
            if (result is Resource.Success) {
                _proxyList.value = result.data ?: emptyList()
            }
        }
    }
    
    /**
     * Clear the proxy list
     */
    fun clearProxies() {
        _proxyList.value = emptyList()
        _proxies.value = Resource.Success(emptyList())
    }
    
    /**
     * Get current proxy list
     */
    fun getCurrentProxies(): List<Proxy> {
        return _proxyList.value ?: emptyList()
    }
}
