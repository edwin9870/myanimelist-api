package com.edwin.myanimelist.services

import com.edwin.myanimelist.data.dto.AnimeDto
import com.edwin.myanimelist.data.entities.Anime
import com.edwin.myanimelist.data.entities.AnimeImages
import com.edwin.myanimelist.data.repositories.AnimeImagesRepository
import com.edwin.myanimelist.data.repositories.AnimeRepository
import com.edwin.myanimelist.exceptions.EntityExistsException
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.net.URL
import java.time.LocalDate
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("test")
class AnimeServiceTest {


    @Autowired
    private lateinit var animeService: AnimeService
    private val animeRepository: AnimeRepository = Mockito.mock(AnimeRepository::class.java)
    private val animeImagesRepository: AnimeImagesRepository = Mockito.mock(AnimeImagesRepository::class.java)

    private var initialized: Boolean = false

    @Before
    fun setUp() {
        if (!initialized) {
            animeService.animeRepository = animeRepository
            animeService.animeImagesRepository = animeImagesRepository

            Mockito.`when`(animeRepository.findByName("Goku")).thenReturn(null)
            val mockAnimeResponse = Anime(
                    null,
                    1,
                    "Boruto",
                    "The Naruto's son",
                    "https://myanimelist.net/anime/34566/Boruto__Naruto_Next_Generations",
                    1,
                    true,
                    LocalDate.now(),
                    LocalDate.now())
            Mockito.`when`(animeRepository.findByName("Boruto")).thenReturn(mockAnimeResponse)
            val mockAnimeImage = AnimeImages("1", 1, Collections.emptyList())
            Mockito.`when`(animeImagesRepository.findByMyAnimeListId(1))
                    .thenReturn(mockAnimeImage)

            initialized = true
        }
    }

    @Test
    fun create_NoExistingAnime_GetTrue() {
        val anime = AnimeDto(1,"Goku", "The best",
                "https://myanimelist.net/anime/1212/Goku_test",
                1,
                false,
                LocalDate.now(),
                LocalDate.now())

        assertTrue("Method must return true when saving a non existing instance",
                animeService.create(anime))
    }

    @Test(expected = EntityExistsException::class)
    fun createExistingAnime_GetException() {
        animeService.create(AnimeDto(
                1,
                "Boruto",
                "The Naruto's son",
                "https://myanimelist.net/anime/34566/Boruto__Naruto_Next_Generations",
                1,
                true,
                LocalDate.now(),
                LocalDate.now()))
    }

    @Test
    fun createAnimeImages_CreateNewAnimeImages_GetTrue() {
        val createAnimeImagesResult = animeService.createAnimeImages(2,
                listOf(
                        URL("https://myanimelist.cdn-dena.com/images/anime/11/82056.jpg"),
                        URL("https://myanimelist.cdn-dena.com/images/anime/13/83077.jpg")))

        assertTrue("Service must create images", createAnimeImagesResult)
    }

    @Test(expected = EntityExistsException::class)
    fun createAnimeImages_TryToCreateImageFromAnAlreadyInsertedAnimeImages_GetException() {
        animeService.createAnimeImages(1, Collections.emptyList())
    }

}