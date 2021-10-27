package com.example.todolist.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.database.TaskModel
import com.example.todolist.databinding.AdapterTaskBinding
import com.example.todolist.databinding.AdapterTaskComletedBinding

class TaskComletedAdapter (
    var items: ArrayList<TaskModel>,
    var listener: AdapterListener,
    ): RecyclerView.Adapter<TaskComletedAdapter.ViewHolder>() {
        class ViewHolder(val binding: AdapterTaskComletedBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        AdapterTaskComletedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.textTask.text = item.task
        holder.binding.textTask.paintFlags = holder.binding.textTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        holder.binding.textDate.text = item.date.toString()
        holder. binding.imageTask.setOnClickListener{
            listener.onClick(item)
        }
    }

    override fun getItemCount() = items.size


    fun addList(list: List<TaskModel>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    interface AdapterListener {
        fun  onClick( taskModel: TaskModel)
    }

}

