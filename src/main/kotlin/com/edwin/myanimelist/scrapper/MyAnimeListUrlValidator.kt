package com.edwin.myanimelist.scrapper

import org.springframework.stereotype.Component

@Component
class MyAnimeListUrlValidator {

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

}