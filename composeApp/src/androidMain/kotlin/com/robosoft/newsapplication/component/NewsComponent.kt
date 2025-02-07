package com.robosoft.newsapplication.component

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.robosoft.newsapplication.R
import com.robosoft.newsapplication.navigation.NavScreen
import com.robosoft.newsapplication.network.data.model.Article
import com.robosoft.newsapplication.network.data.repo.ArticleRepository


@Composable
fun ArticleListScreen(
    articles: List<Article>,
    navController: NavController,
    listState: LazyListState
) {
    val filters = listOf("Author", "Category", "Article Type", "Tag")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Robo News") },
                backgroundColor = Color.Black,
                contentColor = Color.White,
                elevation = 10.dp,
                navigationIcon = {
                    val context = LocalContext.current
                    IconButton(onClick = {
                        (context as? Activity)?.finish()
                    }) {
                        Icon(
                            ImageVector.vectorResource(R.drawable.back_arrow),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            FilterSection(
                onTagSelected = { tag, who ->
                    navController.navigate("${NavScreen.ArticleListScreen.route}/$tag/$who")
                }, availableTags = filters,
                listState = listState
            )
            ArticleList(articles, navController)
        }
    }
}

@Composable
fun FilterSection(
    onTagSelected: (String, Int) -> Unit,
    availableTags: List<String>,
    listState: LazyListState
) {
    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        itemsIndexed(availableTags) { _, filter ->
            DropDownUI(
                filter,
                onTagSelected,
                ArticleRepository.filterArticlesByChoice(Article.filterMap[filter] ?: 0)
            )
        }
    }
}

@Composable
fun DropDownUI(
    filterName: String,
    onTagSelected: (String, Int) -> Unit,
    options: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Button(onClick = {
            expanded = true
        }) {
            Row {
                Text(text = filterName)
                val iconPainter: Painter = painterResource(id = R.drawable.icon_dropdown)
                Icon(
                    painter = iconPainter,
                    contentDescription = "Dropdown Icon",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { tag ->
                DropdownMenuItem(onClick = {
                    onTagSelected(tag, Article.filterMap[filterName] ?: 0)
                    expanded = false
                }) {
                    Text(tag)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleList(articles: List<Article>, navController: NavController) {
    var refreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            val all = "all"
            // Start refreshing
            refreshing = true
            navController.navigate("${NavScreen.ArticleListScreen.route}/$all")
        }
    )

    Box(
        modifier = Modifier
            .padding(bottom = 30.dp)
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
    ) {
        if (articles.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),  // This already implies it will fill the max width and height
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally  // This will center the content horizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.ic_app_icon),
                    contentDescription = "App Icon",
                    modifier = Modifier
                        .size(100.dp),
                    contentScale = ContentScale.Crop
                )
                Text(text = "Oops! No articles found.\nTry some filters there!")
            }
        } else {
            LazyColumn {
                itemsIndexed(articles) { index, article ->
                    ArticleItem(article) {
                        navController.navigate("${NavScreen.ArticleDetailScreen.route}/${article.id}")
                    }
                }
            }
        }
        /** PullRefreshIndicator to show during refreshing */
        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
fun ArticleItem(article: Article, onArticleClick: () -> Unit) {
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .clickable {
            onArticleClick()
        }) {
        Row {
            DisplayImageFromUrl(
                article.imageUrl
                    ?: "https://images.unsplash.com/photo-1584395630827-860eee694d7b?ixlib=rb-1.2.1&auto=format&fit=crop&w=700&q=80\n"
            )
            Column(
                modifier = Modifier.padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "${article.title} - ${article.id}",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = article.subtitle ?: "",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Start,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun DisplayImageFromUrl(imageUrl: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true).build(),
        contentDescription = "Loaded image",
        modifier = Modifier
            .width(80.dp)
            .height(100.dp),
        contentScale = ContentScale.Crop
    )
}