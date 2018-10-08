package com.edwin.myanimelist.data.entities

import org.springframework.data.annotation.Id
import java.net.URL
import java.time.LocalDate

data class AnimeImages(@Id val id: String?,
                       val myAnimeListId: Int,
                       val animeImages: List<URL>,
                       val createdDate: LocalDate = LocalDate.now())