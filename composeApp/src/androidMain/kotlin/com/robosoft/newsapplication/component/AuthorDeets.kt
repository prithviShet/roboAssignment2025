import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.robosoft.newsapplication.network.data.model.Article

@Composable
fun AuthorTextWithPopup(article: Article) {
    var showDialog by remember { mutableStateOf(false) }

    Text(
        text = "Author: ${article.author}",
        style = MaterialTheme.typography.h5,
        color = MaterialTheme.colors.onPrimary,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .clickable { showDialog = true }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Author Details") },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    Arrangement.SpaceBetween
                ) {
                    AsyncImage(
                        model = article.authorImage,
                        contentDescription = "Author Image",
                        modifier = Modifier
                            .size(100.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(text = "Name: ${article.author}")
                        Text(
                            text = "Description: ${article.authorDesc}",
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}
