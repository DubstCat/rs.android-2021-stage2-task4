package com.example.storagetask.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storagetask.ItemAdapter
import com.example.storagetask.ItemViewModel
import com.example.storagetask.R
import com.example.storagetask.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var binding:FragmentMainBinding
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var mItemViewModel: ItemViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manager = LinearLayoutManager(context)
        refreshList()

        binding.fbAdd.apply {
            setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_main_fragment, AddItemFragment())
                    .commit()
            }
            setOnLongClickListener{
                mItemViewModel.nukeTable()
                true
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    private fun refreshList(){
        val recyclerView = binding.rvItems
        recyclerView.layoutManager = manager
        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        mItemViewModel.readAllData.observe(viewLifecycleOwner, Observer {
                items -> run{
            val adapter = ItemAdapter(items)

            adapter.setOnItemClickListener(object: ItemAdapter.ClickListener {
                override fun onItemClick(position: Int, v: View?) {

                    val linearLayout = LinearLayout(context)
                    linearLayout.orientation = LinearLayout.HORIZONTAL
                    val inputNameEditTextField = EditText(context)
                    val inputAgeEditTextField = EditText(context)
                    val inputBreedEditTextField = EditText(context)

                    inputNameEditTextField.hint = "Name"
                    inputAgeEditTextField.hint = "Age"
                    inputBreedEditTextField.hint = "Breed"

                    linearLayout.apply {
                        orientation = LinearLayout.VERTICAL
                        addView(inputNameEditTextField)
                        addView(inputAgeEditTextField)
                        addView(inputBreedEditTextField)
                    }

                    val dialog = AlertDialog.Builder(context!!).apply {
                        setTitle("Edit item")
                        setMessage("Enter new information for an item")
                        setNegativeButton("Cancel",null)
                        setView(linearLayout)
                        setPositiveButton("Input") { _, _ ->
                            val editNameTextInput: String = inputNameEditTextField.text.toString()
                            val editAgeTextInput: String = inputAgeEditTextField.text.toString()
                            val editBreedTextInput: String = inputBreedEditTextField.text.toString()
                            if(editNameTextInput.isNotEmpty() && editAgeTextInput.isNotEmpty() &&editBreedTextInput.isNotEmpty()) {
                                mItemViewModel.update(
                                    name = editNameTextInput,
                                    age = editAgeTextInput,
                                    breed = editBreedTextInput,
                                    item_id = adapter.getItem(position).id
                                )
                            }else{
                                Toast.makeText(context, "Wrong input", Toast.LENGTH_SHORT).show()
                            }

                        }
                        create()
                    }
                    dialog.show()
                }

                override fun onItemLongClick(position: Int, v: View?) {
                    Toast.makeText(context, "Deleted item with index $position", Toast.LENGTH_SHORT).show()
                    mItemViewModel.delete(adapter.getItem(position))
                }

            })
            recyclerView.adapter = adapter
        }
        })
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }
}