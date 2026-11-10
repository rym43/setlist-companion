package com.example.setlistcompanion.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    // Flow: Room re-emits the list every time the table changes.
    @Query("SELECT * FROM songs ORDER BY title")
    fun getSongs(): Flow<List<Song>>

    @Insert
    suspend fun insertSong(song: Song)
}
