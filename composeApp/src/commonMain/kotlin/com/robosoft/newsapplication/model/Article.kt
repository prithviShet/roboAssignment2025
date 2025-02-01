package com.robosoft.newsapplication.model

data class Article(
    val id: Int,
    val title: String,
    val subtitle: String,
    val imageUrl: String?,
    val tag: String = listOf("Action", "Politics", "Stock Market", "Law and Justice").random(),
    val category: String = listOf("Markets", "Fintech", "Editorial", "BNM").random(),
    val author: String = listOf("SSD", "Ramit", "Gopika", "Prithvi").random(),
    val contentUrl: String = "https://www.youtube.com/watch?v=Fihf0_-5FmI&list=RDeZ4gHbBWep8&index=4",
    val type: String = listOf("text", "video").random(),
    val authorDesc: String = "State Department freezes new funding for nearly all US aid programs worldwide",
    val authorImage: String = "https://i.bytvi.com/domain_icons/cbsnews.png",
    val tags: List<String> = listOf("Action", "Politics", "Stock Market", "Law and Justice"),
) {
    companion object {
        const val AUTHOR = 0
        const val CATEGORY = 1
        const val ARTICLE_TYPE = 2
        const val TAG = 3

        val filterMap = mapOf(
            "Author" to AUTHOR,
            "Category" to CATEGORY,
            "Article Type" to ARTICLE_TYPE,
            "Tag" to TAG
        )
    }
}


object ArticleRepository {

    private val articlesList = listOf(
        Article(
            id = 10,
            title = "Senate confirms Pete Hegseth as Trump's defense secretary",
            subtitle = "Hegseth's nomination once appeared on shaky ground amid allegations that included sexual misconduct and financial mismanagement.",
            imageUrl = "https://assets1.cbsnewsstatic.com/hub/i/r/2025/01/25/e590d50b-587f-411a-bf66-c598e1c8026e/thumbnail/60x60/487b9066d32e06267f4e88b6939aaf27/gettyimages-2194044609.jpg?v=e306e7b9fefc168c00baa623d76d1eaf",
            category = "BNM",
            tag = "Politics",
            type = "text",
            contentUrl = "https://www.cbsnews.com/news/pete-hegseth-confirmation-vote-senate-defense-secretary/",
            tags = listOf(
                "Politics",
                "Law and Justice"
            )
        ),
        Article(1, "Title for Video", "Subtitle 1", null, tag = "Action", type = "video",
//            contentUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"),
            contentUrl = "https://www.youtube.com/watch?v=esMMHzePMVk&list=RDWwOcAdyZfgY&index=2"),
        Article(2, "Title 2", "Subtitle 2", null, tag = "Politics"),
        Article(3, "Title 3", "Subtitle 3", null, tag = "Stock Market"),
        Article(4, "Title 1", "Subtitle 1", null, tag = "Action"),
        Article(5, "Title 2", "Subtitle 2", null, tag = "Politics", category = "Politics"),
        Article(6, "Title 3", "Subtitle 3", null, tag = "Stock Market"),
        Article(7, "Title 1", "Subtitle 1", null, tag = "Action"),
        Article(8, "Title 2", "Subtitle 2", null, tag = "Politics"),
        Article(9, "Title 3", "Subtitle 3", null, tag = "Stock Market"),
    )

    fun getArticles(tag: String?): List<Article> =
        articlesList.filter { article -> article.tag == tag }.takeIf { tag?.lowercase() != "all" }
            ?: articlesList


    fun getArticleById(articleId: Int): Article? = articlesList.find {
        it.id == articleId
    }

    fun filterArticlesByTag(who: Int, filterText: String) =
        when (who) {
            Article.AUTHOR -> articlesList.filter { it.author == filterText }
            Article.CATEGORY -> articlesList.filter { it.category == filterText }
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
            Article.CATEGORY -> articlesList.map { it.category }.distinct()
            Article.ARTICLE_TYPE -> articlesList.map { it.type }.distinct()
            Article.TAG -> articlesList.map { it.tag }.distinct()
            else -> listOf()
        }
}
