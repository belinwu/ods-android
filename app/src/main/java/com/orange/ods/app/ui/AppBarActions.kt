/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.app.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.orange.ods.app.R
import com.orange.ods.app.ui.AppBarAction.Companion.defaultActions
import com.orange.ods.app.ui.utilities.extension.isDarkModeEnabled
import com.orange.ods.compose.component.appbar.top.OdsTopAppBar
import com.orange.ods.compose.component.content.OdsComponentContent
import com.orange.ods.compose.component.textfield.search.OdsSearchTextField

enum class AppBarAction {
    Search, ChangeTheme, ChangeMode;

    companion object {
        val defaultActions = listOf(ChangeTheme, ChangeMode)
    }

    @Composable
    fun getOdsTopAppBarAction(onActionClick: (AppBarAction) -> Unit) = when (this) {
        ChangeTheme -> getChangeThemeAction(onActionClick)
        ChangeMode -> getChangeModeAction(onActionClick)
        Search -> getSearchAction(onActionClick)
    }
}

@Composable
fun getDefaultActions(onActionClick: (AppBarAction) -> Unit): List<OdsTopAppBar.ActionButton> =
    defaultActions.map { it.getOdsTopAppBarAction(onActionClick = onActionClick) }

@Composable
fun getHomeActions(onActionClick: (AppBarAction) -> Unit): List<OdsTopAppBar.ActionButton> =
    listOf(getSearchAction(onActionClick)) + getDefaultActions(onActionClick = onActionClick)

@Composable
fun getSearchFieldAction(onTextChange: (TextFieldValue) -> Unit): OdsComponentContent<Nothing> {
    return object : OdsComponentContent<Nothing>() {

        @Composable
        override fun Content(modifier: Modifier) {
            val focusRequester = remember { FocusRequester() }
            OdsSearchTextField(
                value = LocalAppBarManager.current.searchedText,
                onValueChange = onTextChange,
                placeholder = stringResource(id = R.string.search_text_field_hint),
                modifier = modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        }
    }
}

@Composable
private fun getSearchAction(onClick: (AppBarAction) -> Unit) = OdsTopAppBar.ActionButton(
    onClick = { onClick(AppBarAction.Search) },
    painter = painterResource(id = R.drawable.ic_search),
    contentDescription = stringResource(id = R.string.search_content_description)
)

@Composable
private fun getChangeThemeAction(onClick: (AppBarAction) -> Unit) = OdsTopAppBar.ActionButton(
    onClick = { onClick(AppBarAction.ChangeTheme) },
    painter = painterResource(id = R.drawable.ic_palette),
    contentDescription = stringResource(id = R.string.top_app_bar_action_change_theme_desc)
)

@Composable
private fun getChangeModeAction(onClick: (AppBarAction) -> Unit): OdsTopAppBar.ActionButton {
    val configuration = LocalConfiguration.current

    val painterRes = if (configuration.isDarkModeEnabled) R.drawable.ic_ui_light_mode else R.drawable.ic_ui_dark_mode
    val iconDesc =
        if (configuration.isDarkModeEnabled) R.string.top_app_bar_action_change_mode_to_light_desc else R.string.top_app_bar_action_change_mode_to_dark_desc

    return OdsTopAppBar.ActionButton(
        onClick = { onClick(AppBarAction.ChangeMode) },
        painter = painterResource(id = painterRes),
        contentDescription = stringResource(id = iconDesc)
    )
}
