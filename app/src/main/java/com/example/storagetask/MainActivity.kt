package com.example.storagetask

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.storagetask.databinding.ActivityMainBinding
import com.example.storagetask.fragments.MainFragment
import com.example.storagetask.fragments.SettingsFragment


class MainActivity : AppCompatActivity() {

private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.activity_main_fragment, MainFragment::class.java, null)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_item -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_main_fragment, SettingsFragment::class.java, null)
                    .commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_main_fragment, MainFragment::class.java, null)
            .commit()
    }
}
