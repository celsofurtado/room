package br.com.fiap.roomprof.repository

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.fiap.roomprof.model.Game

//@Database(entities = [Game::class], version = 2)
@Database(entities = [Game::class], version = 2)
abstract class GameDataBase() : RoomDatabase() {

    abstract fun gameDao(): GameDAO

    // Singleton
    companion object {

        // Migração de versão de banco

        val migration_1_2: Migration = object: Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE game ADD COLUMN foto BLOB DEFAULT NULL")
            }
        }

        // ------------------------------------


        private lateinit var instance: GameDataBase

        fun getDatabase(context: Context): GameDataBase {
            if (!::instance.isInitialized){
                instance = Room.databaseBuilder(context, GameDataBase::class.java, "gameDB")
                    .addMigrations(migration_1_2) // essa linha é para migração
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }

//        fun getDatabase(context: Context): GameDataBase {
//            if (!::instance.isInitialized){
//                instance = Room.databaseBuilder(context, GameDataBase::class.java, "gameDB")
//                    .allowMainThreadQueries()
//                    .build()
//            }
//            return instance
//        }
    }

}