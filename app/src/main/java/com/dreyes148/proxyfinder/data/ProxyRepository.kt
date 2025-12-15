package com.dreyes148.proxyfinder.data

import com.dreyes148.proxyfinder.model.Proxy
import com.dreyes148.proxyfinder.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Repository for fetching and managing proxy data from multiple sources
 */
class ProxyRepository {
    
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }
    
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
    
    // Geonode API
    private val geonodeApi: ProxyApiService = Retrofit.Builder()
        .baseUrl("https://proxylist.geonode.com/api/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProxyApiService::class.java)
    
    // ProxyScan API
    private val proxyScanApi: ProxyScanApiService = Retrofit.Builder()
        .baseUrl("https://www.proxyscan.io/api/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProxyScanApiService::class.java)
    
    // GitHub raw proxy lists
    private val rawProxyApi: RawProxyApiService = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/TheSpeedX/PROXY-List/master/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RawProxyApiService::class.java)
    
    /**
     * Fetch proxies from all sources and apply filters
     */
    suspend fun fetchProxies(
        country: String? = null,
        protocols: List<String> = emptyList(),
        anonymity: List<String> = emptyList()
    ): Resource<List<Proxy>> = withContext(Dispatchers.IO) {
        try {
            val allProxies = mutableListOf<Proxy>()
            
            // Fetch from all sources in parallel
            val geonodeDeferred = async { fetchFromGeonode() }
            val proxyScanDeferred = async { fetchFromProxyScan() }
            val rawHttpDeferred = async { fetchRawProxies("HTTP") }
            val rawSocks4Deferred = async { fetchRawProxies("SOCKS4") }
            val rawSocks5Deferred = async { fetchRawProxies("SOCKS5") }
            
            // Collect results
            allProxies.addAll(geonodeDeferred.await())
            allProxies.addAll(proxyScanDeferred.await())
            allProxies.addAll(rawHttpDeferred.await())
            allProxies.addAll(rawSocks4Deferred.await())
            allProxies.addAll(rawSocks5Deferred.await())
            
            // Remove duplicates based on IP:Port
            val uniqueProxies = allProxies.distinctBy { it.toConnectionString() }
            
            // Apply filters
            val filteredProxies = applyFilters(uniqueProxies, country, protocols, anonymity)
            
            Resource.Success(filteredProxies)
        } catch (e: Exception) {
            Resource.Error("Error fetching proxies: ${e.message}")
        }
    }
    
    /**
     * Fetch proxies from Geonode API
     */
    private suspend fun fetchFromGeonode(): List<Proxy> {
        return try {
            val response = geonodeApi.getGeonodeProxies()
            if (response.isSuccessful) {
                response.body()?.data?.map { geoProxy ->
                    Proxy(
                        ip = geoProxy.ip,
                        port = geoProxy.port,
                        country = geoProxy.country ?: "Unknown",
                        protocol = geoProxy.protocols?.firstOrNull()?.uppercase() ?: "HTTP",
                        anonymity = geoProxy.anonymityLevel ?: "Unknown"
                    )
                } ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Fetch proxies from ProxyScan API
     */
    private suspend fun fetchFromProxyScan(): List<Proxy> {
        return try {
            val response = proxyScanApi.getProxyScanProxies(limit = 100)
            if (response.isSuccessful) {
                response.body()?.mapNotNull { proxy ->
                    if (proxy.ip != null && proxy.port != null) {
                        Proxy(
                            ip = proxy.ip,
                            port = proxy.port.toString(),
                            country = proxy.location?.country ?: "Unknown",
                            protocol = proxy.type?.firstOrNull()?.uppercase() ?: "HTTP",
                            anonymity = "Unknown"
                        )
                    } else {
                        null
                    }
                } ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Fetch raw proxy lists from GitHub
     */
    private suspend fun fetchRawProxies(protocol: String): List<Proxy> {
        return try {
            val response = when (protocol.uppercase()) {
                "HTTP" -> rawProxyApi.getHttpProxies()
                "SOCKS4" -> rawProxyApi.getSocks4Proxies()
                "SOCKS5" -> rawProxyApi.getSocks5Proxies()
                else -> return emptyList()
            }
            
            if (response.isSuccessful) {
                val text = response.body()?.string() ?: ""
                parseRawProxyList(text, protocol)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Parse raw text proxy list (ip:port format, one per line)
     */
    private fun parseRawProxyList(text: String, protocol: String): List<Proxy> {
        return text.lines()
            .filter { it.isNotBlank() }
            .mapNotNull { line ->
                val parts = line.trim().split(":")
                if (parts.size == 2) {
                    Proxy(
                        ip = parts[0],
                        port = parts[1],
                        protocol = protocol.uppercase(),
                        country = "Unknown",
                        anonymity = "Unknown"
                    )
                } else {
                    null
                }
            }
    }
    
    /**
     * Apply filters to proxy list
     */
    private fun applyFilters(
        proxies: List<Proxy>,
        country: String?,
        protocols: List<String>,
        anonymity: List<String>
    ): List<Proxy> {
        var filtered = proxies
        
        // Filter by country (case-insensitive)
        if (!country.isNullOrBlank() && country != "All Countries") {
            filtered = filtered.filter { 
                it.country.equals(country, ignoreCase = true) 
            }
        }
        
        // Filter by protocols
        if (protocols.isNotEmpty()) {
            filtered = filtered.filter { proxy ->
                protocols.any { it.equals(proxy.protocol, ignoreCase = true) }
            }
        }
        
        // Filter by anonymity
        if (anonymity.isNotEmpty()) {
            filtered = filtered.filter { proxy ->
                anonymity.any { it.equals(proxy.anonymity, ignoreCase = true) }
            }
        }
        
        return filtered
    }
    
    companion object {
        @Volatile
        private var instance: ProxyRepository? = null
        
        fun getInstance(): ProxyRepository {
            return instance ?: synchronized(this) {
                instance ?: ProxyRepository().also { instance = it }
            }
        }
    }
}
