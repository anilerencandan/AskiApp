package com.example.finalapp.presentation.signupscreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalapp.R
import com.example.finalapp.presentation.*
import com.example.finalapp.ui.theme.Purple700


@Composable
fun SignupScreen(
    navController: NavController,
    vm: AskiViewModel
) {
    CheckSignedIn(navController = navController, vm = vm)

    val focus = LocalFocusManager.current
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val usernameState = remember {
                mutableStateOf(TextFieldValue())
            }
            val emailState = remember {
                mutableStateOf(TextFieldValue())
            }
            val passState = remember {
                mutableStateOf(TextFieldValue())
            }

            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "app Logo",
                modifier = Modifier
                    .width(250.dp)
                    .padding(top = 16.dp)
                    .padding(8.dp)
            )
            Text(
                text = "Signup",
                modifier = Modifier.padding(8.dp),
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif

            )
            OutlinedTextField(
                value = usernameState.value,
                onValueChange = { usernameState.value = it },
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "Username") }
            )
            OutlinedTextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "Email") }
            )
            OutlinedTextField(
                value = passState.value,
                onValueChange = { passState.value = it },
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            Button(onClick = {
                focus.clearFocus(force = true)
                vm.onSignup(
                    usernameState.value.text,
                    emailState.value.text,
                    passState.value.text
                )
            }, modifier = Modifier.padding(8.dp)) {
                Text(text = "Signup")
            }
            Text(text = "Already a user? Go to login ->",
                color = Color.Blue,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        navigateTo(navController, ScreenDestination.Login)
                    }
            )
        }
        val isLoading = vm.inProgress.value
        if (isLoading) {
            CommonProgressSpinner()
        }
    }
}
