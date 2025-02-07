package com.robosoft.newsapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.robosoft.newsapplication.navigation.Navigation
import com.robosoft.newsapplication.network.data.repo.ArticleRepository
import com.robosoft.newsapplication.network.data.util.ResponseHelper
import com.robosoft.newsapplication.ui.theme.KotlinProjectTheme

class MainActivity : ComponentActivity() {

    init {
        ArticleRepository.parseJson(ResponseHelper.jsonResponse)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinProjectTheme {
                Navigation()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    Navigation()
}
