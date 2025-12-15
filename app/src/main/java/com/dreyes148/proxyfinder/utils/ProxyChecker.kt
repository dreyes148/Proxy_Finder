package com.dreyes148.proxyfinder.utils

import com.dreyes148.proxyfinder.model.Proxy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.InetSocketAddress
import java.net.Proxy as JavaProxy
import java.util.concurrent.TimeUnit

/**
 * Utility class for checking proxy validity and response time
 */
class ProxyChecker {
    
    companion object {
        private const val TEST_URL = "https://www.google.com"
        private const val TIMEOUT_SECONDS = 10L
        
        /**
         * Check if a proxy is valid by attempting to connect through it
         * Returns true if valid, false otherwise
         * Also updates the proxy's isValid and responseTime fields
         */
        suspend fun checkProxy(proxy: Proxy): Proxy = withContext(Dispatchers.IO) {
            val startTime = System.currentTimeMillis()
            
            try {
                // Create Java proxy based on protocol
                val javaProxy = when (proxy.protocol.uppercase()) {
                    "HTTP", "HTTPS" -> JavaProxy(
                        JavaProxy.Type.HTTP,
                        InetSocketAddress(proxy.ip, proxy.port.toInt())
                    )
                    "SOCKS4", "SOCKS5" -> JavaProxy(
                        JavaProxy.Type.SOCKS,
                        InetSocketAddress(proxy.ip, proxy.port.toInt())
                    )
                    else -> JavaProxy(
                        JavaProxy.Type.HTTP,
                        InetSocketAddress(proxy.ip, proxy.port.toInt())
                    )
                }
                
                // Create OkHttp client with proxy
                val client = OkHttpClient.Builder()
                    .proxy(javaProxy)
                    .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .build()
                
                // Attempt to connect
                val request = Request.Builder()
                    .url(TEST_URL)
                    .build()
                
                client.newCall(request).execute().use { response ->
                    val endTime = System.currentTimeMillis()
                    val responseTime = endTime - startTime
                    
                    if (response.isSuccessful) {
                        proxy.apply {
                            isValid = true
                            this.responseTime = responseTime
                        }
                    } else {
                        proxy.apply {
                            isValid = false
                            responseTime = null
                        }
                    }
                }
            } catch (e: Exception) {
                // Connection failed
                proxy.apply {
                    isValid = false
                    responseTime = null
                }
            }
            
            proxy
        }
        
        /**
         * Parse a list of proxies from text (ip:port format)
         */
        fun parseProxyList(text: String): List<Proxy> {
            return text.lines()
                .filter { it.isNotBlank() }
                .mapNotNull { line ->
                    val trimmed = line.trim()
                    val parts = trimmed.split(":")
                    
                    if (parts.size == 2) {
                        try {
                            Proxy(
                                ip = parts[0].trim(),
                                port = parts[1].trim(),
                                protocol = "HTTP", // Default to HTTP
                                country = "Unknown",
                                anonymity = "Unknown"
                            )
                        } catch (e: Exception) {
                            null
                        }
                    } else {
                        null
                    }
                }
        }
    }
}
