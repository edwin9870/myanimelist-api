package com.edwin.myanimelist.scrapper.data

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.net.URL
import java.time.Month

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("test")
class AnimeProfileScrapperTest {

    val animePageHtmlContent: String = AnimeProfileScrapperTest::class.java.getResource("/HTML/Fullmetal Alchemist_ Brotherhood - MyAnimeList.net.html").readText()
    val animeImagesPageHtmlContent: String = AnimeProfileScrapperTest::class.java.getResource("/HTML/Gintama. (Gintama Season 5) - Pictures - MyAnimeList.net.html").readText()
    val animeTitle: String = "Fullmetal Alchemist: Brotherhood"
    val animeSynopsis: String = "\"In order for something to be obtained, something of equal value must be lost.\"\n" +
            "\n" +
            "Alchemy is bound by this Law of Equivalent Exchange—something the young brothers Edward and Alphonse Elric only realize after attempting human transmutation: the one forbidden act of alchemy. They pay a terrible price for their transgression—Edward loses his left leg, Alphonse his physical body. It is only by the desperate sacrifice of Edward's right arm that he is able to affix Alphonse's soul to a suit of armor. Devastated and alone, it is the hope that they would both eventually return to their original bodies that gives Edward the inspiration to obtain metal limbs called \"automail\" and become a state alchemist, the Fullmetal Alchemist.\n" +
            "\n" +
            "Three years of searching later, the brothers seek the Philosopher's Stone, a mythical relic that allows an alchemist to overcome the Law of Equivalent Exchange. Even with military allies Colonel Roy Mustang, Lieutenant Riza Hawkeye, and Lieutenant Colonel Maes Hughes on their side, the brothers find themselves caught up in a nationwide conspiracy that leads them not only to the true nature of the elusive Philosopher's Stone, but their country's murky history as well. In between finding a serial killer and racing against time, Edward and Alphonse must ask themselves if what they are doing will make them human again... or take away their humanity."

    @Autowired
    lateinit var animeProfileScrapper: AnimeProfileScrapper;

    @Test
    fun getTitle_ValidAnimeTitle_MustGetTrue() {
        val title = animeProfileScrapper.getTitle(animePageHtmlContent)
        assertEquals("Invalid anime's title", animeTitle, title)
    }

    @Test
    fun getTitle_InvalidAnimeTitle_MustGetFalse() {
        val title = animeProfileScrapper.getTitle(animePageHtmlContent)
        assertNotEquals("Anime's title must be invalid", "xdfckjksa", title)
    }

    @Test
    fun getSynopsis_GetValidSummary_MustGetTrue() {
        val synopsis = animeProfileScrapper.getSynopsis(animePageHtmlContent)
        assertEquals("Invalid anime's summary", animeSynopsis, synopsis)
    }

    @Test
    fun getEpisodesNumber_GetNumberOfEpisodes_GetNumberOfEpisodes() {
        val episodesNumber = animeProfileScrapper.getEpisodesNumber(animePageHtmlContent)
        assertTrue("The number of episode must be equal to 64", episodesNumber.toInt() == 64)
    }

    @Test
    fun getEpisodesNumber_InvalidHtmlContent_GetMinusOne() {
        val episodesNumber = animeProfileScrapper.getEpisodesNumber("xksjdkskdaskdj")
        assertTrue("The number of episode must be equal to -1", episodesNumber.toInt() == -1)
    }

    @Test
    fun isOnAiring_GetAiringAnimeStatus_GetTrue() {
        val onAiring = animeProfileScrapper.isOnAiring(animePageHtmlContent)
        if (onAiring == null) {
            fail("Couldn't get on airing value")
            return
        }
        assertFalse("This anime must not be on airing", onAiring)
    }

    @Test
    fun getAnimeReleaseDate_GetReleaseDateFromValidContent_GetReleaseDate() {
        val releaseDate = animeProfileScrapper.getAnimeReleaseDate(animePageHtmlContent)

        assertNotNull("Release date must not be null", releaseDate)
        assertEquals("Release year is invalid", 2009, releaseDate?.year)
        assertEquals("Release month is invalid", Month.APRIL, releaseDate?.month)
        assertEquals("Release day is invalid", 5, releaseDate?.dayOfMonth)
    }

    @Test
    fun getAnimeReleaseDate_SendEmptyContent_GetNullResponse() {
        val releaseDate = animeProfileScrapper.getAnimeReleaseDate("lkdslkdlsd")

        assertNull("Release date must be null", releaseDate)
    }


    @Test
    fun getAnimeEndDate_GetReleaseEndFromValidContent_GetEndDate() {
        val releaseDate = animeProfileScrapper.getAnimeEndDate(animePageHtmlContent)

        assertNotNull("End date must not be null", releaseDate)
        assertEquals("End year is invalid", 2010, releaseDate?.year)
        assertEquals("End month is invalid", Month.JULY, releaseDate?.month)
        assertEquals("End day is invalid", 4, releaseDate?.dayOfMonth)
    }

    @Test
    fun getMyAnimeListIdFromUrl_SendValidUrl_GetId() {
        assertEquals("Id is not valid", 34096, animeProfileScrapper.getMyAnimeListId("https://myanimelist.net/anime/34096/Gintama/pics"))
    }

    @Test(expected = IllegalStateException::class)
    fun getMyAnimeListIdFromUrl_SendInvalidUrl_GetNull() {
        assertNull("Url is invalid, method must throw a exception", animeProfileScrapper.getMyAnimeListId("wskdjksjdksd"))
    }

    @Test
    fun getAnimeImages_SendValidContent_GetImagesUrl() {
        val animeImagesUrlList = animeProfileScrapper.getAnimeImages(animeImagesPageHtmlContent)
        assertEquals("Images number must be 5",5, animeImagesUrlList.size)
        assertTrue(animeImagesUrlList.stream().anyMatch { it == URL("https://myanimelist.cdn-dena.com/images/anime/11/82056l.jpg") })
        assertTrue(animeImagesUrlList.stream().anyMatch { it == URL("https://myanimelist.cdn-dena.com/images/anime/13/83077l.jpg") })
        assertTrue(animeImagesUrlList.stream().anyMatch { it == URL("https://myanimelist.cdn-dena.com/images/anime/9/83132l.jpg") })
        assertTrue(animeImagesUrlList.stream().anyMatch { it == URL("https://myanimelist.cdn-dena.com/images/anime/13/83412l.jpg") })
        assertTrue(animeImagesUrlList.stream().anyMatch { it == URL("https://myanimelist.cdn-dena.com/images/anime/3/83528l.jpg") })


    }

}
