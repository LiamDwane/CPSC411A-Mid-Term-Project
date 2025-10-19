package com.example.cpsc411mid_termproject.data

// Movie data class with basic info
data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val rating: Double, // TODO: maybe use float instead? or BigDecimal?
    val posterUrl: String,
    val genre: String,
    val year: Int
)

// Hardcoded movie data repository
object MovieRepository {
    // TODO: move to database later
    val movies = listOf(
        Movie(
            id = 1,
            title = "The Dark Knight",
            description = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
            rating = 9.0,
            posterUrl = "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_SX300.jpg",
            genre = "Action, Crime, Drama",
            year = 2008
        ),
        Movie(
            id = 2,
            title = "Inception",
            description = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
            rating = 8.8,
            posterUrl = "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SX300.jpg",
            genre = "Action, Sci-Fi, Thriller",
            year = 2010
        ),
        Movie(
            id = 3,
            title = "Pulp Fiction",
            description = "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
            rating = 8.9,
            posterUrl = "https://m.media-amazon.com/images/M/MV5BNGNhMDIzZTUtNTBlZi00MTRlLWFjM2ItYzViMjE3YzI5MjljXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg",
            genre = "Crime, Drama",
            year = 1994
        ),
        Movie(
            id = 4,
            title = "The Shawshank Redemption",
            description = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
            rating = 9.3,
            posterUrl = "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg",
            genre = "Drama",
            year = 1994
        ),
        Movie(
            id = 5,
            title = "The Godfather",
            description = "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
            rating = 9.2,
            posterUrl = "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg",
            genre = "Crime, Drama",
            year = 1972
        ),
        Movie(
            id = 6,
            title = "Forrest Gump",
            description = "The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75.",
            rating = 8.8,
            posterUrl = "https://m.media-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg",
            genre = "Drama, Romance",
            year = 1994
        ),
        Movie(
            id = 7,
            title = "The Matrix",
            description = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
            rating = 8.7,
            posterUrl = "https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg",
            genre = "Action, Sci-Fi",
            year = 1999
        ),
        Movie(
            id = 8,
            title = "Spider-Man: Into the Spider-Verse",
            description = "Teen Miles Morales becomes Spider-Man of his reality, crossing his path with five counterparts from other dimensions to stop a threat for all realities.",
            rating = 8.4,
            posterUrl = "https://m.media-amazon.com/images/M/MV5BMjMwNDkxMTgzOF5BMl5BanBnXkFtZTgwNTkwNTQ3NjM@._V1_SX300.jpg",
            genre = "Animation, Action, Adventure",
            year = 2018
        )
    )
}
