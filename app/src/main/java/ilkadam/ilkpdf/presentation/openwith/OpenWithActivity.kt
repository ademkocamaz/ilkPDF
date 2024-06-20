package ilkadam.ilkpdf.presentation.openwith

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
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

