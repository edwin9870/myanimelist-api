package com.edwin.myanimelist.scrapper.data

import com.edwin.myanimelist.util.stripHtml
import com.edwin.myanimelist.util.trimEachNewLine
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AnimeProfileScrapper {

    private val logger: Logger = LoggerFactory.getLogger(AnimeProfileScrapper::class.java)

    fun getTitle(htmlContent: String): String {
        val document: Document = Jsoup.parse(htmlContent)
        val animeTitle = document.select("#contentWrapper div h1 span[itemprop = name]").text()
        return animeTitle
    }

    fun getSynopsis(htmlContent: String): String {
        val document: Document = Jsoup.parse(htmlContent)
        var animeSynopsis = document.select("#content > table > tbody  table  span[itemprop = description]").html().replace("<br />|<br>|<br/>".toRegex(), "\n")
        animeSynopsis = animeSynopsis.replace("\\[Written by [^]]+]".toRegex(), "").trim()
        return animeSynopsis.trimEachNewLine().stripHtml()
    }

    /**
     * Method to search the numbers of episodes of an Anime
     * @return number of episodes, or -1 if it can't find it
     */
    fun getEpisodesNumber(htmlContent: String): Short {
        val document = Jsoup.parse(htmlContent)
        val episodeString = document.select("#content > table > tbody div span:containsOwn(pisodes:)")?.parents()?.first()?.text()
        logger.debug("episode string: $episodeString")

        if (episodeString == null) {
            return -1
        }

        return episodeString.replace("[^\\d]+".toRegex(), "").toShort()
    }

}