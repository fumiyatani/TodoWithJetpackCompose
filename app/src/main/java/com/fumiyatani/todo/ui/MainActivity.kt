package com.fumiyatani.todo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.fumiyatani.todo.model.Task
import com.fumiyatani.todo.ui.tasks.TaskListScreen
import com.fumiyatani.todo.ui.tasks.TodoRow
import com.fumiyatani.todo.ui.theme.TodoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    TaskListScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TodoTheme {
        Column {
            TodoRow(
                task = Task(title = "タイトル", detail = "TODOの詳細", isCompleted = false),
                onCheckedChange = { _, _ -> },
            )
            TodoRow(
                task = Task(title = "タイトル", detail = "TODOの詳細", isCompleted = true),
                onCheckedChange = { _, _ -> },
            )
        }
    }
}