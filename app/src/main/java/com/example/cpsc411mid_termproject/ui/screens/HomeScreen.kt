package com.example.cpsc411mid_termproject.ui.screens

// Home screen with movie list
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.cpsc411mid_termproject.data.Movie
import com.example.cpsc411mid_termproject.ui.theme.*
import com.example.cpsc411mid_termproject.viewmodel.MovieViewModel

// TODO: add search functionality
// maybe add filters too?

// Movie card componet for list
@Composable
fun MovieCard(
    movieData: Movie, // changed from movie
    onCardClick: () -> Unit // changed from onClick
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
            .shadow(
                elevation = 12.dp, // enhanced shadow
                shape = RoundedCornerShape(28.dp),
                ambientColor = ClayPurple.copy(alpha = 0.12f),
                spotColor = ClayBlue.copy(alpha = 0.18f)
            ),
        shape = RoundedCornerShape(28.dp), // very rounded corners
        colors = CardDefaults.cardColors(
            containerColor = ClaySurface
        )
    ) {
        Row(
            modifier = Modifier.padding(20.dp), // increased padding
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Movie poster with enhanced shadow
            Box(
                modifier = Modifier
                    .size(90.dp) // slightly larger
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(20.dp),
                        ambientColor = ClayBlue.copy(alpha = 0.25f),
                        spotColor = ClayPurple.copy(alpha = 0.2f)
                    )
            ) {
                AsyncImage(
                    model = movieData.posterUrl,
                    contentDescription = movieData.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp)), // more rounded
                    contentScale = ContentScale.Crop,
                    error = null, // TODO: add error placeholder
                    placeholder = null
                )
            }
            
            Spacer(modifier = Modifier.width(18.dp)) // increased spacing
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movieData.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = ClayTextPrimary,
                    fontSize = 16.sp // slightly larger
                )
                
                Spacer(modifier = Modifier.height(8.dp)) // increased spacing
                
                Text(
                    text = "${movieData.year} • ${movieData.genre}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = ClayTextSecondary,
                    fontSize = 13.sp
                )
                
                Spacer(modifier = Modifier.height(6.dp)) // increased spacing
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = ClayYellow,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${movieData.rating}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = ClayPurple,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (Int) -> Unit,
    onWatchlistClick: () -> Unit,
    viewModel: MovieViewModel = viewModel()
) {
    val movies by viewModel.movies.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        ClayBackground,
                        ClayBackground.copy(alpha = 0.8f),
                        ClayBackground
                    )
                )
            )
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { 
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "🎬", // movie emoji for fun
                                fontSize = 24.sp,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                "Movie Explorer",
                                color = ClayTextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                    },
                    actions = {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = ClayPurple.copy(alpha = 0.9f)
                            ),
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .shadow(
                                    elevation = 6.dp,
                                    shape = RoundedCornerShape(20.dp),
                                    ambientColor = ClayPurple.copy(alpha = 0.3f)
                                )
                        ) {
                            TextButton(
                                onClick = onWatchlistClick,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = ClayTextPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "Watchlist", 
                                    color = ClayTextPrimary,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = ClaySurface.copy(alpha = 0.95f)
                    ),
                    modifier = Modifier.shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(0.dp),
                        ambientColor = ClayPurple.copy(alpha = 0.1f)
                    )
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp) // better spacing
        ) {
            // Welcome section
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(28.dp),
                            ambientColor = ClayBlue.copy(alpha = 0.15f),
                            spotColor = ClayPurple.copy(alpha = 0.2f)
                        ),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = ClaySurface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🎭 Discover Amazing Movies",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = ClayTextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Explore our curated collection of cinematic masterpieces",
                            fontSize = 14.sp,
                            color = ClayTextSecondary,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
            
            // Movies list
            items(movies) { movieItem ->
                MovieCard(
                    movieData = movieItem,
                    onCardClick = { onMovieClick(movieItem.id) }
                )
            }
        }
    }
    }
}

