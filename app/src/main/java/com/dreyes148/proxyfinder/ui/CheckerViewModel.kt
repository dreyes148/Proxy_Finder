package com.dreyes148.proxyfinder.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreyes148.proxyfinder.model.Proxy
import com.dreyes148.proxyfinder.utils.ProxyChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for the Checker Fragment
 */
class CheckerViewModel : ViewModel() {
    
    private val _checkedProxies = MutableLiveData<List<Proxy>>(emptyList())
    val checkedProxies: LiveData<List<Proxy>> = _checkedProxies
    
    private val _isChecking = MutableLiveData(false)
    val isChecking: LiveData<Boolean> = _isChecking
    
    private val _progress = MutableLiveData<Pair<Int, Int>>(0 to 0) // current to total
    val progress: LiveData<Pair<Int, Int>> = _progress
    
    // Filter state: "all", "valid", "invalid"
    private val _filterState = MutableLiveData("all")
    val filterState: LiveData<String> = _filterState
    
    // Filtered list based on current filter
    private val _filteredProxies = MutableLiveData<List<Proxy>>(emptyList())
    val filteredProxies: LiveData<List<Proxy>> = _filteredProxies
    
    private var checkJob: Job? = null
    
    /**
     * Start checking a list of proxies
     */
    fun startChecking(proxies: List<Proxy>) {
        if (_isChecking.value == true) return
        
        _isChecking.value = true
        _checkedProxies.value = emptyList()
        _progress.value = 0 to proxies.size
        
        checkJob = viewModelScope.launch {
            val results = mutableListOf<Proxy>()
            
            proxies.forEachIndexed { index, proxy ->
                if (!isActive) return@launch // Check if job was cancelled
                
                val checkedProxy = withContext(Dispatchers.IO) {
                    ProxyChecker.checkProxy(proxy)
                }
                
                results.add(checkedProxy)
                _checkedProxies.postValue(results.toList())
                _progress.postValue((index + 1) to proxies.size)
                
                // Update filtered list
                updateFilteredList()
            }
            
            _isChecking.value = false
        }
    }
    
    /**
     * Set filter state
     */
    fun setFilter(filter: String) {
        _filterState.value = filter
        updateFilteredList()
    }
    
    /**
     * Update filtered list based on current filter
     */
    private fun updateFilteredList() {
        val allProxies = _checkedProxies.value ?: emptyList()
        val filtered = when (_filterState.value) {
            "valid" -> allProxies.filter { it.isValid == true }
            "invalid" -> allProxies.filter { it.isValid == false }
            else -> allProxies // "all"
        }
        _filteredProxies.postValue(filtered)
    }
    
    /**
     * Stop the current checking process
     */
    fun stopChecking() {
        checkJob?.cancel()
        _isChecking.value = false
    }
    
    /**
     * Get only valid proxies
     */
    fun getValidProxies(): List<Proxy> {
        return _checkedProxies.value?.filter { it.isValid == true } ?: emptyList()
    }
    
    /**
     * Get only invalid proxies
     */
    fun getInvalidProxies(): List<Proxy> {
        return _checkedProxies.value?.filter { it.isValid == false } ?: emptyList()
    }
    
    /**
     * Get currently visible (filtered) proxies
     */
    fun getVisibleProxies(): List<Proxy> {
        return _filteredProxies.value ?: emptyList()
    }
    
    /**
     * Clear checked proxies
     */
    fun clearResults() {
        _checkedProxies.value = emptyList()
        _filteredProxies.value = emptyList()
        _progress.value = 0 to 0
    }
    
    override fun onCleared() {
        super.onCleared()
        stopChecking()
    }
}
