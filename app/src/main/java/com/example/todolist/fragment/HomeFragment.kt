package com.example.todolist.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.room.Database
import com.example.todolist.R
import com.example.todolist.activity.EditActivity
import com.example.todolist.adapter.TaskAdapter
import com.example.todolist.adapter.TaskComletedAdapter
import com.example.todolist.database.DatabaseClient
import com.example.todolist.database.TaskDao
import com.example.todolist.database.TaskModel
import com.example.todolist.databinding.FragmentAddBinding
import com.example.todolist.databinding.FragmentHomeBinding
import com.example.todolist.util.dateToday
import java.nio.channels.spi.AbstractSelector
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: TaskDao
    private lateinit var taskSelected: TaskModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentHomeBinding.inflate(inflater,container, false)
        database = DatabaseClient.getService(requireActivity()).taskDao()
        return binding.root
           }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textToday.text = dateToday()
        setupList()
        setupListener()
        setupData()

    }

    override fun onStart() {
        super.onStart()
        setupData()
    }

    private fun setupList(){
        binding.listTask.adapter = taskAdapter
        binding.listTaskCompleted.adapter = taskComletedAdapter
    }
    private fun setupListener(){
        binding.labelTaskCompleted.setOnClickListener {
            if (binding.listTaskCompleted.visibility == View.GONE){
                binding.listTaskCompleted.visibility = View.VISIBLE
                binding.imageCompleted.setImageResource(R.drawable.ic_arrow_down)
        }   else {
            binding.listTaskCompleted.visibility = View.VISIBLE
                binding.imageCompleted.setImageResource(R.drawable.ic_arrow_right)
            }
        }
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
            //            testInsert()
        }
    }
    private fun setupData(){
        database.taskAll( false).observe(viewLifecycleOwner, {
            Log.e("taskAll", it.toString())
            taskAdapter.addList( it )
        })
        database.taskAll( true).observe(viewLifecycleOwner, {
            Log.e("taskAllCompleted", it.toString())
            taskComletedAdapter.addList(it)
        })
    }

    private val taskAdapter by lazy {
        TaskAdapter(arrayListOf(), object : TaskAdapter.AdapterListener {
            override fun onUpdate(taskModel: TaskModel) {
                taskSelected = taskModel
                taskSelected.comleted = true
                Thread {
                    database.update(taskSelected)
                } .start()
            }

            override fun onDetail(taskModel: TaskModel) {
                startActivity(Intent(requireActivity(), EditActivity::class.java)
                    .putExtra("intent_task", taskModel)
                )
            }

        })
    }

    private val taskComletedAdapter by lazy {
        TaskComletedAdapter(arrayListOf(), object : TaskComletedAdapter.AdapterListener {
            override fun onClick(taskModel: TaskModel) {
                taskSelected = taskModel
                taskSelected.comleted = false
                Thread {
                    database.update(taskSelected)
                } .start()
            }

        })
    }

}