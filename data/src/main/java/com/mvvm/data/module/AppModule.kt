package com.mvvm.data.module

import android.app.Application
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.mvvm.data.common.Constants.BASE_URL
import com.mvvm.data.common.Constants.DB_NAME
import com.mvvm.data.local.UsersDB
import com.mvvm.data.remote.ProjectService
import com.mvvm.data.repository.UserRepository
import com.mvvm.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): ProjectService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProjectService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserDatabase(app: Application): UsersDB {
        return Room.databaseBuilder(app, UsersDB::class.java, DB_NAME).build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: ProjectService, db: UsersDB): UserRepository {
        return UserRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideFirestore() = FirebaseFirestore.getInstance()
}