package com.example.storagetask.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.storagetask.data.Item

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(item: Item)

    @Query("SELECT * FROM item_table ORDER BY :sorting_mode ASC")
    fun readAllData(sorting_mode:String): LiveData<List<Item>>

    @Query("DELETE FROM item_table")
    fun nukeTable()

    @Delete
    fun delete(item: Item)

    @Query("UPDATE item_table SET name =:name, age = :age, breed = :breed WHERE id = :item_id")
    fun update(name: String, age: String, breed: String, item_id: Int)



}