package ilkadam.ilkpdf.presentation.library

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ilkadam.ilkpdf.AdmobBanner
import ilkadam.ilkpdf.R
import ilkadam.ilkpdf.presentation.library.components.LibraryItem
import ilkadam.ilkpdf.presentation.reader.ReaderScreenViewModel

@Composable
fun LibraryScreen(
    navController: NavController,
    libraryViewModel: LibraryViewModel,
    readerScreenViewModel: ReaderScreenViewModel
) {
//    val uri = remember { mutableStateOf<Uri?>(null) }
    val documents = libraryViewModel.documents
    val context = LocalContext.current

    /*uri.value?.let {
        context.contentResolver.takePersistableUriPermission(it,Intent.FLAG_GRANT_READ_URI_PERMISSION)
        navController.navigate(
            route = Screen.ReaderScreen.route + "/{documentUri}"
                .replace(
                    oldValue = "{documentUri}",
                    newValue = Uri.encode(it.toString())
                )
        )
    }*/


    val pickPDFLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) {

            it?.let {
                libraryViewModel.addDocument(it)
                readerScreenViewModel.uri.value = it
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }

        }


    /*val requestPermission =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data.let {
                    it?.let {
                        context.contentResolver.takePersistableUriPermission(
                            it,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                        libraryViewModel.addDocument(it)
                        navController.navigate(
                            route = Screen.ReaderScreen.route + "/{documentUri}"
                                .replace(
                                    oldValue = "{documentUri}",
                                    newValue = Uri.encode(it.toString())
                                )
                        )
                        //uri.value = it
                    }

                }
            }

        }*/
    Scaffold(
        /*topBar = {
            Text(text = "Count:" + documents.value?.size.toString())
        },*/
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    /*val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "application/pdf"
                    }
                    requestPermission.launch(intent)*/

                    pickPDFLauncher.launch(arrayOf("application/pdf"))

                }) {
                Text(text = stringResource(R.string.add))
            }
        }
    ) { innerPadding ->
        if (documents.value.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(documents.value) { document ->
                    LibraryItem(
                        document = document,
                        navController = navController,
                        readerScreenViewModel = readerScreenViewModel,
                        libraryViewModel = libraryViewModel
                    )
                    AdmobBanner()

                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(R.string.empty_list), fontSize = 30.sp)
            }
        }


    }


}