package com.example.todoapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.dataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.todoapp.databinding.FragmentAddTaskBinding
import db.TodoListSerializer
import db.TodoListSerializer2
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.launch
import model.ToDo
import utils.Picker
import utils.fullDate
import utils.hour
import utils.minute



val Context.dataStore by dataStore("mainFile.json", TodoListSerializer())
class AddTask : Fragment() {
    private lateinit var binding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.hi.setOnClickListener {
            Picker(parentFragmentManager,binding.hi)
        }
        binding.AddTask.setOnClickListener{
            val newToDo = ToDo(
                binding.titleEditText.editText?.text.toString(),
                binding.descriptionEditText.editText?.text.toString(),
                "$hour:$minute", fullDate, false)

            lifecycleScope.launch{
                requireContext().dataStore.updateData {
                    it.copy(
                        it.todoList.mutate {
                            it.add(newToDo)
                        }
                    )
                }
                Navigation.findNavController(binding.AddTask).navigate(R.id.action_addTask_to_correntToDos)

            }
        }
    }
}
