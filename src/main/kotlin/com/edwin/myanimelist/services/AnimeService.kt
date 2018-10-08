package com.edwin.myanimelist.services

import com.edwin.myanimelist.data.dto.AnimeDto
import com.edwin.myanimelist.data.dto.toAnimeEntity
import com.edwin.myanimelist.data.entities.AnimeImages
import com.edwin.myanimelist.data.repositories.AnimeImagesRepository
import com.edwin.myanimelist.data.repositories.AnimeRepository
import com.edwin.myanimelist.exceptions.EntityExistsException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.URL

@Service
class AnimeService @Autowired constructor(var animeRepository: AnimeRepository, var animeImagesRepository: AnimeImagesRepository) {

    fun create(anime: AnimeDto): Boolean {

        if(animeRepository.findByName(anime.name) != null) {
            throw EntityExistsException("Anime with the name ${anime.name} already exist")
        }

        animeRepository.save(anime.toAnimeEntity())
        return true
    }

    fun createAnimeImages(myAnimeListId: Int, imagesUrl: List<URL>): Boolean {
        if(animeImagesRepository.findByMyAnimeListId(myAnimeListId) != null) {
            throw EntityExistsException("Anime with animeListID $myAnimeListId already exist")
        }

        animeImagesRepository.save(AnimeImages(null, myAnimeListId, imagesUrl))
        return true
    }

}