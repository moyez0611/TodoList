package com.example.todolist.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.todolist.R
import com.example.todolist.database.DatabaseClient
import com.example.todolist.database.TaskDao
import com.example.todolist.database.TaskModel
import com.example.todolist.databinding.FragmentUpdateBinding
import com.example.todolist.util.dateToDialog
import com.example.todolist.util.dateToLong
import com.example.todolist.util.dateToString
import kotlin.concurrent.thread

class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding
    private lateinit var database : TaskDao
    private lateinit var detail : TaskModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        database = DatabaseClient.getService(requireActivity()).taskDao()
        detail = requireArguments().getSerializable("argumnent_task") as TaskModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            setupData()
            setupListener()
    }


    private fun setupData(){
        binding.editTask.setText(detail.task)
        binding.textDate.text = dateToString(detail.date)
    }
    private fun setupListener() {
        binding.labelDate.setOnClickListener {
            val datePicker = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                binding.textDate.text = dateToString(year, month, day)
            }
            dateToDialog(
                requireActivity(), datePicker).show()
        }
        binding.buttonSave.setOnClickListener {
            detail.task = binding.editTask.text.toString()
            detail.date = dateToLong(binding.textDate.text.toString())
            Thread {
                database.update(detail)
                requireActivity().runOnUiThread {
                    Toast.makeText(requireActivity(), "Perubahan Disimpan", Toast.LENGTH_SHORT).show()
                    requireActivity().finish()
                }
            }.start()
        }
        binding.buttonDelete.setOnClickListener {
            Thread {
                database.delete(detail)
                requireActivity().runOnUiThread {
                    Toast.makeText(requireActivity(), "Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                    requireActivity().finish()
                }
            }.start()
        }

    }
}