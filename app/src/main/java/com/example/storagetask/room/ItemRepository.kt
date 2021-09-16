package com.example.storagetask.room

import androidx.lifecycle.LiveData
import com.example.storagetask.data.Item
import com.example.storagetask.data.Sort

class ItemRepository(private val itemDao: ItemDao) {

    val readAllData: LiveData<List<Item>> = itemDao.readAllData(Sort.sorting_mode!!)

    suspend fun addItem(item: Item){
        itemDao.addItem(item)
    }
    fun nukeTable(){
        itemDao.nukeTable()
    }
    fun delete(item: Item){
        itemDao.delete(item)
    }
    fun update(name: String, age: String, breed: String, item_id: Int){
        itemDao.update(name, age, breed, item_id)
    }
}