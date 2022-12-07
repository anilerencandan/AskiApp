package com.example.finalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalapp.data.remote.dto.PostDto
import com.example.finalapp.presentation.AskiViewModel
import com.example.finalapp.presentation.NotificationMessage
import com.example.finalapp.presentation.ScreenDestination
import com.example.finalapp.presentation.commentsscreen.CommentsScreen
import com.example.finalapp.presentation.feedscreen.FeedScreen
import com.example.finalapp.presentation.loginscreen.LoginScreen
import com.example.finalapp.presentation.mypostsscreen.MyPostsScreen
import com.example.finalapp.presentation.newpostscreen.NewPostScreen
import com.example.finalapp.presentation.profilescreen.ProfileScreen
import com.example.finalapp.presentation.searchscreen.SearchScreen
import com.example.finalapp.presentation.signupscreen.SignupScreen
import com.example.finalapp.presentation.singlepostscreen.SinglePostScreen
import com.example.finalapp.ui.theme.AskiAppTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AskiAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AskiApp()
                }
            }
        }
    }
}

@Composable
fun AskiApp() {
    val vm = hiltViewModel<AskiViewModel>()
    val navController = rememberNavController()
    NotificationMessage(vm = vm)
    NavHost(navController = navController, startDestination = ScreenDestination.Signup.route) {
        composable(ScreenDestination.Signup.route) {
            SignupScreen(navController = navController, vm = vm)
        }
        composable(ScreenDestination.Login.route) {
            LoginScreen(navController = navController, vm = vm)
        }
        composable(ScreenDestination.Feed.route) {
            FeedScreen(navController = navController, vm = vm)
        }
        composable(ScreenDestination.Search.route) {
            SearchScreen(navController = navController, vm = vm)
        }
        composable(ScreenDestination.MyPosts.route) {
            MyPostsScreen(navController = navController, vm = vm)
        }
        composable(ScreenDestination.Profile.route) {
            ProfileScreen(navController = navController, vm = vm)
        }
        composable(ScreenDestination.NewPost.route) { navBackstackEntry ->
            val imageUri = navBackstackEntry.arguments?.getString("imageUri")
            imageUri?.let {
                NewPostScreen(navController = navController, vm = vm, encodedUri = it)
            }
        }
        composable(ScreenDestination.SinglePost.route) {
            val postData =
                navController.previousBackStackEntry?.arguments?.getParcelable<PostDto>("post")

            postData?.let {
                SinglePostScreen(navController = navController, vm = vm, post = postData)
            }
        }

        composable(ScreenDestination.Comments.route) { navBackStackEntry ->
            val postId = navBackStackEntry.arguments?.getString("postId")
            postId?.let {
                CommentsScreen(navController = navController, vm = vm, postId = it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AskiAppTheme {
        AskiApp()
    }
    var db = Firebase.firestore

//to reconnect
    db.terminate()
    db = Firebase.firestore
}