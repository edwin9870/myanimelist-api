package com.edwin.myanimelist.data.dto

import com.edwin.myanimelist.data.entities.Anime
import java.time.LocalDate
import java.util.*

data class AnimeDto(val myAnimeListId: Int,
                    val name: String,
                    val synopsis: String,
                    val myAnimelistUrl: String,
                    val episodesNumber: Short,
                    val isOnAiring: Boolean?,
                    val releaseDate: LocalDate?,
                    val endDate: LocalDate?)

fun AnimeDto.toAnimeEntity(): Anime {
    return Anime(null,
            this.myAnimeListId,
            this.name,
            this.synopsis,
            this.myAnimelistUrl,
            this.episodesNumber,
            this.isOnAiring,
            this.releaseDate,
            this.endDate)

}