package com.edwin.myanimelist.scrapper

import edu.uci.ics.crawler4j.crawler.Page
import edu.uci.ics.crawler4j.crawler.WebCrawler
import edu.uci.ics.crawler4j.parser.HtmlParseData
import edu.uci.ics.crawler4j.url.WebURL
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class MyAnimeListCrawler @Autowired constructor(private var animeListUrlValidator: MyAnimeListUrlValidator) : WebCrawler() {
    private val log: Logger = LoggerFactory.getLogger(MyAnimeListCrawler::class.java)

    override fun shouldVisit(referringPage: Page?, webURL: WebURL?): Boolean {
        if (webURL == null) {
            return false
        }

        val url = webURL.url.toLowerCase()
        if (animeListUrlValidator.isAnimePage(url)) {
            return true
        }

        if (animeListUrlValidator.isAnimeListPage(url)) {
            return true
        }

        return false

    }

    override fun visit(page: Page?) {
        if (page == null) {
            return
        }

        val url = page.webURL.url
        log.debug("Page to visit: {}", url)
        if (page.parseData is HtmlParseData) {
            val htmlParseData = page.parseData as HtmlParseData
            val text = htmlParseData.text
            val html = htmlParseData.html
            val links = htmlParseData.outgoingUrls

            log.debug("Text length: " + text.length)
            log.debug("Html length: " + html.length)
            log.debug("Number of outgoing links: " + links.size)
        }
    }
}