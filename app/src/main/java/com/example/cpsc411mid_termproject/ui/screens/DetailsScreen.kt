package com.example.cpsc411mid_termproject.ui.screens

// Details screen for movie info
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.cpsc411mid_termproject.ui.theme.*
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
                            "Movie Details",
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
                                tint = if (isMovieInWatchlist) ClayPink else ClayTextSecondary
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Movie poster with gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp) // increased height for better visual
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = movie.posterUrl,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(28.dp)) // very rounded corners
                        .shadow(
                            elevation = 12.dp, // deeper shadow
                            shape = RoundedCornerShape(28.dp),
                            ambientColor = ClayPurple.copy(alpha = 0.15f),
                            spotColor = ClayBlue.copy(alpha = 0.2f)
                        ),
                    contentScale = ContentScale.Crop,
                    error = null, // TODO: add error image
                    placeholder = null
                )
                
                // Gradient overlay for better text readability
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(28.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    ClayBackground.copy(alpha = 0.3f)
                                )
                            )
                        )
                )
            }

            // Movie info section with claymorphism card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(24.dp),
                        ambientColor = ClayPurple.copy(alpha = 0.1f),
                        spotColor = ClayBlue.copy(alpha = 0.15f)
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = ClaySurface
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = ClayTextPrimary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Rating with star icon
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = ClayYellow,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${movie.rating}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = ClayPurple
                        )
                        
                        Spacer(modifier = Modifier.width(20.dp))
                        
                        // Year badge
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = ClayBlue.copy(alpha = 0.3f)
                            )
                        ) {
                            Text(
                                text = "${movie.year}",
                                style = MaterialTheme.typography.titleMedium,
                                color = ClayTextPrimary,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            // Genre section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(20.dp),
                        ambientColor = ClayGreen.copy(alpha = 0.1f)
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = ClaySurface
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Genre",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = ClayTextPrimary
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Genre chips
                    Row {
                        movie.genre.split(", ").forEach { genre ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = ClayGreen.copy(alpha = 0.2f)
                                ),
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text(
                                    text = genre.trim(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = ClayTextPrimary,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Description section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(20.dp),
                        ambientColor = ClayPink.copy(alpha = 0.1f)
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = ClaySurface
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = ClayTextPrimary
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = movie.description,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 24.sp,
                        color = ClayTextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Watchlist Button with claymorphism
            Button(
                onClick = {
                    if (isMovieInWatchlist) {
                        viewModel.removeFromWatchlist(movieId)
                    } else {
                        viewModel.addToWatchlist(movieId)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(24.dp),
                        ambientColor = ClayPink.copy(alpha = 0.2f),
                        spotColor = ClayPurple.copy(alpha = 0.2f)
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isMovieInWatchlist) ClayPink else ClayPurple
                )
            ) {
                Icon(
                    imageVector = if (isMovieInWatchlist) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = ClayTextPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    if (isMovieInWatchlist) "Remove from Watchlist" else "Add to Watchlist",
                    color = ClayTextPrimary,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
    }
}
