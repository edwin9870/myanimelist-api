package com.edwin.myanimelist.data.dto

import com.edwin.myanimelist.data.entities.Anime

data class AnimeDto(val name: String, val synopsis: String, val myAnimelistUrl: String)

fun AnimeDto.toAnimeEntity(): Anime {
    return Anime(null, this.name, this.synopsis, this.myAnimelistUrl)

}