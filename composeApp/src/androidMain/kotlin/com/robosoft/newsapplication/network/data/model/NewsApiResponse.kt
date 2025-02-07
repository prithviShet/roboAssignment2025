package com.robosoft.newsapplication.network.data.model

import com.google.gson.annotations.SerializedName


data class NewsApiResponse(
    @SerializedName("nextPage")
    val nextPage: String = "",
    @SerializedName("results")
    val articles: ArrayList<Article> = arrayListOf(),
    @SerializedName("status")
    val status: String = "",
    @SerializedName("totalResults")
    val totalResults: Int = 0
)

data class Article(
    @SerializedName("ai_org")
    val aiOrg: String = "",
    @SerializedName("ai_region")
    val aiRegion: String = "",
    @SerializedName("ai_tag")
    val aiTag: String = "",
    @SerializedName("article_id")
    val id: String = "",
    @SerializedName("category")
    val category: List<String> = listOf(),
    @SerializedName("content")
    val content: String = "",
    @SerializedName("country")
    val country: List<String> = listOf(),
    @SerializedName("creator")
    val creator: List<String> = listOf(),
    @SerializedName("description")
    val subtitle: String? = "",
    @SerializedName("duplicate")
    val duplicate: Boolean = false,
    @SerializedName("image_url")
    val imageUrl: String = "",
    @SerializedName("keywords")
    val keywords: List<String> = listOf(),
    @SerializedName("language")
    val language: String = "",
    @SerializedName("link")
    val contentUrl: String = "",
    @SerializedName("pubDate")
    val pubDate: String = "",
    @SerializedName("pubDateTZ")
    val pubDateTZ: String = "",
    @SerializedName("sentiment")
    val sentiment: String = "",
    @SerializedName("sentiment_stats")
    val sentimentStats: String = "",
    @SerializedName("source_icon")
    val authorImage: String = "https://i.bytvi.com/domain_icons/menafn.png",
    @SerializedName("source_id")
    val sourceId: String = "",
    @SerializedName("source_name")
    val sourceName: String = "",
    @SerializedName("source_priority")
    val sourcePriority: Int = 0,
    @SerializedName("source_url")
    val sourceUrl: String = "",
    @SerializedName("title")
    val title: String = "",
    val videoUrl: String = listOf(
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
    ).random(),
    val tag: String = listOf("Action", "Politics", "Stock Market", "Law and Justice").random(),
    val categoory: String = listOf("Markets", "Fintech", "Editorial", "BNM").random(),
    val author: String = listOf("Suraj D", "Ramit Y", "Gopika G", "Prithvi S").random(),
    val type: String = listOf("text", "video").random(),
    val authorDesc: String = "State Department freezes new funding for nearly all US aid programs worldwide",
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

