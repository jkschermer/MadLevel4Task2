package com.hva.madlevel4task2.dao

import androidx.room.*
import com.hva.madlevel4task2.model.Game

@Dao
interface GameDao {

    @Query("SELECT * FROM gameTable")
    suspend fun getAllGames() : List<Game>

    @Insert
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Query("DELETE FROM gameTable")
    suspend fun deleteAllGames()

    @Query("SELECT COUNT(amountOfGamesWon) from gameTable where result LIKE :win")
    suspend fun getAmountOfGamesWon(win: String) : Int

    @Query("SELECT COUNT(amountOfGamesDrawn) from gameTable where result LIKE :draw")
    suspend fun getAmountOfGamesDrawn(draw: String) : Int

    @Query("SELECT COUNT(amountOfGamesLost) from gameTable where result LIKE :lost")
    suspend fun getAmountOfGamesLost(lost: String) : Int
}