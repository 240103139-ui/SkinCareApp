package com.example.skincareapp.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.historyDataStore by preferencesDataStore(
    name = "history_settings"
)

class HistoryPreferencesRepository(
    context: Context
) {
    private val dataStore =
        context.applicationContext.historyDataStore

    private val newestFirstKey =
        booleanPreferencesKey("history_newest_first")

    val newestFirst: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[newestFirstKey] ?: true
        }

    suspend fun setNewestFirst(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[newestFirstKey] = value
        }
    }
}