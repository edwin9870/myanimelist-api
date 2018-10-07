package com.edwin.myanimelist.data.repositories

import com.edwin.myanimelist.data.entities.Anime
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AnimeRepository: MongoRepository<Anime, String> {

    fun findByName(name: String): Anime?

}