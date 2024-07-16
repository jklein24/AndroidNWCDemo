package com.example.nwcdemo.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nwcdemo.MainViewModel
import com.example.nwcdemo.state.Connection
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val connection by viewModel.activeConnection.collectAsState()
    val balance by viewModel.balance.collectAsState()

    val createdInvoice by viewModel.createdInvoice.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    if (connection == null) {
        LoginScreen(onLogin = viewModel::setConnectionUri, modifier = modifier)
        return
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Logged in as ${connection?.lud16 ?: "Unknown user"}")
        Button(onClick = { coroutineScope.launch { viewModel.fetchBalance() } }) {
            Text(text = "Get Balance")
        }
        Text(text = balance)
        Button(onClick = { coroutineScope.launch { viewModel.createInvoice(1000) } }) {
            Text(text = "Create Invoice")
        }
        Text(text = createdInvoice)
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        MainViewModel(
            Connection.fromUri(
                "nostr+walletconnect://walletPubKey?relay=relay&secret=sjdjkdj&lud16=jeremy@foo.bar"
            )
        )
    )
}
