package com.newsscraper.transportobjects

import java.io.Serializable

data class NewsDTO(
    val id: String,
    val tags: List<String>,
    val author: String?,
    val text: String?,
    val link: String?,
    val photo: String?
) : Serializable