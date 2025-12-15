package com.dreyes148.proxyfinder.data

import com.dreyes148.proxyfinder.model.GeonodeResponse
import com.dreyes148.proxyfinder.model.ProxyScanProxy
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API service for fetching proxies from various sources
 */
interface ProxyApiService {
    
    /**
     * Fetch proxies from Geonode API
     * Example: https://proxylist.geonode.com/api/proxy-list?limit=500&page=1&sort_by=lastChecked&sort_type=desc
     */
    @GET("proxy-list")
    suspend fun getGeonodeProxies(
        @Query("limit") limit: Int = 500,
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "lastChecked",
        @Query("sort_type") sortType: String = "desc"
    ): Response<GeonodeResponse>
}

/**
 * API service for ProxyScan
 */
interface ProxyScanApiService {
    
    /**
     * Fetch proxies from ProxyScan API
     * Example: https://www.proxyscan.io/api/proxy?format=json&limit=20
     */
    @GET("proxy")
    suspend fun getProxyScanProxies(
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 20,
        @Query("type") type: String? = null
    ): Response<List<ProxyScanProxy>>
}

/**
 * API service for raw text proxy lists (GitHub)
 */
interface RawProxyApiService {
    
    /**
     * Fetch HTTP proxies from GitHub raw text
     */
    @GET("http.txt")
    suspend fun getHttpProxies(): Response<ResponseBody>
    
    /**
     * Fetch SOCKS4 proxies from GitHub raw text
     */
    @GET("socks4.txt")
    suspend fun getSocks4Proxies(): Response<ResponseBody>
    
    /**
     * Fetch SOCKS5 proxies from GitHub raw text
     */
    @GET("socks5.txt")
    suspend fun getSocks5Proxies(): Response<ResponseBody>
}
