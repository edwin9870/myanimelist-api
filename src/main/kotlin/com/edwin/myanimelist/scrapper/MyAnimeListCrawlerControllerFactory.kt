package com.edwin.myanimelist.scrapper

import edu.uci.ics.crawler4j.crawler.CrawlController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MyAnimeListCrawlerControllerFactory @Autowired constructor(private var myAnimeListCrawler: MyAnimeListCrawler) : CrawlController.WebCrawlerFactory<MyAnimeListCrawler> {

    override fun newInstance(): MyAnimeListCrawler {
        return myAnimeListCrawler
    }
}