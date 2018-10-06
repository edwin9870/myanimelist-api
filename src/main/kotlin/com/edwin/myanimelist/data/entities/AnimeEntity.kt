package com.edwin.myanimelist.data.entities

import org.springframework.data.annotation.Id

data class AnimeEntity(@Id val id: String?, val name: String, val synopsis: String)