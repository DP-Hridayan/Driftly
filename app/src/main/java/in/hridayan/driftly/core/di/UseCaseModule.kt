package `in`.hridayan.driftly.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import `in`.hridayan.driftly.calender.domain.usecase.GetWeekDayLabelsUseCase
import `in`.hridayan.driftly.settings.domain.usecase.GetAllChangelogsUseCase

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetWeekDayLabelsUseCase(): GetWeekDayLabelsUseCase =
        GetWeekDayLabelsUseCase()

    @Provides
    fun provideGetChangelogsUseCase(@ApplicationContext context: Context): GetAllChangelogsUseCase =
        GetAllChangelogsUseCase(context)
}