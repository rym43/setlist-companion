package com.example.setlistcompanion.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.setlistcompanion.data.Song
import com.example.setlistcompanion.data.SongDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RepertoireViewModel(private val songDao: SongDao) : ViewModel() {

    // The screen observes this; it updates by itself after every insert.
    val songs: StateFlow<List<Song>> = songDao.getSongs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun addSong(title: String, musicalKey: String, difficulty: Int, status: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            songDao.insertSong(
                Song(
                    title = title.trim(),
                    musicalKey = musicalKey.trim(),
                    difficulty = difficulty,
                    status = status
                )
            )
        }
    }
}
