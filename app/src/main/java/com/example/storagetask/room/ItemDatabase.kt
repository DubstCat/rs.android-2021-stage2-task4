package com.example.storagetask.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.storagetask.data.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDao
    companion object {

        @Volatile
        private var INSTANCE: ItemDatabase? = null
        private val database_name = "item_database"

        fun getDatabase(context: Context): ItemDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }else{
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ItemDatabase::class.java,
                        database_name
                    ).build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
}
