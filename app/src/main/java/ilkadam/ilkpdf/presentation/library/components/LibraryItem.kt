package ilkadam.ilkpdf.presentation.library.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import ilkadam.ilkpdf.core.domain.Document
import ilkadam.ilkpdf.presentation.Screen
import ilkadam.ilkpdf.presentation.StringUtil
import ilkadam.ilkpdf.presentation.reader.ReaderScreenViewModel

@Composable
fun LibraryItem(
    navController: NavController,
    document: Document,
    readerScreenViewModel: ReaderScreenViewModel
) {

    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.clickable {
                readerScreenViewModel.uri.value = Uri.parse(document.url)
                navController.navigate(
                    route = Screen.ReaderScreen.route
                )
                /*navController.navigate(
                    route = Screen.ReaderScreen.route + "/{documentUri}"
                        .replace(
                            oldValue = "{documentUri}",
                            newValue = Uri.encode(document.url)
                        )
                )*/


            },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            AsyncImage(
                model = document.thumbnail,
                modifier = Modifier
                    .size(100.dp)
                    .padding(5.dp),
                contentDescription = document.name
            )
            Column {
                Text(text = document.name)
                Text(text = StringUtil.readableFileSize(document.size))
            }
        }
    }

}