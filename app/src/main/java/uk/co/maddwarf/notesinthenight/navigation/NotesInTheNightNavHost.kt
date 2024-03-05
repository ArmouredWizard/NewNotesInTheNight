package uk.co.maddwarf.notesinthenight.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import uk.co.maddwarf.notesinthenight.home.HomeDestination
import uk.co.maddwarf.notesinthenight.home.HomeScreen
import uk.co.maddwarf.notesinthenight.ui.screens.ability.AbilityListDestination
import uk.co.maddwarf.notesinthenight.ui.screens.ability.AbilityListScreen
import uk.co.maddwarf.notesinthenight.ui.screens.contact.ContactListDestination
import uk.co.maddwarf.notesinthenight.ui.screens.contact.ContactListScreen
import uk.co.maddwarf.notesinthenight.ui.screens.crew.CrewDetailsDestination
import uk.co.maddwarf.notesinthenight.ui.screens.crew.CrewDetailsScreen
import uk.co.maddwarf.notesinthenight.ui.screens.crew.CrewEditDestination
import uk.co.maddwarf.notesinthenight.ui.screens.crew.CrewEditScreen
import uk.co.maddwarf.notesinthenight.ui.screens.crew.CrewEntryDestination
import uk.co.maddwarf.notesinthenight.ui.screens.crew.CrewEntryScreen
import uk.co.maddwarf.notesinthenight.ui.screens.crew.CrewListDestination
import uk.co.maddwarf.notesinthenight.ui.screens.crew.CrewListScreen
import uk.co.maddwarf.notesinthenight.ui.screens.notes.NoteEditDestination
import uk.co.maddwarf.notesinthenight.ui.screens.notes.NoteEditScreen
import uk.co.maddwarf.notesinthenight.ui.screens.notes.NoteEntryDestination
import uk.co.maddwarf.notesinthenight.ui.screens.notes.NoteEntryScreen
import uk.co.maddwarf.notesinthenight.ui.screens.notes.NotesListDestination
import uk.co.maddwarf.notesinthenight.ui.screens.notes.NotesListScreen
import uk.co.maddwarf.notesinthenight.ui.screens.scoundrel.ScoundrelDetailsDestination
import uk.co.maddwarf.notesinthenight.ui.screens.scoundrel.ScoundrelDetailsScreen
import uk.co.maddwarf.notesinthenight.ui.screens.scoundrel.ScoundrelEditDestination
import uk.co.maddwarf.notesinthenight.ui.screens.scoundrel.ScoundrelEditScreen
import uk.co.maddwarf.notesinthenight.ui.screens.scoundrel.ScoundrelEntryDestination
import uk.co.maddwarf.notesinthenight.ui.screens.scoundrel.ScoundrelEntryScreen
import uk.co.maddwarf.notesinthenight.ui.screens.scoundrel.ScoundrelListDestination
import uk.co.maddwarf.notesinthenight.ui.screens.scoundrel.ScoundrelListScreen

