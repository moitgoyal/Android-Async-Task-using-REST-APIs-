package com.example.mg156.assignment8

import java.io.Serializable

val BASE_URL = "http://192.168.1.108/mohit/"
data class MovieData(
        var error: Boolean,
        var message: String,
        var vote_count: Int?,
        var id: Int?,
        var vote_average: Double?,
        var title: String?,
        var popularity: Double?,
        var poster_path: String?,
        var original_language: String?,
        var original_title: String?,
        var genre_ids: List<Int?>?,
        var backdrop_path: String?,
        var overview: String?,
        var release_date: String?,
        var db_id: Int = -1,
        var geners: String = "",
        var selection: Boolean = false
) : Serializable

/*
val movies ="""[
    {
      "vote_count": 10958,
      "id": 278,
      "video": false,
      "vote_average": 8.6,
      "title": "The Shawshank Redemption",
      "popularity": 24.454,
      "poster_path": "/9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg",
      "original_language": "en",
      "original_title": "The Shawshank Redemption",
      "genre_ids": [
        18,
        80
      ],
      "backdrop_path": "/j9XKiZrVeViAixVRzCta7h1VU9W.jpg",
      "adult": false,
      "overview": "Framed in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
      "release_date": "1994-09-23",
      "selection": false
    },
    {
      "vote_count": 8387,
      "id": 238,
      "video": false,
      "vote_average": 8.6,
      "title": "The Godfather",
      "popularity": 21.315,
      "poster_path": "/rPdtLWNsZmAtoZl9PK7S2wE3qiS.jpg",
      "original_language": "en",
      "original_title": "The Godfather",
      "genre_ids": [
        18,
        80
      ],
      "backdrop_path": "/6xKCYgH16UuwEGAyroLU6p8HLIn.jpg",
      "adult": false,
      "overview": "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.",
      "release_date": "1972-03-14",
      "selection": false
    },
    {
      "vote_count": 6390,
      "id": 424,
      "video": false,
      "vote_average": 8.5,
      "title": "Schindler's List",
      "popularity": 18.147,
      "poster_path": "/yPisjyLweCl1tbgwgtzBCNCBle.jpg",
      "original_language": "en",
      "original_title": "Schindler's List",
      "genre_ids": [
        18,
        36,
        10752
      ],
      "backdrop_path": "/cTNYRUTXkBgPH3wP3kmPUB5U6dA.jpg",
      "adult": false,
      "overview": "The true story of how businessman Oskar Schindler saved over a thousand Jewish lives from the Nazis while they worked as slaves in his factory during World War II.",
      "release_date": "1993-12-15",
      "selection": false
    },
    {
      "vote_count": 4856,
      "id": 240,
      "video": false,
      "vote_average": 8.5,
      "title": "The Godfather: Part II",
      "popularity": 17.484,
      "poster_path": "/bVq65huQ8vHDd1a4Z37QtuyEvpA.jpg",
      "original_language": "en",
      "original_title": "The Godfather: Part II",
      "genre_ids": [
        18,
        80
      ],
      "backdrop_path": "/gLbBRyS7MBrmVUNce91Hmx9vzqI.jpg",
      "adult": false,
      "overview": "In the continuing saga of the Corleone crime family, a young Vito Corleone grows up in Sicily and in 1910s New York. In the 1950s, Michael Corleone attempts to expand the family business into Las Vegas, Hollywood and Cuba.",
      "release_date": "1974-12-20",
      "selection": false
    },
    {
      "vote_count": 6429,
      "id": 497,
      "video": false,
      "vote_average": 8.4,
      "title": "The Green Mile",
      "popularity": 15.827,
      "poster_path": "/sOHqdY1RnSn6kcfAHKu28jvTebE.jpg",
      "original_language": "en",
      "original_title": "The Green Mile",
      "genre_ids": [
        14,
        18,
        80
      ],
      "backdrop_path": "/Rlt20sEbOQKPVjia7lUilFm49W.jpg",
      "adult": false,
          "overview": "A supernatural tale set on death row in a Southern prison, where gentle giant John Coffey possesses the mysterious power to heal people's ailments. When the cell block's head guard, Paul Edgecomb, recognizes Coffey's miraculous gift, he tries desperately to help stave off the condemned man's execution.",
      "release_date": "1999-12-10",
      "selection": false
    },
    {
      "vote_count": 12484,
      "id": 680,
      "video": false,
      "vote_average": 8.4,
      "title": "Pulp Fiction",
      "popularity": 30.503,
      "poster_path": "/dM2w364MScsjFf8pfMbaWUcWrR.jpg",
      "original_language": "en",
      "original_title": "Pulp Fiction",
      "genre_ids": [
        53,
        80
      ],
      "backdrop_path": "/4cDFJr4HnXN5AdPw4AKrmLlMWdO.jpg",
      "adult": false,
      "overview": "A burger-loving hit man, his philosophical partner, a drug-addled gangster's moll and a washed-up boxer converge in this sprawling, comedic crime caper. Their adventures unfurl in three stories that ingeniously trip back and forth in time.",
      "release_date": "1994-09-10",
      "selection": false
    },
    {
      "vote_count": 13602,
      "id": 550,
      "video": false,
      "vote_average": 8.4,
      "title": "Fight Club",
      "popularity": 26.893,
      "poster_path": "/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg",
      "original_language": "en",
      "original_title": "Fight Club",
      "genre_ids": [
        18
      ],
      "backdrop_path": "/87hTDiay2N2qWyX4Ds7ybXi9h8I.jpg",
      "adult": false,
      "overview": "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
      "release_date": "1999-10-15",
      "selection": false
    },
    {
      "vote_count": 3851,
      "id": 539,
      "video": false,
      "vote_average": 8.4,
      "title": "Psycho",
      "popularity": 14.599,
      "poster_path": "/81d8oyEFgj7FlxJqSDXWr8JH8kV.jpg",
      "original_language": "en",
      "original_title": "Psycho",
      "genre_ids": [
        18,
        27,
        53
      ],
      "backdrop_path": "/3md49VBCeqY6MSNyAVY6d5eC6bA.jpg",
      "adult": false,
      "overview": "When larcenous real estate clerk Marion Crane goes on the lam with a wad of cash and hopes of starting a new life, she ends up at the notorious Bates Motel, where manager Norman Bates cares for his housebound mother. The place seems quirky, but fineâ€¦ until Marion decides to take a shower.",
      "release_date": "1960-06-16",
      "selection": false
    },
    {
      "vote_count": 16089,
      "id": 155,
      "video": false,
      "vote_average": 8.4,
      "title": "The Dark Knight",
      "popularity": 25.9,
      "poster_path": "/1hRoyzDtpgMU7Dz4JF22RANzQO7.jpg",
      "original_language": "en",
      "original_title": "The Dark Knight",
      "genre_ids": [
        18,
        28,
        80,
        53
      ],
      "backdrop_path": "/hqkIcbrOHL86UncnHIsHVcVmzue.jpg",
      "adult": false,
      "overview": "Batman raises the stakes in his war on crime. With the help of Lt. Jim Gordon and District Attorney Harvey Dent, Batman sets out to dismantle the remaining criminal organizations that plague the streets. The partnership proves to be effective, but they soon find themselves prey to a reign of chaos unleashed by a rising criminal mastermind known to the terrified citizens of Gotham as the Joker.",
      "release_date": "2008-07-16",
      "selection": false
    },
    {
      "vote_count": 11884,
      "id": 13,
      "video": false,
      "vote_average": 8.4,
      "title": "Forrest Gump",
      "popularity": 22.31,
      "poster_path": "/yE5d3BUhE8hCnkMUJOo1QDoOGNz.jpg",
      "original_language": "en",
      "original_title": "Forrest Gump",
      "genre_ids": [
        35,
        18,
        10749
      ],
      "backdrop_path": "/7c9UVPPiTPltouxRVY6N9uugaVA.jpg",
      "adult": false,
      "overview": "A man with a low IQ has accomplished great things in his life and been present during significant historic events - in each case, far exceeding what anyone imagined he could do. Yet, despite all the things he has attained, his one true love eludes him. 'Forrest Gump' is the story of a man who rose above his challenges, and who proved that determination, courage, and love are more important than ability.",
      "release_date": "1994-07-06",
      "selection": false
    }
  ]""".trimIndent()*/
