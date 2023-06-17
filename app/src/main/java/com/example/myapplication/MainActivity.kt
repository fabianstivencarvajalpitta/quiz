package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val todoList = remember { mutableStateListOf(TodoItemData("Tarea 1", false), TodoItemData("Tarea 2", false), TodoItemData("Tarea 3", false)) }
            val newTaskText = remember { mutableStateOf("") }

            MyApplicationTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    TopAppBar(
                        title = { Text(text = "Tareas") }
                    )
                    TextField(
                        value = newTaskText.value,
                        onValueChange = { newTaskText.value = it },
                        label = { Text(text = "Nueva tarea") },
                        modifier = Modifier.padding(16.dp)
                    )
                    Button(
                        onClick = {
                            val newTask = newTaskText.value.trim()
                            if (newTask.isNotEmpty()) {
                                todoList.add(TodoItemData(newTask, false))
                                newTaskText.value = ""
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Agregar tarea")
                    }
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(todoList) { todo ->
                            TodoItem(todo = todo)
                        }
                    }
                    Button(
                        onClick = {
                            todoList.removeAll { it.checked }
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Eliminar tareas")
                    }
                }
            }
        }
    }
}


@Composable
fun TodoItem(todo: TodoItemData) {
    var checkedState by remember { mutableStateOf(todo.checked) }

    LaunchedEffect(todo.checked) {
        checkedState = todo.checked
    }

    Row(modifier = Modifier.padding(16.dp)) {
        Checkbox(
            checked = checkedState,
            onCheckedChange = { isChecked ->
                checkedState = isChecked
                todo.checked = isChecked
            },
            modifier = Modifier.padding(end = 16.dp)
        )
        Text(text = todo.text)
    }
}

data class TodoItemData(
    val text: String,
    var checked: Boolean
)