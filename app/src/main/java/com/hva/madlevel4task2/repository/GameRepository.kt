package com.hva.madlevel4task2.repository

import android.content.Context
import com.hva.madlevel4task2.dao.GameDao
import com.hva.madlevel4task2.database.GameRoomDatabase
import com.hva.madlevel4task2.model.Game

class GameRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database =
            GameRoomDatabase.getDatabase(context)
            gameDao = database!!.gameDao()
    }

    suspend fun getAllGames() : List<Game> {
        return gameDao.getAllGames()
    }

    suspend fun insertGame(game: Game) {
        gameDao.insertGame(game)
    }

    suspend fun deleteGame(game: Game) {
        gameDao.deleteGame(game)
    }

    suspend fun deleteAllGames() {
        gameDao.deleteAllGames()
    }

    suspend fun getAmountOfGamesWon(won: String): Int {
        return gameDao.getAmountOfGamesWon(won)
    }

    suspend fun getAmountOfGamesDrawn(draw: String):Int {
        return gameDao.getAmountOfGamesWon(draw)
    }

    suspend fun getAmountOfGamesLost(lost: String):Int {
        return gameDao.getAmountOfGamesWon(lost)
    }
}