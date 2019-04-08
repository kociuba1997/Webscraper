package com.newsscraper.transportobjects

data class NewsDTO(
    val id: String,
    val tags: List<String>,
    val author: String,
    val text: String,
    val link: String
)