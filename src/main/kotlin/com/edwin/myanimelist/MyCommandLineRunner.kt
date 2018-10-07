package com.edwin.myanimelist

import com.edwin.myanimelist.scrapper.MyAnimeListCrawlerControllerFactory
import com.edwin.myanimelist.scrapper.MyAnimeListCrawler
import edu.uci.ics.crawler4j.crawler.CrawlConfig
import edu.uci.ics.crawler4j.crawler.CrawlController
import edu.uci.ics.crawler4j.fetcher.PageFetcher
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("!test")
class MyCommandLineRunner constructor(private var myAnimeListCrawlerControllerFactory: MyAnimeListCrawlerControllerFactory) : CommandLineRunner {
    private val logger: Logger = LoggerFactory.getLogger(MyAnimeListCrawler::class.java)

    override fun run(vararg args: String?) {
        logger.info("Start....")

        val crawlStorageFolder = "D:\\Downloads\\craw"
        val numberOfCrawlers = 7

        val config = CrawlConfig()
        config.crawlStorageFolder = crawlStorageFolder

        val pageFetcher = PageFetcher(config)
        val robotstxtConfig = RobotstxtConfig()
        val robotstxtServer = RobotstxtServer(robotstxtConfig, pageFetcher)
        val controller = CrawlController(config, pageFetcher, robotstxtServer)

        controller.addSeed("https://myanimelist.net/anime.php")

        controller.startNonBlocking(myAnimeListCrawlerControllerFactory, numberOfCrawlers)
        logger.info("End")
    }
}