package com.robosoft.newsapplication.component

import AuthorTextWithPopup
import android.webkit.WebView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.robosoft.newsapplication.model.Article
import com.robosoft.newsapplication.model.ArticleRepository
import com.robosoft.newsapplication.navigation.NavScreen

@Composable
fun ArticleDetailScreen(articleId: String?, navController: NavController) {
    if (articleId.isNullOrEmpty()) {
        Text("Invalid article ID")
        return
    }

    ArticleRepository.getArticleById(articleId.toInt())?.let { article ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))
            Image(
                painter = rememberAsyncImagePainter(article.imageUrl),
                contentDescription = "Hero Image",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(16.dp))
            AuthorTextWithPopup(article)
            Spacer(Modifier.height(8.dp))
            Text(
                text = article.subtitle,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(Modifier.height(8.dp))
            ArticleContent(article = article)
            Spacer(Modifier.height(8.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                itemsIndexed(article.tags) { index, tag ->
                    Chip(tag = tag) {
                        navController.navigate("${NavScreen.ArticleListScreen.route}/$tag")
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleContent(article: Article) {
    if (article.type == "text") {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    loadUrl(article.contentUrl)
                }
            }, modifier = Modifier
                .fillMaxSize()
        )
    } else if (article.type == "video") {
        VideoPlayer(article.contentUrl)
//        VideoPlayer("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        /*AndroidView(
            factory = { context ->
                VideoView(context).apply {
                    setVideoPath(article.contentUrl)
                    start()
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )*/
    }
}

@Composable
fun Chip(tag: String, onTagClick: () -> Unit) {
    Surface(
        modifier = Modifier.clickable {
            onTagClick()
        },
        shape = MaterialTheme.shapes.small,
        color = Color.LightGray
    ) {
        Text(
            text = tag,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.body2
        )
    }
}
