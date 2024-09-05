package com.sprtcoding.salita.database.repository

import android.util.Log
import com.sprtcoding.salita.database.contract.IAssessmentContract
import com.sprtcoding.salita.database.dao.PlayerDao
import com.sprtcoding.salita.database.entities.PlayerEntities
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlayerRepository(private val playerDao: PlayerDao) {
    suspend fun addPlayer(playerEntities: PlayerEntities, iPlayer: IAssessmentContract) {
        try {
            playerDao.insertPlayer(playerEntities)
            iPlayer.onPlayerAdd(true, "Name save successfully")
        } catch(e: Exception) {
            iPlayer.onPlayerAdd(false, e.message.toString())
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getPlayer(name: String, iPlayer: IAssessmentContract.IPlayer) {
        GlobalScope.launch(Dispatchers.IO) {  // Use appropriate scope for your use case
            try {
                val player = playerDao.getPlayer(name)
                withContext(Dispatchers.Main) {
                    iPlayer.onPlayer(player)
                }
            } catch (err: Exception) {
                withContext(Dispatchers.Main) {
                    iPlayer.onPlayer(null)
                }
            }
        }
    }

    suspend fun isPlayerExists(playerName: String): Boolean {
        return withContext(Dispatchers.IO) {
            playerDao.isPlayerExists(playerName)
        }
    }

    private suspend fun clearPlayer() {
        try {
            // Clear local database after syncing
            withContext(Dispatchers.IO) { playerDao.clearPlayer() }
        } catch (e: Exception) {
            // Handle exceptions
            Log.e("MedsRepository", "Error syncing with FireStore", e)
        }
    }
}