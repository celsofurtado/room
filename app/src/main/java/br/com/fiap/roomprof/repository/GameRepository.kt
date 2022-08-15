package br.com.fiap.roomprof.repository

import android.content.Context
import android.util.Log
import br.com.fiap.roomprof.model.Game

class GameRepository(context: Context) {

    // Ganhando acesso ao banco de dados
    private val db = GameDataBase.getDatabase(context).gameDao()

    // Buscar um game pelo id
    fun getGameById(id: Int): Game {
        return db.getGameById(id)
    }

    // Inserir um game
    fun save(game: Game): Long {
        return db.save(game)
    }

    // Buscar todos os games
    fun getAllGames(): List<Game> {
        return db.getAllGames()
    }

    // Listar todos os games finalizados
    fun getGamesFinished(): List<Game> {
        return db.getGameFinished()
    }

    // Atulizar um game
    fun update(game: Game): Int {
        return db.update(game)
    }

    // Excluir um game
    fun delete(game: Game) {
        db.delete(game)
    }

}