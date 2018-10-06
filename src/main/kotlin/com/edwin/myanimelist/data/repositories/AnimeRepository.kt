package com.edwin.myanimelist.data.repositories

import com.edwin.myanimelist.data.entities.AnimeEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AnimeRepository: MongoRepository<AnimeEntity, String> {

    fun findByName(name: String): AnimeEntity?

}