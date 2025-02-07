package com.robosoft.newsapplication.component

import AuthorTextWithPopup
import android.webkit.WebView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.robosoft.newsapplication.R
import com.robosoft.newsapplication.navigation.NavScreen
import com.robosoft.newsapplication.network.data.model.Article
import com.robosoft.newsapplication.network.data.repo.ArticleRepository
import kotlinx.coroutines.delay

@Composable
fun ArticleDetailScreen(articleId: String?, navController: NavController) {
    if (articleId.isNullOrEmpty()) {
        Text("Invalid article ID")
        return
    }

    var isLoading by remember { mutableStateOf(true) }

    // Simulate loading delay
    LaunchedEffect(key1 = true) {
        delay(2000)
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Robo News") },
                backgroundColor = Color.Black,
                contentColor = Color.White,
                elevation = 10.dp,
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            ImageVector.vectorResource(R.drawable.back_arrow),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        if (isLoading) {
            // Display a circular progress indicator while loading
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(color = MaterialTheme.colors.primary)
            }
        } else {
            // Load the article content after the 'isLoading' is set to false
            ArticleRepository.getArticleById(articleId)?.let { article ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(vertical = 10.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Your existing content rendering logic
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        textAlign = TextAlign.Start
                    )
                    Spacer(Modifier.height(16.dp))
                    if (article.imageUrl.isNullOrEmpty().not()) {
                        Image(
                            painter = rememberAsyncImagePainter(article.imageUrl),
                            contentDescription = "Hero Image",
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                        Spacer(Modifier.height(16.dp))
                    }
                    AuthorTextWithPopup(article)
                    Spacer(Modifier.height(8.dp))
                    if (article.subtitle.isNullOrEmpty().not()) {
                        Text(
                            text = article.subtitle!!,
                            style = MaterialTheme.typography.subtitle2,
                            color = MaterialTheme.colors.onPrimary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                    ArticleContent(article = article)
                    Spacer(Modifier.height(8.dp))
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
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
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
        )
    } else if (article.type == "video") {
        VideoPlayer(article.videoUrl)
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
