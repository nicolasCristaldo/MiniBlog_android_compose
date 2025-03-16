package com.nicolascristaldo.miniblog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nicolascristaldo.miniblog.ui.MiniBlogApp
import com.nicolascristaldo.miniblog.ui.theme.MiniBlogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniBlogTheme {
                MiniBlogApp()
            }
        }
    }
}