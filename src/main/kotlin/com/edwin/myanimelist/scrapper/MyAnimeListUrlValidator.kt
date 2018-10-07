package com.edwin.myanimelist.scrapper

import com.edwin.myanimelist.scrapper.data.AnimeProfileScrapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class MyAnimeListUrlValidator {

    private val logger: Logger = LoggerFactory.getLogger(MyAnimeListUrlValidator::class.java)

    fun isAnimeListPage(url: String): Boolean {

        val regexToMatch = Regex("^https:\\/\\/myanimelist.net\\/anime\\.php\\?letter=[A-Z](\\&show=\\d+)?$", RegexOption.IGNORE_CASE)
        if (regexToMatch.matches(url.toLowerCase())) {
            return true
        }

        return false
    }

    fun isAnimePage(url: String): Boolean {
        val regexToMatch = Regex("^https:\\/\\/myanimelist.net\\/anime\\/\\d+\\/[^\\/]+(?<!\\?suggestion)\$", RegexOption.IGNORE_CASE)
        if (regexToMatch.matches(url.toLowerCase())) {
            return true
        }

        return false
    }

    fun isAnimeImagesPage(url: String): Boolean {
        val regexToMatch = Regex("^https:\\/\\/myanimelist.net\\/anime\\/\\d+\\/[^\\/]+\\/pics\$", RegexOption.IGNORE_CASE)
        if (regexToMatch.matches(url.toLowerCase())) {
            return true
        }

        return false
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