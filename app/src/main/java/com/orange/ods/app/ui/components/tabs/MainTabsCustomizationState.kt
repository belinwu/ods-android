/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.app.ui.components.tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.orange.ods.app.ui.utilities.NavigationItem

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun rememberMainTabsCustomizationState(
    bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    tabsCount: MutableState<Int>,
    pagerState: PagerState = rememberPagerState(),
    selectedTabsIconPosition: MutableState<MainTabsCustomizationState.TabsIconPosition> = rememberSaveable { mutableStateOf(MainTabsCustomizationState.TabsIconPosition.Top) },
    tabIconEnabled: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) },
    tabTextEnabled: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) }
) =
    remember(bottomSheetScaffoldState, pagerState, tabsCount, selectedTabsIconPosition, tabIconEnabled, tabTextEnabled) {
        MainTabsCustomizationState(bottomSheetScaffoldState, pagerState, tabsCount, selectedTabsIconPosition, tabIconEnabled, tabTextEnabled)
    }

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
class MainTabsCustomizationState(
    val bottomSheetScaffoldState: BottomSheetScaffoldState,
    val pagerState: PagerState,
    val tabsCount: MutableState<Int>,
    val tabsIconPosition: MutableState<TabsIconPosition>,
    val tabIconEnabled: MutableState<Boolean>,
    val tabTextEnabled: MutableState<Boolean>
) {
    enum class TabsIconPosition {
        Leading, Top
    }

    private val availableTabs = NavigationItem.values().toList()

    val isTabTextCustomizationEnabled: Boolean
        get() = tabIconEnabled.value

    val isTabIconCustomizationEnabled: Boolean
        get() = tabTextEnabled.value

    val isTabsIconPositionEnabled: Boolean
        get() = isTabIconCustomizationEnabled && isTabTextCustomizationEnabled

    val tabs: List<NavigationItem>
        get() = availableTabs.take(tabsCount.value.coerceAtLeast(0))
}