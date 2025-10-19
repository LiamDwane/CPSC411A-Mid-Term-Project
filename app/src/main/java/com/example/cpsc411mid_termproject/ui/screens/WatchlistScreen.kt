package com.example.cpsc411mid_termproject.ui.screens

// Watchlist screen for saved movies
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.cpsc411mid_termproject.data.Movie
import com.example.cpsc411mid_termproject.viewmodel.MovieViewModel

// TODO: add sorting options
// maybe by rating or year?

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistScreen(
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    viewModel: MovieViewModel = viewModel()
) {
    val watchlistMovies by viewModel.watchlist.collectAsState()
    val movies = viewModel.getWatchlistMovies()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Watchlist") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Empty state when no movies
        if (movies.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Your watchlist is empty",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant // gray looks good here
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Add movies to your watchlist from the home screen",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(17.dp), // changed from 16
                verticalArrangement = Arrangement.spacedBy(11.dp) // changed from 12
            ) {
                items(movies) { movieData ->
                    WatchlistMovieCard(
                        movieItem = movieData,
                        onCardClick = { onMovieClick(movieData.id) },
                        onRemoveFromList = { viewModel.removeFromWatchlist(movieData.id) }
                    )
                }
            }
        }
    }
}

// Watchlist movie card componet
@Composable
fun WatchlistMovieCard(
    movieItem: Movie, // changed from movie
    onCardClick: () -> Unit, // changed from onClick
    onRemoveFromList: () -> Unit // changed from onRemoveClick
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(19.dp), // changed from 16
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = movieItem.posterUrl,
                contentDescription = movieItem.title,
                modifier = Modifier
                    .size(78.dp) // changed from 80
                    .clip(RoundedCornerShape(7.dp)) // changed from 8
                    .clickable { onCardClick() },
                contentScale = ContentScale.Crop,
                error = null, // TODO: add error image
                placeholder = null
            )
            
            Spacer(modifier = Modifier.width(15.dp)) // changed from 16
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movieItem.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.clickable { onCardClick() }
                )
                
                Spacer(modifier = Modifier.height(5.dp)) // changed from 4
                
                Text(
                    text = "${movieItem.year} • ${movieItem.genre}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(3.dp)) // changed from 4
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "★ ${movieItem.rating}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            IconButton(onClick = onRemoveFromList) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Remove from watchlist",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
