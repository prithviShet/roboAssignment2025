package com.robosoft.newsapplication.network.data.repo

import com.google.gson.Gson
import com.robosoft.newsapplication.network.data.model.Article
import com.robosoft.newsapplication.network.data.model.NewsApiResponse


object ArticleRepository {

    private val articlesList: ArrayList<Article> = arrayListOf()

    fun parseJson(jsonString: String) {
        val data = Gson().fromJson(jsonString, NewsApiResponse::class.java)
        articlesList.addAll(data.articles)
    }

    fun getArticles(tag: String?): List<Article> =
        articlesList.filter { article -> article.tag == tag }.takeIf { tag?.lowercase() != "all" }
            ?: articlesList


    fun getArticleById(articleId: String): Article? = articlesList.find {
        it.id == articleId
    }

    fun filterArticlesByTag(who: Int, filterText: String) =
        when (who) {
            Article.AUTHOR -> articlesList.filter { it.author == filterText }
            Article.CATEGORY -> articlesList.filter { it.categoory == filterText }
            Article.ARTICLE_TYPE -> articlesList.filter { it.type == filterText }
            Article.TAG -> if (filterText.lowercase() == "all") {
                articlesList
            } else {
                articlesList.filter { it.tags.contains(filterText) }
            }

            else -> articlesList
        }

    fun filterArticlesByChoice(who: Int): List<String> =
        when (who) {
            Article.AUTHOR -> articlesList.map { it.author }.distinct()
            Article.CATEGORY -> articlesList.map { it.categoory }.distinct()
            Article.ARTICLE_TYPE -> articlesList.map { it.type }.distinct()
            Article.TAG -> articlesList.map { it.tag }.distinct()
            else -> listOf()
        }
}