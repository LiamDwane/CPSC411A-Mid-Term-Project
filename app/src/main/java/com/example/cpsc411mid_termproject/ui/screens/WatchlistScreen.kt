package com.example.cpsc411mid_termproject.ui.screens

// Watchlist screen for saved movies
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.cpsc411mid_termproject.data.Movie
import com.example.cpsc411mid_termproject.ui.theme.*
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ClayBackground)
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { 
                        Text(
                            "My Watchlist",
                            color = ClayTextPrimary,
                            fontWeight = FontWeight.Bold
                        ) 
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onBackClick
                        ) {
                            Icon(
                                Icons.Default.ArrowBack, 
                                contentDescription = "Back",
                                tint = ClayTextPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = ClaySurface
                    )
                )
            },
            containerColor = ClayBackground
        ) { paddingValues ->
        // Empty state with claymorphism design
        if (movies.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(32.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(32.dp),
                            ambientColor = ClayPink.copy(alpha = 0.15f),
                            spotColor = ClayPurple.copy(alpha = 0.2f)
                        ),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = ClaySurface
                    )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(40.dp)
                    ) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .shadow(
                                    elevation = 6.dp,
                                    shape = RoundedCornerShape(40.dp),
                                    ambientColor = ClayPink.copy(alpha = 0.3f)
                                ),
                            tint = ClayPink
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Your watchlist is empty",
                            style = MaterialTheme.typography.headlineMedium,
                            color = ClayTextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Add movies to your watchlist from the home screen",
                            style = MaterialTheme.typography.bodyLarge,
                            color = ClayTextSecondary,
                            textAlign = TextAlign.Center,
                            lineHeight = 24.sp
                        )
                    }
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
}

// Watchlist movie card componet
@Composable
fun WatchlistMovieCard(
    movieItem: Movie, // changed from movie
    onCardClick: () -> Unit, // changed from onClick
    onRemoveFromList: () -> Unit // changed from onRemoveClick
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp, // enhanced shadow
                shape = RoundedCornerShape(26.dp),
                ambientColor = ClayPurple.copy(alpha = 0.12f),
                spotColor = ClayBlue.copy(alpha = 0.18f)
            ),
        shape = RoundedCornerShape(26.dp), // very rounded
        colors = CardDefaults.cardColors(
            containerColor = ClaySurface
        )
    ) {
        Row(
            modifier = Modifier.padding(19.dp), // changed from 16
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = movieItem.posterUrl,
                contentDescription = movieItem.title,
                modifier = Modifier
                    .size(85.dp) // slightly larger
                    .clip(RoundedCornerShape(18.dp)) // more rounded
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(18.dp),
                        ambientColor = ClayBlue.copy(alpha = 0.25f)
                    )
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
                    color = ClayTextPrimary,
                    modifier = Modifier.clickable { onCardClick() }
                )
                
                Spacer(modifier = Modifier.height(6.dp))
                
                Text(
                    text = "${movieItem.year} • ${movieItem.genre}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = ClayTextSecondary
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = ClayYellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${movieItem.rating}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = ClayPurple
                    )
                }
            }
            
            IconButton(
                onClick = onRemoveFromList
            ) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Remove from watchlist",
                    tint = ClayPink
                )
            }
        }
    }
}
