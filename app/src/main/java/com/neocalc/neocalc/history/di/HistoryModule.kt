package com.neocalc.neocalc.history.di

import com.neocalc.neocalc.history.data.local.CalculatorHistoryDatabase
import com.neocalc.neocalc.history.data.repository.CalculationHistoryRepositoryImpl
import com.neocalc.neocalc.history.domain.repository.CalculationHistoryRepository
import com.neocalc.neocalc.history.domain.usecases.ClearCalculationHistoryUseCase
import com.neocalc.neocalc.history.domain.usecases.GetCalculationHistoryUseCase
import com.neocalc.neocalc.history.domain.usecases.GetItemCountUseCase
import com.neocalc.neocalc.history.domain.usecases.UpsertCalculationHistoryUseCase
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