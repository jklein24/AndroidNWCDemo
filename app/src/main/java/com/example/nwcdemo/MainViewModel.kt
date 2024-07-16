package com.example.nwcdemo

import androidx.lifecycle.ViewModel
import com.example.nwcdemo.state.Connection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import rust.nostr.protocol.MakeInvoiceRequestParams
import rust.nostr.protocol.NostrWalletConnectUri
import rust.nostr.sdk.Nwc

class MainViewModel(
    initialConnection: Connection? = null,
) : ViewModel() {
    private val _activeConnection = MutableStateFlow(initialConnection)
    val activeConnection: StateFlow<Connection?> = _activeConnection.asStateFlow()
    private val _balance = MutableStateFlow("")
    val balance: StateFlow<String> = _balance.asStateFlow()
    private val _createdInvoice = MutableStateFlow("")
    val createdInvoice: StateFlow<String> = _createdInvoice.asStateFlow()
    private var nwc: Nwc? = null

    fun setConnectionUri(uri: String) {
        _activeConnection.value = Connection.fromUri(uri)
        nwc?.close()
        nwc = Nwc(uri = NostrWalletConnectUri.parse(uri))
    }

    suspend fun fetchBalance() {
        nwc?.getBalance()?.let { balance ->
            _balance.value = "$balance SATs"
        }
    }

    suspend fun createInvoice(amount: Long, memo: String? = null, expiry: Long? = null) {
        nwc?.makeInvoice(
            MakeInvoiceRequestParams(
                amount = amount.toULong(),
                description = memo,
                descriptionHash = null,
                expiry = expiry?.toULong(),
            )
        )?.let { result ->
            _createdInvoice.value = result.invoice
        }
    }

    override fun onCleared() {
        nwc?.close()
    }
}
