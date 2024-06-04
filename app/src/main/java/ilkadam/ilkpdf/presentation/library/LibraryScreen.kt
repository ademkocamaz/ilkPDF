package ilkadam.ilkpdf.presentation.library

import android.app.Instrumentation.ActivityResult
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ilkadam.ilkpdf.presentation.library.components.LibraryItem

@Composable
fun LibraryScreen() {
    val libraryViewModel: LibraryViewModel = viewModel()
    val uri = remember { mutableStateOf<Uri?>(null) }
    val documents = libraryViewModel.documents.observeAsState()

    LaunchedEffect(key1 = Unit) {
        libraryViewModel.loadDocuments()
    }


    val pickPDFLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = {
            uri.value = it
        }
    )

    uri.value?.let {
        libraryViewModel.addDocument(it)
    }

    Scaffold(
        topBar = {
            Text(text = "Count:"+documents.value?.size.toString())
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    pickPDFLauncher.launch(arrayOf("application/pdf"))
                }) {
                Text(text = "Ekle")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            documents.value?.let { documents ->
                items(documents) { document ->
                    LibraryItem(document = document)
                }
            }
        }
    }


}