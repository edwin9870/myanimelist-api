package com.edwin.myanimelist.scrapper.data

import com.edwin.myanimelist.util.stripHtml
import com.edwin.myanimelist.util.trimEachNewLine
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter



@Component
class AnimeProfileScrapper {

    private val logger: Logger = LoggerFactory.getLogger(AnimeProfileScrapper::class.java)
    private val myAnimeListDateFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy")

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

    fun isOnAiring(htmlContent: String): Boolean? {
        val document = Jsoup.parse(htmlContent)
        val animeStatusString = document.select("#content > table > tbody div span:containsOwn(tatus:)")?.parents()?.first()?.text()
        logger.debug("animeStatusString: $animeStatusString")

        if (animeStatusString == null) {
            return null
        }

        return !animeStatusString.contains("finished airing", true)
    }

    fun getAnimeReleaseDate(htmlContent: String): LocalDate? {
        val document = Jsoup.parse(htmlContent)
        var animeReleaseDateString: String? = document.select("#content > table > tbody div span:containsOwn(ired:)")?.parents()?.first()?.text()
        animeReleaseDateString ?: return null

        animeReleaseDateString = animeReleaseDateString.split("to")[0].replace("^[^:]+: ".toRegex(), "").trim()
        logger.debug("animeReleaseDateString: $animeReleaseDateString")



        val animeReleaseDate = LocalDate.parse(animeReleaseDateString, myAnimeListDateFormatter)
        logger.debug("Release date: $animeReleaseDate")
        return animeReleaseDate
    }

    fun getAnimeEndDate(htmlContent: String): LocalDate? {
        val document = Jsoup.parse(htmlContent)
        var animeEndDateString: String? = document.select("#content > table > tbody div span:containsOwn(ired:)")?.parents()?.first()?.text()
        animeEndDateString ?: return null

        animeEndDateString = animeEndDateString.split("to")[1].trim()
        logger.debug("animeEndDateString: $animeEndDateString")



        val animeReleaseDate = LocalDate.parse(animeEndDateString, myAnimeListDateFormatter)
        logger.debug("End date: $animeReleaseDate")
        return animeReleaseDate
    }

    fun getMyAnimeListIdFromUrl(url: String): Int {
        val regexValue: String? = "^https:\\/\\/myanimelist.net\\/anime\\/(\\d+)".toRegex().find(url)?.groupValues?.get(1)
        logger.debug("regexValue: $regexValue")
        if(regexValue == null) {
            throw IllegalStateException("Invalid URL")
        }
        return regexValue.toInt()
    }

}