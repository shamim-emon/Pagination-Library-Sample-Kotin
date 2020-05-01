package com.emon.newsapp.data.model

data class ApiResponse(
    var articles: List<Article>,
    var status: String,
    var totalResults: Int
)