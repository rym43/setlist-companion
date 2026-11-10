package com.example.setlistcompanion.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.setlistcompanion.data.Song

@Composable
fun RepertoireScreen(
    songs: List<Song>,
    onAddSong: (title: String, musicalKey: String, difficulty: Int, status: String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Form state only lives while the user is typing; saved songs live in Room.
    var title by remember { mutableStateOf("") }
    var musicalKey by remember { mutableStateOf("") }
    var difficulty by remember { mutableIntStateOf(3) }
    var status by remember { mutableStateOf("Learning") }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("My Repertoire", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Song title") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = musicalKey,
            onValueChange = { musicalKey = it },
            label = { Text("Key (e.g. G major)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Text("Difficulty")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            for (level in 1..5) {
                FilterChip(
                    selected = difficulty == level,
                    onClick = { difficulty = level },
                    label = { Text("$level") }
                )
            }
        }

        Text("Status")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            for (option in listOf("Learning", "Ready")) {
                FilterChip(
                    selected = status == option,
                    onClick = { status = option },
                    label = { Text(option) }
                )
            }
        }

        Button(
            onClick = {
                onAddSong(title, musicalKey, difficulty, status)
                title = ""
                musicalKey = ""
            },
            enabled = title.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add song")
        }

        if (songs.isEmpty()) {
            Text("No songs yet. Add your first one above.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(songs, key = { it.id }) { song ->
                    SongCard(song)
                }
            }
        }
    }
}

@Composable
fun SongCard(song: Song) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(song.title, style = MaterialTheme.typography.titleMedium)
            Text("Key: ${song.musicalKey.ifBlank { "—" }}  ·  Difficulty: ${song.difficulty}/5  ·  ${song.status}")
        }
    }
}
