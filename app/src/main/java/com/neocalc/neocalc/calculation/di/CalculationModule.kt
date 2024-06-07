package com.neocalc.neocalc.calculation.di

import android.app.Application
import com.faendir.rhino_android.RhinoAndroidHelper
import com.neocalc.neocalc.calculation.data.repository.CalculationRepositoryImpl
import com.neocalc.neocalc.calculation.domain.formatter.CalculationInputFormatter
import com.neocalc.neocalc.calculation.domain.repository.CalculationRepository
import com.neocalc.neocalc.calculation.domain.use_cases.CalculateResultUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.mozilla.javascript.ScriptableObject
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CalculationModule {

	@Singleton
	@Provides
	fun provideRhino(application : Application) : RhinoAndroidHelper {
		return RhinoAndroidHelper(application)
	}

	@Singleton
	@Provides
	fun provideCalculationRepository(rhino: RhinoAndroidHelper) : CalculationRepository{
		return CalculationRepositoryImpl(rhino)
	}

	@Singleton
	@Provides
	fun provideCalculateResultUseCase(
		calculationRepository: CalculationRepository
	): CalculateResultUseCase{
		return CalculateResultUseCase(calculationRepository)
	}

}