package com.dreyes148.proxyfinder.model

import com.google.gson.annotations.SerializedName

/**
 * Response model for Geonode API
 * https://proxylist.geonode.com/api/proxy-list
 */
data class GeonodeResponse(
    @SerializedName("data")
    val data: List<GeonodeProxy>
)

data class GeonodeProxy(
    @SerializedName("ip")
    val ip: String,
    @SerializedName("port")
    val port: String,
    @SerializedName("country")
    val country: String?,
    @SerializedName("protocols")
    val protocols: List<String>?,
    @SerializedName("anonymityLevel")
    val anonymityLevel: String?
)

/**
 * Response model for ProxyScan API
 * https://www.proxyscan.io/api/proxy
 */
data class ProxyScanProxy(
    @SerializedName("Ip")
    val ip: String?,
    @SerializedName("Port")
    val port: Int?,
    @SerializedName("Type")
    val type: List<String>?,
    @SerializedName("Location")
    val location: ProxyScanLocation?
)

data class ProxyScanLocation(
    @SerializedName("country")
    val country: String?
)
