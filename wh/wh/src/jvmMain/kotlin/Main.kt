// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import topMenu.topMenu

@Composable
fun App() {

//    var onceForStart by remember { mutableStateOf(true) }
//
//    LaunchedEffect(onceForStart) {
//        kotlin.runCatching {
//            if (onceForStart) {
//                onceForStart = false
//                DB.create(DB_NAME, closed = true)
//                DB.createTable(selectedDB, selectedDBTable)
//            }
//        }
//    }

    MaterialTheme {

        Scaffold(topBar = { topMenu() }, content = { tables() }, bottomBar = {})

    }
}

fun main() = application {

    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
