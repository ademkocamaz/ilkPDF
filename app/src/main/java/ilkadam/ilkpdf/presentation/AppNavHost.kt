package ilkadam.ilkpdf.presentation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ilkadam.ilkpdf.presentation.library.LibraryScreen
import ilkadam.ilkpdf.presentation.library.LibraryViewModel
import ilkadam.ilkpdf.presentation.reader.ReaderScreen
import ilkadam.ilkpdf.presentation.reader.ReaderScreenViewModel
import java.io.Serializable

sealed class Screen(
    val route: String
) {
    object LibraryScreen : Screen("library_screen")
    object ReaderScreen : Screen("reader_screen")
    object TestScreen : Screen("test_screen")
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Screen.LibraryScreen.route
) {
    val readerScreenViewModel: ReaderScreenViewModel = hiltViewModel()
    val libraryViewModel: LibraryViewModel = hiltViewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.LibraryScreen.route) {
            LibraryScreen(
                navController = navController,
                libraryViewModel = libraryViewModel,
                readerScreenViewModel = readerScreenViewModel
            )
        }

        composable(
            route = Screen.ReaderScreen.route,
        ) {
            ReaderScreen(
                navController = navController,
                readerScreenViewModel = readerScreenViewModel
            )
        }

        /*composable(route = Screen.TestScreen.route) {
            TestScreen()
        }*/
    }
}
