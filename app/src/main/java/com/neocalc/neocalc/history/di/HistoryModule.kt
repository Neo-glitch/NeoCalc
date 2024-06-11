package com.neocalc.neocalc.history.di

import android.app.Application
import androidx.room.Room
import com.neocalc.neocalc.core.util.Constants
import com.neocalc.neocalc.history.data.local.CalculatorHistoryDatabase
import com.neocalc.neocalc.history.data.repository.CalculationHistoryRepositoryImpl
import com.neocalc.neocalc.history.domain.repository.CalculationHistoryRepository
import com.neocalc.neocalc.history.domain.use_cases.ClearCalculationHistoryUseCase
import com.neocalc.neocalc.history.domain.use_cases.GetCalculationHistoryUseCase
import com.neocalc.neocalc.history.domain.use_cases.GetItemCountUseCase
import com.neocalc.neocalc.history.domain.use_cases.UpsertCalculationHistoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HistoryModule {


	@Provides
	@Singleton
	fun provideHistoryDb(application: Application): CalculatorHistoryDatabase {
		return Room.databaseBuilder(
			application,
			CalculatorHistoryDatabase::class.java,
			Constants.CALCULATION_HISTORY_DB
		).build()
	}

	@Provides
	@Singleton
	fun provideCalculationHistoryRepository(historyDatabase: CalculatorHistoryDatabase) : CalculationHistoryRepository{
		return CalculationHistoryRepositoryImpl(historyDatabase)
	}

	@Provides
	@Singleton
	fun provideGetHistoryUseCase(repository: CalculationHistoryRepository) =
		GetCalculationHistoryUseCase(repository)

	@Provides
	@Singleton
	fun provideUpsertHistoryUseCase(repository: CalculationHistoryRepository) =
		UpsertCalculationHistoryUseCase(repository)

	@Provides
	@Singleton
	fun provideClearHistoryUseCase(repository: CalculationHistoryRepository) =
		ClearCalculationHistoryUseCase(repository)

	@Provides
	@Singleton
	fun provideGetItemCountUseCase(repository: CalculationHistoryRepository) = GetItemCountUseCase(repository)



}