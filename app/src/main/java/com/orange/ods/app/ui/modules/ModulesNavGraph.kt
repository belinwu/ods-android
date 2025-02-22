/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.app.ui.modules

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.orange.ods.app.ui.modules.ModuleDemoDestinations.AboutCustomizationRoute
import com.orange.ods.app.ui.modules.about.AboutCustomizationScreen
import com.orange.ods.app.ui.modules.about.AboutCustomizationViewModel

/**
 * Modules demo destinations.
 */
object ModuleDemoDestinations {
    const val AboutCustomizationRoute = "module/about/customization"
}

fun NavGraphBuilder.addModulesGraph(navigateToAboutDemo: () -> Unit) {
    composable(
        route = AboutCustomizationRoute
    ) { _ ->
        val viewModelStoreOwner = LocalContext.current as ViewModelStoreOwner
        val viewModel = viewModel<AboutCustomizationViewModel>(viewModelStoreOwner)
        AboutCustomizationScreen(navigateToAboutDemo = navigateToAboutDemo, viewModel = viewModel)
    }
}
