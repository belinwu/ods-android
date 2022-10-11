/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.demo.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.orange.ods.compose.component.bottomnavigation.OdsBottomNavigation
import com.orange.ods.compose.component.bottomnavigation.OdsBottomNavigationItem
import com.orange.ods.demo.R
import com.orange.ods.demo.ui.about.AboutScreen
import com.orange.ods.demo.ui.components.ComponentsScreen
import com.orange.ods.demo.ui.guidelines.GuidelinesScreen
import com.orange.ods.demo.ui.modules.ModulesScreen

@Composable
fun MainBottomNavigation(items: Array<BottomNavigationSections>, currentRoute: String, navigateToRoute: (String) -> Unit) {
    OdsBottomNavigation {
        items.forEach { item ->
            OdsBottomNavigationItem(
                icon = { Icon(painter = painterResource(id = item.iconRes), contentDescription = null) },
                label = stringResource(id = item.titleRes),
                selected = currentRoute == item.route,
                onClick = { navigateToRoute(item.route) }
            )
        }
    }
}

@ExperimentalMaterialApi
fun NavGraphBuilder.addBottomNavigationGraph(navigateToElement: (String, Long?, NavBackStackEntry) -> Unit) {
    composable(BottomNavigationSections.Guidelines.route) { from ->
        LocalMainTabsManager.current.clearTopAppBarTabs()
        GuidelinesScreen(onGuidelineClick = { route -> navigateToElement(route, null, from) })
    }
    composable(BottomNavigationSections.Components.route) { from ->
        LocalMainTabsManager.current.clearTopAppBarTabs()
        ComponentsScreen(onComponentClick = { id -> navigateToElement(MainDestinations.ComponentDetailRoute, id, from) })
    }
    composable(BottomNavigationSections.Modules.route) {
        LocalMainTabsManager.current.clearTopAppBarTabs()
        ModulesScreen()
    }
    composable(BottomNavigationSections.About.route) { from ->
        LocalMainTabsManager.current.clearTopAppBarTabs()
        AboutScreen(onAboutItemClick = { id -> navigateToElement(MainDestinations.AboutItemDetailRoute, id, from) })
    }
}

enum class BottomNavigationSections(
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int,
    val route: String
) {
    Guidelines(R.string.navigation_item_guidelines, R.drawable.ic_guideline_dna, "main/guidelines"),
    Components(R.string.navigation_item_components, R.drawable.ic_component_atom, "main/components"),
    Modules(R.string.navigation_item_modules, R.drawable.ic_module_molecule, "main/modules"),
    About(R.string.navigation_item_about, R.drawable.ic_info, "main/about");
}