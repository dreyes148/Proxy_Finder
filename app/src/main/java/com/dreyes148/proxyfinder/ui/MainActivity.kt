package com.dreyes148.proxyfinder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dreyes148.proxyfinder.R
import com.dreyes148.proxyfinder.databinding.ActivityMainBinding

/**
 * Main Activity that hosts the bottom navigation and fragments
 */
class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var proxyViewModel: ProxyViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Initialize shared ViewModel
        proxyViewModel = ViewModelProvider(this)[ProxyViewModel::class.java]
        
        // Set up bottom navigation
        setupBottomNavigation()
        
        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(SearchFragment())
        }
    }
    
    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_search -> {
                    loadFragment(SearchFragment())
                    true
                }
                R.id.navigation_list -> {
                    loadFragment(ListFragment())
                    true
                }
                R.id.navigation_checker -> {
                    loadFragment(CheckerFragment())
                    true
                }
                else -> false
            }
        }
    }
    
    private fun loadFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
