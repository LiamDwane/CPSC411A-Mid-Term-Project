package com.example.cpsc411mid_termproject.navigation

// Navigation setup for screens
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cpsc411mid_termproject.ui.screens.DetailsScreen
import com.example.cpsc411mid_termproject.ui.screens.HomeScreen
import com.example.cpsc411mid_termproject.ui.screens.WatchlistScreen
import com.example.cpsc411mid_termproject.viewmodel.MovieViewModel

// TODO: add deep linking support
// maybe add animations too?

@Composable
fun MovieNavigation(
    navController: NavHostController = rememberNavController()
) {
    // TODO: maybe use hilt for DI later?
    val sharedViewModel: MovieViewModel = viewModel()
    
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onMovieClick = { movieId ->
                    navController.navigate("details/$movieId")
                },
                onWatchlistClick = {
                    navController.navigate("watchlist")
                },
                viewModel = sharedViewModel // pass shared viewmodel
            )
        }
        
        composable("details/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull() ?: 0
            // TODO: better error handling for invalid IDs
            DetailsScreen(
                movieId = movieId,
                onBackClick = {
                    navController.popBackStack()
                },
                viewModel = sharedViewModel // pass shared viewmodel
            )
        }
        
        composable("watchlist") {
            WatchlistScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onMovieClick = { movieId ->
                    navController.navigate("details/$movieId")
                },
                viewModel = sharedViewModel // pass shared viewmodel
            )
        }
    }
}
