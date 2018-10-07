package com.edwin.myanimelist.scrapper

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("test")
class MyAnimeListUrlValidatorTest {

    @Autowired
    lateinit var myAnimeListUrlValidator: MyAnimeListUrlValidator

    @Test
    fun isAnimeListPage_EmptyUrl_FalseReturned() {
        assertFalse("Empty url must not be allowed",myAnimeListUrlValidator.isAnimeListPage(""))
    }

    @Test
    fun isAnimeListPage_ValidUrl_TrueReturned() {
        assertTrue("Valid URL with letter U must be valid", myAnimeListUrlValidator.isAnimeListPage("https://myanimelist.net/anime.php?letter=U"))
        assertTrue("Valid URL with letter U must be valid", myAnimeListUrlValidator.isAnimeListPage("https://myanimelist.net/anime.php?letter=A"))
        assertTrue("Valid URL with letter U and show query parameter must be valid",myAnimeListUrlValidator.isAnimeListPage("https://myanimelist.net/anime.php?letter=U&show=250"))
    }

    @Test
    fun isAnimeListPage_InvalidUrl_FalseReturned() {
        assertFalse("A different domain than myanimelist.net must not be valid", myAnimeListUrlValidator.isAnimeListPage("http://google.com.do"))
        assertFalse("Url that contains others query parameter than letter and show is not valid", myAnimeListUrlValidator.isAnimeListPage("https://myanimelist.net/anime.php?letter=U&sy=0&sm=0&sd=0&ey=0&em=0&ed=0&o=3&w=1"))

    }

    @Test
    fun isAnimePage_EmptyUrl_FalseReturned() {
        assertFalse("Empty url must not be allowed", myAnimeListUrlValidator.isAnimePage(""))
    }

    @Test
    fun isAnimePage_ValidUrl_TrueReturned() {
        assertTrue("Anime homepage must be valid", myAnimeListUrlValidator.isAnimePage("https://myanimelist.net/anime/35760/Shingeki_no_Kyojin_Season_3"))

        assertTrue("Anime homepage with an underscore name in their must be valid", myAnimeListUrlValidator.isAnimePage("https://myanimelist.net/anime/31765/Sword_Art_Online_Movie__Ordinal_Scale"))

    }

    @Test
    fun isAnimePage_InvalidUrl_FalseReturned() {
        assertFalse("Anime homepage with a question mark in their must not be valid", myAnimeListUrlValidator.isAnimePage("https://myanimelist.net/anime/36475/Sword_Art_Online_Alternative__Gun_Gale_Online?suggestion"))
    }


    @Test
    fun isAnimeImagesPage_ValidUrl_TrueReturned() {
        assertTrue("Anime pics must be valid", myAnimeListUrlValidator.isAnimeImagesPage("https://myanimelist.net/anime/34096/Gintama/pics"))
    }

    @Test
    fun isAnimeImagesPage_InvalidUrl_FalseReturned() {
        assertFalse("Different Anime pics's url must not be valid", myAnimeListUrlValidator.isAnimeImagesPage("https://myanimelist.net/anime/36475/Sword_Art_Online_Alternative__Gun_Gale_Online?suggestion"))
    }

    @Test
    fun getMyAnimeListIdFromUrl_SendValidUrl_GetId() {
        assertEquals("Id is not valid", 34096, myAnimeListUrlValidator.getMyAnimeListIdFromUrl("https://myanimelist.net/anime/34096/Gintama/pics"))
    }

    @Test(expected = IllegalStateException::class)
    fun getMyAnimeListIdFromUrl_SendInvalidUrl_GetNull() {
        assertNull("Url is invalid, method must throw a exception", myAnimeListUrlValidator.getMyAnimeListIdFromUrl("wskdjksjdksd"))
    }

}
