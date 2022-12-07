package com.example.finalapp.presentation.newpostscreen

import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.finalapp.presentation.CommonDivider
import com.example.finalapp.presentation.CommonProgressSpinner
import com.example.finalapp.presentation.AskiViewModel
import com.example.finalapp.ui.theme.Purple500
import com.example.finalapp.ui.theme.Purple700


@Composable
fun NewPostScreen(navController: NavController, vm: AskiViewModel, encodedUri: String) {

    val imageUri by remember { mutableStateOf(encodedUri) }
    var description by rememberSaveable() { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .background(Purple700)
            .verticalScroll(scrollState)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .background(Purple500)
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Cancel", modifier = Modifier.clickable { navController.popBackStack() })
            Text(text = "Post", modifier = Modifier.clickable {
                focusManager.clearFocus()
                vm.onNewPost(Uri.parse(imageUri), description) {
                    navController.popBackStack()
                }
            })
        }

        CommonDivider()

        Image(
            painter = rememberImagePainter(imageUri),
            contentDescription = "post image",
            modifier = Modifier
                .background(Purple500)
                .fillMaxWidth()
                .defaultMinSize(minHeight = 150.dp),
            contentScale = ContentScale.FillWidth
        )

        Row(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .background(Purple500)
                    .fillMaxWidth()
                    .height(150.dp),
                label = { Text(text = "Description") },
                singleLine = false,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                )
            )
        }
    }
    val inProgress = vm.inProgress.value
    if (inProgress) {
        CommonProgressSpinner()
    }
}