package com.edwin.myanimelist.util

fun String.trimEachNewLine(): String {
    val stringLines = this.split("\n")
    val trimmedLines = StringBuffer()

    for (line in stringLines) {
        trimmedLines.append(line.trim { it <= ' ' } + "\n")
    }
    return trimmedLines.trim().toString()
}

fun String.stripHtml(): String {
    return this.replace("\\\\<[^>]*>".toRegex(), "")
}