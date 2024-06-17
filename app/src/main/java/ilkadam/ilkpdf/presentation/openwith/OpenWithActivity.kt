package ilkadam.ilkpdf.presentation.openwith

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import ilkadam.ilkpdf.presentation.openwith.OpenWithScreen
import ilkadam.ilkpdf.presentation.reader.ReaderScreen
import ilkadam.ilkpdf.ui.theme.IlkPDFTheme

@AndroidEntryPoint
class OpenWithActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val uri = intent.data
        /*this.grantUriPermission(this.packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        this.contentResolver.takePersistableUriPermission(
            uri!!,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )*/
        setContent {
            IlkPDFTheme {
                if (uri != null) {
                    OpenWithScreen(uri)
                }
            }
        }
    }
}

