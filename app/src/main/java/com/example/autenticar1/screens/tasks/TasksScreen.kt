package com.example.autenticar1.screens.tasks

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.autenticar1.common.composable.ActionToolbar
import com.example.autenticar1.common.ext.smallSpacer
import com.example.autenticar1.common.ext.toolbarActions
import com.example.autenticar1.model.Task
import com.example.autenticar1.ui.theme.Autenticar1Theme
import com.example.autenticar1.R.drawable as AppIcon
import com.example.autenticar1.R.string as AppText

@Composable
fun TasksScreen(
    openScreen: (String) -> Unit,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val tasks = viewModel.tasks.collectAsStateWithLifecycle(emptyList())
    val options by viewModel.options

    TasksScreenContent(
        tasks = tasks.value,
        options = options,
        onAddClick = viewModel::onAddClick,
        onStatsClick = viewModel::onStatsClick,
        onSettingsClick = viewModel::onSettingsClick,
        onTaskCheckChange = viewModel::onTaskCheckChange,
        onTaskActionClick = viewModel::onTaskActionClick,
        openScreen = openScreen
    )

    LaunchedEffect(viewModel) { viewModel.loadTaskOptions() }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TasksScreenContent(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    options: List<String>,
    onAddClick: ((String) -> Unit) -> Unit,
    onStatsClick: ((String) -> Unit) -> Unit,
    onSettingsClick: ((String) -> Unit) -> Unit,
    onTaskCheckChange: (Task) -> Unit,
    onTaskActionClick: ((String) -> Unit, Task, String) -> Unit,
    openScreen: (String) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddClick(openScreen) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, "Add")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            ActionToolbar(
                title = AppText.tasks,
                modifier = Modifier.toolbarActions(),
                primaryActionIcon = AppIcon.ic_stats,
                primaryAction = { onStatsClick(openScreen) },
                secondaryActionIcon = AppIcon.ic_settings,
                secondaryAction = { onSettingsClick(openScreen) }
            )

            Spacer(modifier = Modifier.smallSpacer())

            LazyColumn(
                contentPadding = innerPadding
            ) {
                items(tasks, key = { it.id }) { taskItem ->
                    TaskItem(
                        task = taskItem,
                        options = options,
                        onCheckChange = { onTaskCheckChange(taskItem) },
                        onActionClick = { action -> onTaskActionClick(openScreen, taskItem, action) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TasksScreenPreview() {
    val task = Task(
        title = "Task title",
        flag = true,
        completed = true
    )

    val options = TaskActionOption.getOptions(hasEditOption = true)

    Autenticar1Theme {
        TasksScreenContent(
            tasks = listOf(task),
            options = options,
            onAddClick = { },
            onStatsClick = { },
            onSettingsClick = { },
            onTaskCheckChange = { },
            onTaskActionClick = { _, _, _ -> },
            openScreen = { }
        )
    }
}