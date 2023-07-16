package xyz.teamgravity.sharingdatabetweenscreens.injection.provide

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.teamgravity.sharingdatabetweenscreens.stateful_dependency.GlobalCounter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideGlobalCounter(): GlobalCounter = GlobalCounter()
}