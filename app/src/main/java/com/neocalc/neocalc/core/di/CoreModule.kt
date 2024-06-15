package com.neocalc.neocalc.core.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.neocalc.neocalc.core.data.datasources.local.preferences.AppPreferences
import com.neocalc.neocalc.core.data.datasources.local.preferences.AppPreferencesImpl
import com.neocalc.neocalc.core.data.manager.AppSettingsManagerImpl
import com.neocalc.neocalc.core.domain.manager.AppSettingsManager
import com.neocalc.neocalc.core.domain.usecases.GetAppThemeUseCase
import com.neocalc.neocalc.core.domain.usecases.SaveAppThemeUseCase
import com.neocalc.neocalc.core.util.Constants
import com.neocalc.neocalc.history.data.local.CalculatorHistoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "Settings")

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

	@Provides
	@Singleton
	fun provideHistoryDb(application: Application): CalculatorHistoryDatabase =
		Room.databaseBuilder(
			application,
			CalculatorHistoryDatabase::class.java,
			Constants.CALCULATION_HISTORY_DB
		).build()

	@Provides
	@Singleton
	fun provideAppPreferences(
		application: Application
	): AppPreferences =
		AppPreferencesImpl(application.datastore)

	@Provides
	@Singleton
	fun provideAppSettingsManager(
		appPreferences: AppPreferences
	): AppSettingsManager =
		AppSettingsManagerImpl(appPreferences)

	@Provides
	@Singleton
	fun provideGetAppThemeUseCase(appSettingsManager: AppSettingsManager) =
		GetAppThemeUseCase(appSettingsManager)

	@Provides
	@Singleton
	fun provideSaveAppThemeUseCase(appSettingsManager: AppSettingsManager) =
		SaveAppThemeUseCase(appSettingsManager)
}