@Composable
fun NotesInTheNightNavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route
    ) {
        fun navigateToHome() = navController.navigate(HomeDestination.route)
        fun navigateToScoundrelList() = navController.navigate(ScoundrelListDestination.route)
        fun navigateToScoundrelEntry() = navController.navigate(ScoundrelEntryDestination.route)
        fun navigateToAbilitiesList() = navController.navigate(AbilityListDestination.route)
        fun navigateToCrewList() = navController.navigate(CrewListDestination.route)
        fun navigateToCrewEntry() = navController.navigate(CrewEntryDestination.route)
        fun navigateToContactList() = navController.navigate(ContactListDestination.route)
        fun navigateToGeneralNotesList() = navController.navigate(NotesListDestination.route)
        fun navigateToAddNoteScreen() = navController.navigate(NoteEntryDestination.route)

        composable(route = HomeDestination.route) {
            HomeScreen(
                modifier = Modifier,
                navigateToScoundrelList = { navigateToScoundrelList() },
                navigateToAbilitiesList = { navigateToAbilitiesList() },
                navigateToCrewList = { navigateToCrewList() },
                onNavigateUp = { navController.navigateUp() },
                navigateToContactList = { navigateToContactList() },
                navigateToGeneralNotesList = { navigateToGeneralNotesList() }
                //     navigateToSettings = {},//todo add Settings destination
            )
        }//end Home composable

        composable(route = ScoundrelListDestination.route) {
            ScoundrelListScreen(
                navigateToHome = { navigateToHome() },
                onNavigateUp = { navController.navigateUp() },
                navigateToScoundrelEntry = { navigateToScoundrelEntry() },
                navigateToScoundrelDetails = { navController.navigate("${ScoundrelDetailsDestination.route}/${it}") }
            )
        }

        composable(route = ScoundrelEntryDestination.route) {
            ScoundrelEntryScreen(
                navigateBack = { navController.popBackStack() },
                navigateToHome = { navigateToHome() },
                navigateToScoundrelList = { navigateToScoundrelList() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(
            route = ScoundrelDetailsDestination.routeWithArgs,
            arguments = listOf(
                navArgument(ScoundrelDetailsDestination.itemIdArg) {
                    type = NavType.IntType
                }
            )
        ) {
            ScoundrelDetailsScreen(
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateBack = { navController.popBackStack() },
                navigateToScoundrelEdit = { navController.navigate("${ScoundrelEditDestination.route}/${it}") },
                navigateToCrewDetails = { navController.navigate("${CrewDetailsDestination.route}/${it}") }
            )
        }//end ScoundrelDetailsScreen

        composable(route = ScoundrelEditDestination.routeWithArgs,
            arguments = listOf(
                navArgument(ScoundrelEditDestination.itemIdArg) {
                    type = NavType.IntType
                }
            )
        ) {
            ScoundrelEditScreen(
                navigateBack = { navController.popBackStack() },
                navigateToHome = { navigateToHome() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(route = CrewListDestination.route) {
            CrewListScreen(
                navigateToHome = { navigateToHome() },
                onNavigateUp = { navController.navigateUp() },
                navigateToCrewEntry = { navigateToCrewEntry() },
                navigateToCrewDetails = { navController.navigate("${CrewDetailsDestination.route}/${it}") }
            )
        }

        composable(route = CrewDetailsDestination.routeWithArgs,
            arguments = listOf(
                navArgument(CrewDetailsDestination.itemIdArg) {
                    type = NavType.IntType
                }
            )
        ) {
            CrewDetailsScreen(
                navigateBack = { navController.popBackStack() },
                navigateToHome = { navigateToHome() },
                navigateToCrewEdit = { navController.navigate("${CrewEditDestination.route}/${it}") }
            )
        }

        composable(route = CrewEntryDestination.route) {
            CrewEntryScreen(
                navigateBack = { navController.popBackStack() },
                navigateToHome = { navigateToHome() },
                //navigateToCrewList = { navigateToCrewList() },
                onNavigateUp = { navController.navigateUp() },
                navigateToCrewDetails = { navController.navigate("${CrewDetailsDestination.route}/${it}") }
            )
        }

        composable(route = CrewEditDestination.routeWithArgs,
            arguments = listOf(
                navArgument(CrewEditDestination.itemIdArg) {
                    type = NavType.IntType
                }
            )
        ) {
            CrewEditScreen(
                navigateBack = { navController.popBackStack() },
                navigateToHome = { navigateToHome() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(route = ContactListDestination.route) {
            ContactListScreen(
                navigateBack = { navController.popBackStack() },
                navigateToHome = { navigateToHome() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(route = AbilityListDestination.route) {
            AbilityListScreen(
                navigateToHome = { navigateToHome() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(route = NotesListDestination.route) {
            NotesListScreen(
                navigateToHome = { navigateToHome() },
                navigateToAddNoteScreen = { navigateToAddNoteScreen() },
                onNavigateUp = { navController.navigateUp() },
                navigateToNoteEdit = {  navController.navigate("${NoteEditDestination.route}/${it}")  }
            )
        }

        composable(route = NoteEntryDestination.route){
            NoteEntryScreen(
                navigateBack = { navController.popBackStack() },
                navigateToHome = { navigateToHome() },
                onNavigateUp = { navController.navigateUp() },
                canNavigateBack = true
            )
        }

        composable(route = NoteEditDestination.routeWithArgs,
            arguments = listOf(
                navArgument(NoteEditDestination.itemIdArg) {
                    type = NavType.IntType
                }
            )
            ){
            NoteEditScreen(
                navigateBack = { navController.popBackStack() },
                navigateToHome = { navigateToHome() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

    }//end navHost
}//end NotesInTheNightNavHost
