package com.example.newsscraper.data.db

import android.app.Application
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.newsscraper.data.model.News
import com.example.newsscraper.data.net.NewsInfoProvider

@Database(entities = [News::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        private val lock = Any()
        private const val DB_NAME = "News.db"
        private var INSTANCE: NewsDatabase? = null

        fun getInstance(application: Application): NewsDatabase {
            synchronized(NewsDatabase.lock) {
                if (NewsDatabase.INSTANCE == null) {
                    NewsDatabase.INSTANCE =
                        Room.databaseBuilder(application, NewsDatabase::class.java, NewsDatabase.DB_NAME)
                            .allowMainThreadQueries()
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    NewsDatabase.INSTANCE?.let {
                                        NewsDatabase.prePopulate(it, NewsInfoProvider.newsList)
                                    }
                                }
                            })
                            .build()
                }
                return NewsDatabase.INSTANCE!!
            }
        }

        fun prePopulate(database: NewsDatabase, peopleList: List<News>) {
            for (people in peopleList) {
                AsyncTask.execute { database.newsDao().insert(people) }
            }
        }
    }
}