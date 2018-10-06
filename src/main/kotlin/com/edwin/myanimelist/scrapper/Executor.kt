package com.edwin.myanimelist.scrapper

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import edu.uci.ics.crawler4j.crawler.CrawlController
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig
import edu.uci.ics.crawler4j.fetcher.PageFetcher
import edu.uci.ics.crawler4j.crawler.CrawlConfig



val log: Logger = LoggerFactory.getLogger("MainLogger")

fun main(args: Array<String>) {
    val crawlStorageFolder = "C:\\Users\\erv-2\\Downloads\\craw"
    val numberOfCrawlers = 7

    val config = CrawlConfig()
    config.crawlStorageFolder = crawlStorageFolder

    /*
         * Instantiate the controller for this crawl.
         */
    val pageFetcher = PageFetcher(config)
    val robotstxtConfig = RobotstxtConfig()
    val robotstxtServer = RobotstxtServer(robotstxtConfig, pageFetcher)
    val controller = CrawlController(config, pageFetcher, robotstxtServer)

    /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
    controller.addSeed("https://myanimelist.net/anime.php")

    /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
    controller.start(MyAnimeListCrawler::class.java, numberOfCrawlers)
}