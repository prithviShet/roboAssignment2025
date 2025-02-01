package com.robosoft.newsapplication.navigation

import androidx.compose.runtime.Immutable

@Immutable
sealed class NavScreen(val route: String) {
    data object ArticleListScreen : NavScreen("articleList") {
        const val routeWithOneArgument = "articleList/{tag}"
        const val routeWithTwoArguments = "articleList/{tag}/{who}"
        const val tagArg = "tag"
        const val whoArg = "who"
    }

    data object ArticleDetailScreen : NavScreen("articleDetail") {
        const val routeWithOneArgument = "articleDetail/{articleId}"
        const val articleIdArg = "articleId"
    }
}