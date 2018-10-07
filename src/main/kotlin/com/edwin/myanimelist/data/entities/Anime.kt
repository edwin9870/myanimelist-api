package com.edwin.myanimelist.data.entities

import org.springframework.data.annotation.Id
import java.util.*

data class Anime(@Id val id: String?, val name: String, val synopsis: String, val myAnimelistUrl: String, val episodesNumber: Short,val createdDate: Date = Date())