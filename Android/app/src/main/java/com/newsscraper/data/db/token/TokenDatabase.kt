package com.newsscraper.data.db.token

import android.app.Application
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.newsscraper.data.model.News
import com.newsscraper.data.model.Token
import com.newsscraper.data.net.NewsInfoProvider

@Database(entities = [Token::class], version = 1)
abstract class TokenDatabase : RoomDatabase() {

    abstract fun tokenDao(): TokenDao

    companion object {
        private val lock = Any()
        private const val DB_NAME = "Token.db"
        private var INSTANCE: TokenDatabase? = null

        fun getInstance(application: Application): TokenDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE =
                        Room.databaseBuilder(application, TokenDatabase::class.java,
                            DB_NAME
                        )
                            .allowMainThreadQueries()
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    INSTANCE?.let {
//                                        prePopulate(
//                                            it,
//                                            NewsInfoProvider.newsList
//                                        )
                                    }
                                }
                            })
                            .build()
                }
                return INSTANCE!!
            }
        }

//        fun prePopulate(database: TokenDatabase, peopleList: List<News>) {
//            for (people in peopleList) {
//                AsyncTask.execute { database.newsDao().insert(people) }
//            }
//        }
    }
}