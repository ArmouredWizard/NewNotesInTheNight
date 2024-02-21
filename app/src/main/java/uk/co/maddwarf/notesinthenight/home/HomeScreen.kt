package uk.co.maddwarf.notesinthenight.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import uk.co.maddwarf.notesinthenight.NotesInTheNightTopAppBar
import uk.co.maddwarf.notesinthenight.R
import uk.co.maddwarf.notesinthenight.navigation.NavigationDestination
import uk.co.maddwarf.notesinthenight.ui.composables.MyButton

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    navigateToScoundrelList: () -> Unit,
    navigateToCrewList: () -> Unit,
    navigateToContactList: () -> Unit,
    navigateToAbilitiesList: () -> Unit,
    onNavigateUp: () -> Unit,
    navigateToGeneralNotesList: () -> Unit,
    //homeViewModel: HomeViewModel = hiltViewModel()
) {

    Scaffold(
        //   modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NotesInTheNightTopAppBar(
                title = stringResource(id = R.string.app_name),
                canNavigateBack = false,
                navigateUp = onNavigateUp,
                //  scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        HomeBody(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            navigateToScoundrelList = navigateToScoundrelList,
            navigateToCrewList = navigateToCrewList,
            navigateToContactList = navigateToContactList,
            navigateToAbilitiesList = navigateToAbilitiesList,
            navigateToGeneralNotesList = navigateToGeneralNotesList
        )
    }//end Scaffold
}//end HomeScreen

@Composable
fun HomeBody(
    modifier: Modifier,
    navigateToScoundrelList: () -> Unit,
    navigateToCrewList: () -> Unit,
    navigateToContactList: () -> Unit,
    navigateToAbilitiesList: () -> Unit,
    navigateToGeneralNotesList:()->Unit
) {

    Box(
        modifier = modifier
            .background(color = Color.LightGray)
            .paint(
                painterResource(id = R.drawable.cobbles),
                contentScale = ContentScale.FillBounds
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(.5f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            MyButton(
                text = "Scoundrels",
                onClick = navigateToScoundrelList,
            )
            MyButton(
                text = "Crews",
                onClick = navigateToCrewList,
            )
            MyButton(
                text = "Contacts",
                onClick = navigateToContactList,
            )
            MyButton(
                text = "Special Abilities",
                onClick = navigateToAbilitiesList,
            )
            MyButton(
                text = "General Notes",
                onClick = navigateToGeneralNotesList,
            )


            MyButton(
                text = "Next...",
                onClick = {}
            )

        }//end outer Column
    }//end outer box

}

