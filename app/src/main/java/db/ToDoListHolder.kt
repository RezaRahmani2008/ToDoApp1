package db

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable
import model.ToDo

@Serializable
data class ToDoListHolder(
    @Serializable(TodoListSerializer2::class)
    val todoList: PersistentList<ToDo> = persistentListOf()
)
