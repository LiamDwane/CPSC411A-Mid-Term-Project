package com.example.cpsc411mid_termproject.ui.screens

// Home screen with movie list
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.cpsc411mid_termproject.data.Movie
import com.example.cpsc411mid_termproject.viewmodel.MovieViewModel

// TODO: add search functionality
// maybe add filters too?

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (Int) -> Unit,
    onWatchlistClick: () -> Unit,
    viewModel: MovieViewModel = viewModel()
) {
    val movies by viewModel.movies.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movie Explorer") },
                actions = {
                    TextButton(onClick = onWatchlistClick) {
                        Text("Watchlist")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(15.dp), // changed from 16
            verticalArrangement = Arrangement.spacedBy(10.dp) // spacing looks good
        ) {
            items(movies) { movieItem ->
                MovieCard(
                    movieData = movieItem,
                    onCardClick = { onMovieClick(movieItem.id) }
                )
            }
        }
    }
}

// Movie card componet for list
@Composable
fun MovieCard(
    movieData: Movie, // changed from movie
    onCardClick: () -> Unit // changed from onClick
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(18.dp), // changed from 16
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = movieData.posterUrl,
                contentDescription = movieData.title,
                modifier = Modifier
                    .size(75.dp) // changed from 80
                    .clip(RoundedCornerShape(6.dp)), // changed from 8
                contentScale = ContentScale.Crop,
                error = null, // TODO: add error placeholder
                placeholder = null
            )
            
            Spacer(modifier = Modifier.width(14.dp)) // changed from 16
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movieData.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(6.dp)) // changed from 4
                
                Text(
                    text = "${movieData.year} • ${movieData.genre}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(3.dp)) // changed from 4
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "★ ${movieData.rating}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
