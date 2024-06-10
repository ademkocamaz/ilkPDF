package ilkadam.ilkpdf.presentation.library

import android.Manifest
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityOptionsCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import ilkadam.ilkpdf.presentation.Screen
import ilkadam.ilkpdf.presentation.library.components.LibraryItem
import ilkadam.ilkpdf.presentation.reader.ReaderScreenViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LibraryScreen(
    navController: NavController,
    libraryViewModel: LibraryViewModel,
    readerScreenViewModel: ReaderScreenViewModel
) {
//    val uri = remember { mutableStateOf<Uri?>(null) }
    val documents = libraryViewModel.documents.observeAsState()
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
                    LibraryItem(
                        document = document,
                        navController = navController,
                        readerScreenViewModel = readerScreenViewModel
                    )
                }
            }
        }
    }


}