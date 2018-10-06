package com.edwin.myanimelist.data.dto

import com.edwin.myanimelist.data.entities.AnimeEntity

data class AnimeDto(val name: String, val synopsis: String)

fun AnimeDto.toAnimeEntity(): AnimeEntity {
    return AnimeEntity(null, this.name, this.synopsis)

}