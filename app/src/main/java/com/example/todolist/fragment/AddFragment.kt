package com.example.todolist.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.database.DatabaseClient
import com.example.todolist.database.TaskDao
import com.example.todolist.database.TaskModel
import com.example.todolist.databinding.ActivityHomeBinding
import com.example.todolist.databinding.FragmentAddBinding
import com.example.todolist.util.dateToDialog
import com.example.todolist.util.dateToLong
import com.example.todolist.util.dateToString
import com.example.todolist.util.dateToday

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var database: TaskDao

    //untuk creet add
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        database = DatabaseClient.getService(requireActivity()).taskDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textDate.text = dateToday()
        binding.labelDate.setOnClickListener {
            val datePicker = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                binding.textDate.text = dateToString(year, month, day)
            }
            dateToDialog(
                requireActivity(),
                datePicker
            ).show()
            binding.buttonSave.setOnClickListener {
                val task = TaskModel(
                    0,
                    binding.editTask.text.toString(),
                    false,
                    dateToLong(binding.textDate.text.toString())
                )

                Thread {
                    database.insert(task)
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireActivity(), "Sukses Di Tambah", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().navigateUp()
                    }
                }.start()
            }
        }
    }
}

