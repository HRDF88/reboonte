package com.openclassrooms.rebonnte.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.openclassrooms.rebonnte.R
import com.openclassrooms.rebonnte.currentRoute
import com.openclassrooms.rebonnte.ui.addAisle.AddAisleScreen
import com.openclassrooms.rebonnte.ui.addMedicine.AddMedicineScreen
import com.openclassrooms.rebonnte.ui.aisle.AisleScreen
import com.openclassrooms.rebonnte.ui.aisle.AisleViewModel
import com.openclassrooms.rebonnte.ui.medicine.MedicineScreen
import com.openclassrooms.rebonnte.ui.medicine.MedicineViewModel
import com.openclassrooms.rebonnte.ui.theme.RebonnteTheme
import com.openclassrooms.rebonnte.utils.accessibility.AccessibilityAnnouncer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val medicineViewModel: MedicineViewModel = hiltViewModel()
    val aisleViewModel: AisleViewModel = hiltViewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route
    val context = LocalContext.current

    var showLogoutDialog by remember { mutableStateOf(false) }

    //Accessibility
    val addContentDescription = when (route) {
        "medicine" -> stringResource(R.string.content_desc_add_medicine)
        "aisle" -> stringResource(R.string.content_desc_add_aisle)
        else -> stringResource(R.string.content_desc_add_medicine)
    }


    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text(stringResource(id = R.string.logout_title)) },
            text = { Text(stringResource(id = R.string.logout_message)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        medicineViewModel.logOut(context)
                    }
                ) {
                    Text(stringResource(id = R.string.yes))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog = false }
                ) {
                    Text(stringResource(id = R.string.no))
                }
            }
        )
    }
    RebonnteTheme {
        when (route) {
            "addAisle" -> {
                AddAisleScreen(navController = navController, viewModel = hiltViewModel())
            }

            "addMedicine" -> {
                AddMedicineScreen(navController = navController, viewModel = hiltViewModel())
            }

            else -> {
                Scaffold(
                    topBar = {
                        var isSearchActive by rememberSaveable { mutableStateOf(false) }
                        var searchQuery by remember { mutableStateOf("") }

                        Column(verticalArrangement = Arrangement.spacedBy((-1).dp)) {
                            TopAppBar(
                                title = {
                                    if (route == "aisle") Text(text = stringResource(R.string.aisle)) else Text(
                                        text = stringResource(R.string.medicine)
                                    )
                                },
                                actions = {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ExitToApp,
                                        contentDescription = stringResource(R.string.content_desc_logout),
                                        modifier = Modifier.clickable {
                                            showLogoutDialog = true
                                            AccessibilityAnnouncer.announce(
                                                context,
                                                context.getString(R.string.logout_message)
                                            )
                                        }
                                    )
                                    var expanded by remember { mutableStateOf(false) }
                                    if (currentRoute(navController) == "medicine") {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .padding(end = 8.dp)
                                                .background(MaterialTheme.colorScheme.surface)
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                        ) {
                                            Box {
                                                IconButton(onClick = { expanded = true }) {
                                                    Icon(
                                                        Icons.Default.MoreVert,
                                                        contentDescription = stringResource(R.string.content_desc_more_options)
                                                    )
                                                }
                                                DropdownMenu(
                                                    expanded = expanded,
                                                    onDismissRequest = { expanded = false },
                                                    offset = DpOffset(x = 0.dp, y = 0.dp)
                                                ) {
                                                    DropdownMenuItem(
                                                        onClick = {
                                                            medicineViewModel.loadAllMedicine()
                                                            AccessibilityAnnouncer.announce(
                                                                context,
                                                                context.getString(R.string.sort_by_none)
                                                            )
                                                            expanded = false
                                                        },
                                                        text = {
                                                            Text(
                                                                stringResource(R.string.sort_by_none)
                                                            )
                                                        }
                                                    )
                                                    DropdownMenuItem(
                                                        onClick = {
                                                            medicineViewModel.sortByName()
                                                            AccessibilityAnnouncer.announce(
                                                                context,
                                                                context.getString(R.string.sort_by_name)
                                                            )
                                                            expanded = false
                                                        },
                                                        text = {
                                                            Text(
                                                                stringResource(R.string.sort_by_name)
                                                            )
                                                        }
                                                    )
                                                    DropdownMenuItem(
                                                        onClick = {
                                                            medicineViewModel.sortByStock()
                                                            AccessibilityAnnouncer.announce(
                                                                context,
                                                                context.getString(R.string.sort_by_stock)
                                                            )
                                                            expanded = false
                                                        },
                                                        text = {
                                                            Text(
                                                                stringResource(R.string.sort_by_stock)
                                                            )
                                                        }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            )
                            if (currentRoute(navController) == "medicine") {
                                EmbeddedSearchBar(
                                    query = searchQuery,
                                    onQueryChange = {
                                        medicineViewModel.filterByName(it)
                                        searchQuery = it
                                    },
                                    isSearchActive = isSearchActive,
                                    onActiveChanged = { isSearchActive = it }
                                )
                            }
                        }

                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        Icons.Default.Home,
                                        contentDescription = stringResource(R.string.content_desc_home)
                                    )
                                },
                                label = { Text(stringResource(R.string.aisle)) },
                                selected = currentRoute(navController) == "aisle",
                                onClick = { navController.navigate("aisle") }
                            )
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        Icons.AutoMirrored.Filled.List,
                                        contentDescription = stringResource(R.string.content_desc_medicine)
                                    )
                                },
                                label = { Text(stringResource(R.string.medicine)) },
                                selected = currentRoute(navController) == "medicine",
                                onClick = { navController.navigate("medicine") }
                            )
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            if (route == "medicine") {
                                navController.navigate(route = "addMedicine")
                            } else if (route == "aisle") {
                                navController.navigate(route = "addAisle")
                            }
                        }) {
                            Icon(Icons.Default.Add, contentDescription = addContentDescription)
                        }
                    }
                ) {
                    NavHost(
                        modifier = Modifier.padding(it),
                        navController = navController,
                        startDestination = "aisle"
                    ) {
                        composable("aisle") { AisleScreen(aisleViewModel) }
                        composable("medicine") { MedicineScreen(medicineViewModel) }
                        composable("addAisle") {
                            AddAisleScreen(
                                navController = navController,
                                viewModel = hiltViewModel()
                            )
                        }
                        composable(route = "addMedicine") {
                            AddMedicineScreen(
                                navController = navController,
                                viewModel = hiltViewModel()
                            )
                        }
                    }
                }
            }
        }
    }
}
