package com.example.storagetask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.storagetask.data.Item
import com.example.storagetask.room.ItemDatabase
import com.example.storagetask.room.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Item>>
    private val repository: ItemRepository

    init {
        val itemDao = ItemDatabase.getDatabase(application).itemDao()
        repository = ItemRepository(itemDao)
        readAllData = repository.readAllData
    }

    fun addItem(item: Item){
        viewModelScope.launch(Dispatchers.IO){
            repository.addItem(item)
        }
    }
    fun nukeTable(){
        viewModelScope.launch(Dispatchers.IO){
            repository.nukeTable()
        }
    }
    fun delete(item: Item){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(item)
        }
    }

    fun update(name: String, age: String, breed: String, item_id: Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.update(name, age, breed, item_id)
        }
    }
}