package com.example.nwcdemo.state

import android.net.Uri

@OptIn(ExperimentalStdlibApi::class)
data class Connection(
    val relay: String,
    val secret: String,
    val walletPubKey: String,
    val lud16: String?,
) {
    val uri: String
        get() = "nostr+walletconnect://$walletPubKey?relay=$relay&secret=$secret" +
            (lud16?.let { "&lud16=$it" } ?: "")

    val walletPubKeyBytes
        get() = walletPubKey.hexToByteArray()

    companion object {
        fun fromUri(uri: String): Connection {
            if (!uri.startsWith("nostr+walletconnect://")) {
                throw IllegalArgumentException("Invalid NWC URI")
            }
            val parsedUri = Uri.parse(uri)
            return Connection(
                relay = parsedUri.getQueryParameter("relay")!!,
                secret = parsedUri.getQueryParameter("secret")!!,
                walletPubKey = requireNotNull(parsedUri.host) { "Missing host" },
                lud16 = parsedUri.getQueryParameter("lud16"),
            )
        }
    }
}