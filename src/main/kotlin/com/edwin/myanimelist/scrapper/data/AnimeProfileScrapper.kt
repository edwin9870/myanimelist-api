package com.edwin.myanimelist.scrapper.data

import com.edwin.myanimelist.util.stripHtml
import com.edwin.myanimelist.util.trimEachNewLine
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component

@Component
class AnimeProfileScrapper {

    fun getTitle(htmlContent: String): String {
        val document: Document = Jsoup.parse(htmlContent)
        val animeTitle = document.select("#contentWrapper div h1 span[itemprop = name]").text()
        return animeTitle
    }

    fun getSynopsis(htmlContent: String): String {
        val document: Document = Jsoup.parse(htmlContent)
        var animeSynopsis = document.select("#content > table > tbody  table  span[itemprop = description]").html().replace("<br />|<br>|<br/>".toRegex(),"\n")
        animeSynopsis = animeSynopsis.replace("\\[Written by [^]]+]".toRegex(),"").trim()
        return animeSynopsis.trimEachNewLine().stripHtml()
    }

}