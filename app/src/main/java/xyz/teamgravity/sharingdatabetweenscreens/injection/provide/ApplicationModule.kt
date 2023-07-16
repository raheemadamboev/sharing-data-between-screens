package xyz.teamgravity.sharingdatabetweenscreens.injection.provide

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.teamgravity.sharingdatabetweenscreens.persistent_storage.Session
import xyz.teamgravity.sharingdatabetweenscreens.persistent_storage.SessionCache
import xyz.teamgravity.sharingdatabetweenscreens.stateful_dependency.GlobalCounter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideGlobalCounter(): GlobalCounter = GlobalCounter()

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences("name", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideJsonAdapter(): JsonAdapter<Session> = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter(Session::class.java)

    @Provides
    @Singleton
    fun provideSessionCache(
        sharedPreferences: SharedPreferences,
        jsonAdapter: JsonAdapter<Session>
    ): SessionCache = SessionCache(
        preferences = sharedPreferences,
        adapter = jsonAdapter
    )
}