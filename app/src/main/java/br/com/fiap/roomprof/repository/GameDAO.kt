package br.com.fiap.roomprof.repository

import androidx.room.*
import br.com.fiap.roomprof.model.Game

@Dao
interface GameDAO {

    @Insert
    fun save(game: Game): Long

    @Update
    fun update(game: Game): Int

    @Delete
    fun delete(game: Game)

    @Query("SELECT * FROM game")
    fun getAllGames(): List<Game>

    @Query("SELECT * FROM game WHERE id = :id")
    fun getGameById(id: Int): Game

    @Query("SELECT * FROM game WHERE finalizado = 1")
    fun getGameFinished(): List<Game>

}