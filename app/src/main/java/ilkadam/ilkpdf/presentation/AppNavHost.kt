package ilkadam.ilkpdf.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ilkadam.ilkpdf.presentation.library.LibraryScreen
import ilkadam.ilkpdf.presentation.library.LibraryViewModel
import ilkadam.ilkpdf.presentation.reader.ReaderScreen
import ilkadam.ilkpdf.presentation.reader.ReaderScreenViewModel

sealed class Screen(
    val route: String
) {
    data object LibraryScreen : Screen("library_screen")
    data object ReaderScreen : Screen("reader_screen")
    data object TestScreen : Screen("test_screen")
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
