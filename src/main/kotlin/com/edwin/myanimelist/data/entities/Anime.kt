package com.edwin.myanimelist.data.entities

import org.springframework.data.annotation.Id
import java.time.LocalDate

data class Anime(@Id val id: String?,
                 val myAnimeListId: Int,
                 val name: String,
                 val synopsis: String,
                 val myAnimelistUrl: String,
                 val episodesNumber: Short,
                 val isOnAiring: Boolean?,
                 val releaseDate: LocalDate?,
                 val endDate: LocalDate?,
                 val createdDate: LocalDate = LocalDate.now())