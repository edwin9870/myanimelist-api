package com.edwin.myanimelist.scrapper

import com.edwin.myanimelist.data.dto.AnimeDto
import com.edwin.myanimelist.exceptions.EntityExistsException
import com.edwin.myanimelist.scrapper.data.AnimeProfileScrapper
import com.edwin.myanimelist.services.AnimeService
import edu.uci.ics.crawler4j.crawler.Page
import edu.uci.ics.crawler4j.crawler.WebCrawler
import edu.uci.ics.crawler4j.parser.HtmlParseData
import edu.uci.ics.crawler4j.url.WebURL
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class MyAnimeListCrawler @Autowired constructor(private var animeListUrlValidator: MyAnimeListUrlValidator, private var animeProfileScrapper: AnimeProfileScrapper, private var animeService: AnimeService) : WebCrawler() {
    private val logger: Logger = LoggerFactory.getLogger(MyAnimeListCrawler::class.java)

    override fun shouldVisit(referringPage: Page?, webURL: WebURL?): Boolean {
        if (webURL == null) {
            return false
        }

        val url = webURL.url.toLowerCase()
        if (animeListUrlValidator.isAnimePage(url) ||
                animeListUrlValidator.isAnimeListPage(url) ||
                animeListUrlValidator.isAnimeImagesPage(url)) {
            return true
        }

        return false

    }

    override fun visit(page: Page?) {
        if (page == null) {
            return
        }

        val url = page.webURL.url
        logger.debug("Page to visit: {}", url)
        if (page.parseData is HtmlParseData) {
            val htmlParseData = page.parseData as HtmlParseData
            val html = htmlParseData.html

            if (animeListUrlValidator.isAnimePage(url)) {
                val myAnimeListId: Int = animeProfileScrapper.getMyAnimeListId(url)
                val name = animeProfileScrapper.getTitle(html)
                val synopsis = animeProfileScrapper.getSynopsis(html)
                val episodesNumber = animeProfileScrapper.getEpisodesNumber(html)
                val onAiring = animeProfileScrapper.isOnAiring(html)
                val animeReleaseDate = animeProfileScrapper.getAnimeReleaseDate(html)
                val animeEndDate = animeProfileScrapper.getAnimeEndDate(html)

                val anime = AnimeDto(myAnimeListId, name, synopsis, url, episodesNumber, onAiring, animeReleaseDate, animeEndDate)
                logger.info("Anime to save: $anime")

                try {
                    animeService.create(anime)
                    logger.info("Created!")
                } catch (e: EntityExistsException) {
                    logger.warn("Entity already exist")
                }

            }

            if (animeListUrlValidator.isAnimeImagesPage(url)) {
                val myAnimeListId: Int = animeProfileScrapper.getMyAnimeListId(url)
                val animeImagesUrl = animeProfileScrapper.getAnimeImages(html)

                logger.info("Saving anime images with the myAnimeListId: $myAnimeListId")
                logger.debug("Anime images's urls: $animeImagesUrl")

                try {
                    animeService.createAnimeImages(myAnimeListId, animeImagesUrl)
                } catch (e: EntityExistsException) {
                    logger.warn("Entity already exist")
                }
            }
        }
    }
}