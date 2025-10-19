package com.example.cpsc411mid_termproject.ui.screens

// Details screen for movie info
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.cpsc411mid_termproject.viewmodel.MovieViewModel

// TODO: add share functionality
// also maybe add reviews?

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    movieId: Int,
    onBackClick: () -> Unit,
    viewModel: MovieViewModel = viewModel()
) {
    val movie = viewModel.getMovieById(movieId)
    val isInWatchlist by viewModel.watchlist.collectAsState()
    val isMovieInWatchlist = isInWatchlist.contains(movieId)

    if (movie == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Movie not found") // TODO: better error handling
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movie Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (isMovieInWatchlist) {
                                viewModel.removeFromWatchlist(movieId)
                            } else {
                                viewModel.addToWatchlist(movieId)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isMovieInWatchlist) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (isMovieInWatchlist) "Remove from watchlist" else "Add to watchlist",
                            tint = if (isMovieInWatchlist) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Movie poster image
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp) // changed from 400
                    .padding(14.dp) // changed from 16
                    .clip(RoundedCornerShape(10.dp)), // changed from 12
                contentScale = ContentScale.Crop,
                error = null, // TODO: add error image
                placeholder = null
            )

            // Movie info section
            Column(
                modifier = Modifier.padding(18.dp) // changed from 16
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold // looks better bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "★ ${movie.rating}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Text(
                        text = "${movie.year}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Genre",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = movie.genre,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = movie.description,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Watchlist Button
                Button(
                    onClick = {
                        if (isMovieInWatchlist) {
                            viewModel.removeFromWatchlist(movieId)
                        } else {
                            viewModel.addToWatchlist(movieId)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = if (isMovieInWatchlist) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        if (isMovieInWatchlist) "Remove from Watchlist" else "Add to Watchlist"
                    )
                }
            }
        }
    }
}
