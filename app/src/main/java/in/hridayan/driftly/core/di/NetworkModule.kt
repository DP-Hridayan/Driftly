package `in`.hridayan.driftly.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.hridayan.driftly.settings.data.remote.api.GitHubApi
import `in`.hridayan.driftly.settings.data.repository.UpdateRepositoryImpl
import `in`.hridayan.driftly.settings.domain.repository.UpdateRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    @Provides
    @Singleton
    fun provideGitHubApi(client: HttpClient): GitHubApi = GitHubApi(client)

    @Provides
    @Singleton
    fun provideUpdateRepository(api: GitHubApi): UpdateRepository = UpdateRepositoryImpl(api)
}