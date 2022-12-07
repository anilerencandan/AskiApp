package com.example.finalapp.presentation.searchscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finalapp.presentation.AskiViewModel
import com.example.finalapp.presentation.NavParam
import com.example.finalapp.presentation.ScreenDestination
import com.example.finalapp.presentation.bottomnavigationmenu.BottomNavigationItem
import com.example.finalapp.presentation.bottomnavigationmenu.BottomNavigationMenu
import com.example.finalapp.presentation.mypostsscreen.PostList
import com.example.finalapp.presentation.navigateTo
import com.example.finalapp.ui.theme.Purple500
import com.example.finalapp.ui.theme.Purple700


@Composable
fun SearchScreen(navController: NavController, vm: AskiViewModel) {

    val searchedPostsLoading = vm.searchedPostsProgress.value
    val searchedPosts = vm.searchedPosts.value
    var searchTerm by rememberSaveable {
        mutableStateOf("")
    }

    Column {
        SearchBar(
            searchTerm = searchTerm,
            onSearchChange = { searchTerm = it },
            onSearch = { vm.searchPosts(searchTerm) }
        )
        PostList(
            isContextLoading = false,
            postsLoading = searchedPostsLoading,
            posts = searchedPosts,
            modifier = Modifier
                .background(Purple700)
                .weight(1f)
                .fillMaxWidth()
                .padding(
                    8.dp
                )
        ) { post ->
            navigateTo(
                navController = navController,
                dest = ScreenDestination.SinglePost,
                NavParam("post", post)
            )
        }
        BottomNavigationMenu(
            selectedItem = BottomNavigationItem.SEARCH,
            navController = navController
        )
    }
}

@Composable
fun SearchBar(
    searchTerm: String,
    onSearchChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = searchTerm,
        onValueChange = onSearchChange,
        modifier = Modifier
            .background(Purple700)
            .padding(8.dp)
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, CircleShape),
        shape = CircleShape,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
                focusManager.clearFocus()
            }
        ),
        maxLines = 1,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            textColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            IconButton(onClick = {
                onSearch()
                focusManager.clearFocus()
            }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
        }
    )
}