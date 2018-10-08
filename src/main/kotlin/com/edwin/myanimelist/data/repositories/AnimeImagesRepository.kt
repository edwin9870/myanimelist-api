package com.edwin.myanimelist.data.repositories

import com.edwin.myanimelist.data.entities.AnimeImages
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AnimeImagesRepository: MongoRepository<AnimeImages, String> {
    fun findByMyAnimeListId(myAnimeListId: Int): AnimeImages?
}