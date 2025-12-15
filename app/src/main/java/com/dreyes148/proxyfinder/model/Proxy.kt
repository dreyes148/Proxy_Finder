package com.dreyes148.proxyfinder.model

/**
 * Data class representing a proxy server
 */
data class Proxy(
    val ip: String,
    val port: String,
    val country: String = "Unknown",
    val protocol: String = "HTTP",
    val anonymity: String = "Unknown",
    var isValid: Boolean? = null,
    var responseTime: Long? = null // in milliseconds
) {
    /**
     * Returns the proxy in ip:port format
     */
    fun toConnectionString(): String = "$ip:$port"
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Proxy
        return ip == other.ip && port == other.port
    }
    
    override fun hashCode(): Int {
        var result = ip.hashCode()
        result = 31 * result + port.hashCode()
        return result
    }
}
