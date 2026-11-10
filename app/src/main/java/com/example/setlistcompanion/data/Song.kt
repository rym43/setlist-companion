package com.example.setlistcompanion.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// One row in the "songs" table = one song in the repertoire.
@Entity(tableName = "songs")
data class Song(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val musicalKey: String,   // e.g. "G major"
    val difficulty: Int,      // 1 (easy) to 5 (hard)
    val status: String        // "Learning" or "Ready"
)
