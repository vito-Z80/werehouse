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




    MaterialTheme {

        Scaffold(topBar = { topMenu() }, content = { tables() }, bottomBar = {})

    }
}

fun main() = application {
    DB.create(DB_NAME)
    DB.createTable(selectedDBTable)
//    DB.createTable(LIST_TABLE)
    DB.selectFrom(selectedDBTable)
    DB.close()
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
