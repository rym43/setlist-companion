package com.example.setlistcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.setlistcompanion.data.AppDatabase
import com.example.setlistcompanion.ui.RepertoireScreen
import com.example.setlistcompanion.ui.RepertoireViewModel
import com.example.setlistcompanion.ui.theme.SetlistCompanionTheme

class MainActivity : ComponentActivity() {

    // The ViewModel gets the DAO from the database; it survives screen rotation.
    private val viewModel: RepertoireViewModel by viewModels {
        viewModelFactory {
            initializer {
                RepertoireViewModel(AppDatabase.getDatabase(applicationContext).songDao())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SetlistCompanionTheme {
                val songs by viewModel.songs.collectAsStateWithLifecycle()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RepertoireScreen(
                        songs = songs,
                        onAddSong = viewModel::addSong,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
