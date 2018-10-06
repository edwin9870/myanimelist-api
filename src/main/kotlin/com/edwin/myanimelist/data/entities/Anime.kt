package com.edwin.myanimelist.data.entities

import org.springframework.data.annotation.Id

data class Anime(@Id val id: String?, val name: String, val synopsis: String)