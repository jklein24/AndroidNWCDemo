package com.example.nwcdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nwcdemo.screen.LoginScreen
import com.example.nwcdemo.screen.MainScreen
import com.example.nwcdemo.ui.theme.NWCDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = viewModel<MainViewModel>()
            val activeConnection by viewModel.activeConnection.collectAsState()

            NWCDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (activeConnection == null) {
                        LoginScreen(
                            onLogin = viewModel::setConnectionUri,
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        MainScreen(viewModel = viewModel, modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}
