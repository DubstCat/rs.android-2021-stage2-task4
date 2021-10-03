package com.example.storagetask.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.storagetask.data.Item
import com.example.storagetask.ItemViewModel
import com.example.storagetask.R
import com.example.storagetask.Validate
import com.example.storagetask.databinding.FragmentAddItemBinding


class AddItemFragment : Fragment(), Validate {

    private lateinit var binding: FragmentAddItemBinding
    private lateinit var mItemViewModel: ItemViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_item, container, false)
        binding.btnAddItem.setOnClickListener{
            insertDataToDatabase()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment, MainFragment())
                .commit()

        }

        return binding.root
    }
    private fun insertDataToDatabase(){
        if(validate(binding.etName.text.toString(),binding.etAge.text.toString(),binding.etBreed.text.toString())) {
            val item = Item(
                0,
                binding.etName.text.toString(),
                binding.etAge.text.toString(),
                binding.etBreed.text.toString(),
            )
            mItemViewModel.addItem(item)
        }else{
            Toast.makeText(context, "Please enter valid data", Toast.LENGTH_SHORT).show()
        }
    }

    override fun validate(vararg strings: String):Boolean {
        strings.forEach {
            if(it.isEmpty())return false
        }
        return true
    }
}