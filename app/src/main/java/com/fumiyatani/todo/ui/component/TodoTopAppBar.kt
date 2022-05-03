package com.fumiyatani.todo.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.fumiyatani.todo.ui.tasks.DisplayMode

@Composable
fun TodoTopAppBar(
    title: String = "タスク管理",
    onClickMenuIcon: (DisplayMode) -> Unit,
    checkedDisplayMode: DisplayMode,
) {
    val showPopup = remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            // DropdownMenu を IconButton の下に表示するために Box でくるんでいる。
            Box {
                IconButton(
                    onClick = {
                        showPopup.value = true
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                    )
                }
                DropdownMenu(
                    expanded = showPopup.value,
                    onDismissRequest = { showPopup.value = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            onClickMenuIcon(DisplayMode.ALL)
                            showPopup.value = false
                        }
                    ) {
                        Text(
                            modifier = Modifier,
                            text = "全て"
                        )
                        if (checkedDisplayMode == DisplayMode.ALL) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null)
                        }
                    }
                    DropdownMenuItem(
                        onClick = {
                            onClickMenuIcon(DisplayMode.Unfinished)
                            showPopup.value = false
                        }
                    ) {
                        Text(
                            modifier = Modifier,
                            text = "未完了"
                        )
                        if (checkedDisplayMode == DisplayMode.Unfinished) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null)
                        }
                    }
                    DropdownMenuItem(
                        onClick = {
                            onClickMenuIcon(DisplayMode.Finish)
                            showPopup.value = false
                        }
                    ) {
                        Text(
                            modifier = Modifier,
                            text = "完了"
                        )
                        if (checkedDisplayMode == DisplayMode.Finish) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null)
                        }
                    }
                }
            }
        }
    )
}
