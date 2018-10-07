package com.edwin.myanimelist.services

import com.edwin.myanimelist.data.dto.AnimeDto
import com.edwin.myanimelist.data.entities.Anime
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
import java.time.LocalDate

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("test")
class AnimeServiceTest {


    @Autowired
    private lateinit var animeService: AnimeService
    private val animeRepository: AnimeRepository = Mockito.mock(AnimeRepository::class.java)

    private var initialized: Boolean = false

    @Before
    fun setUp() {
        if (!initialized) {
            animeService.animeRepository = animeRepository

            Mockito.`when`(animeRepository.findByName("Goku")).thenReturn(null)
            val mockAnimeResponse = Anime(
                    "ajkskajsk",
                    "Boruto",
                    "The Naruto's son",
                    "https://myanimelist.net/anime/34566/Boruto__Naruto_Next_Generations",
                    1,
                    true,
                    LocalDate.now(),
                    LocalDate.now())
            Mockito.`when`(animeRepository.findByName("Boruto")).thenReturn(mockAnimeResponse)
            initialized = true
        }
    }

    @Test
    fun create_NoExistingAnime_GetTrue() {
        val anime = AnimeDto("Goku", "The best",
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
        animeService.create(AnimeDto("Boruto",
                "The Naruto's son",
                "https://myanimelist.net/anime/34566/Boruto__Naruto_Next_Generations",
                1,
                true,
                LocalDate.now(),
                LocalDate.now()))
    }

}