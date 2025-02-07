package com.robosoft.newsapplication.navigation

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.robosoft.newsapplication.component.ArticleDetailScreen
import com.robosoft.newsapplication.component.ArticleListScreen
import com.robosoft.newsapplication.network.data.repo.ArticleRepository

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val listState = rememberLazyListState()
    val defaultTag = "all"
    NavHost(navController, startDestination = NavScreen.ArticleListScreen.route) {

        // Route without any arguments
        composable(NavScreen.ArticleListScreen.route) {
            ArticleListScreen(ArticleRepository.getArticles(defaultTag), navController, listState)
        }

        // Route with one argument (tag)
        composable(
            NavScreen.ArticleListScreen.routeWithOneArgument,
            arguments = listOf(navArgument(NavScreen.ArticleListScreen.tagArg) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            ArticleListScreen(
                ArticleRepository.getArticles(
                    backStackEntry.arguments?.getString(NavScreen.ArticleListScreen.tagArg)
                        ?: defaultTag
                ),
                navController,
                listState
            )
        }

        // Route with two arguments (tag and who)
        composable(
            NavScreen.ArticleListScreen.routeWithTwoArguments,
            arguments = listOf(
                navArgument(NavScreen.ArticleListScreen.tagArg) { type = NavType.StringType },
                navArgument(NavScreen.ArticleListScreen.whoArg) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            ArticleListScreen(
                ArticleRepository.filterArticlesByTag(
                    backStackEntry.arguments?.getString(NavScreen.ArticleListScreen.whoArg)?.toInt()
                        ?: 0,
                    backStackEntry.arguments?.getString(NavScreen.ArticleListScreen.tagArg)
                        ?: defaultTag
                ),
                navController,
                listState
            )
        }

        // Article detail route
        composable(
            NavScreen.ArticleDetailScreen.routeWithOneArgument,
            arguments = listOf(navArgument(NavScreen.ArticleDetailScreen.articleIdArg) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            ArticleDetailScreen(
                backStackEntry.arguments?.getString(NavScreen.ArticleDetailScreen.articleIdArg),
                navController
            )
        }
    }
}

