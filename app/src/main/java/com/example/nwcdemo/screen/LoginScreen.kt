package com.example.nwcdemo.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(onLogin: (String) -> Unit, modifier: Modifier = Modifier) {
    var inputString by remember { mutableStateOf("") }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier) {
        Text(text = "Type your UMA or NWC URI")
        TextField(
            value = inputString,
            onValueChange = { inputString = it },
        )
        Button(onClick = { onLogin(inputString) }) {
            Text("Login")
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen({})
}