/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.demo.ui.components.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orange.ods.compose.component.button.OdsTextButton
import com.orange.ods.compose.theme.OdsDisplayAppearance
import com.orange.ods.demo.R
import com.orange.ods.demo.ui.utils.Subtitle
import com.orange.ods.demo.ui.utils.Title

@Composable
fun TextButtons() {
    Title(R.string.component_buttons_text_title)

    Subtitle(R.string.component_buttons_text_subtitle_default)
    OdsTextButton(modifier = Modifier.fullWidthButton(), text = stringResource(R.string.component_button_enabled), onClick = {})
    OdsTextButton(modifier = Modifier.fullWidthButton(false), text = stringResource(R.string.component_button_disabled), onClick = {}, enabled = false)

    OdsTextButton(modifier = Modifier.fullWidthButton(), text = stringResource(R.string.component_button_enabled), onClick = {}, iconRes = R.drawable.ic_search)
    OdsTextButton(
        modifier = Modifier.fullWidthButton(false),
        text = stringResource(R.string.component_button_disabled),
        onClick = {},
        enabled = false,
        iconRes = R.drawable.ic_search
    )

    Subtitle(R.string.component_buttons_text_subtitle_primary)
    OdsTextButton(modifier = Modifier.fullWidthButton(), text = stringResource(R.string.component_button_enabled), onClick = {}, hasPrimaryColor = true)
    OdsTextButton(modifier = Modifier.fullWidthButton(false), text = stringResource(R.string.component_button_disabled), onClick = {}, enabled = false, hasPrimaryColor = true)

    OdsTextButton(
        modifier = Modifier.fullWidthButton(),
        text = stringResource(R.string.component_button_enabled),
        onClick = {},
        iconRes = R.drawable.ic_search,
        hasPrimaryColor = true
    )
    OdsTextButton(
        modifier = Modifier.fullWidthButton(false),
        text = stringResource(R.string.component_button_disabled),
        onClick = {},
        enabled = false,
        iconRes = R.drawable.ic_search,
        hasPrimaryColor = true
    )

    LightSurface {
        TextButtonsFullWidthAppearanceForced(OdsDisplayAppearance.ON_LIGHT)
    }
    DarkSurface {
        TextButtonsFullWidthAppearanceForced(OdsDisplayAppearance.ON_DARK)
    }
}

@Composable
private fun TextButtonsFullWidthAppearanceForced(displayAppearance: OdsDisplayAppearance) {
    OdsTextButton(modifier = Modifier.fullWidthButton(), text = stringResource(R.string.component_button_enabled), onClick = {}, displayAppearance = displayAppearance)
    OdsTextButton(
        modifier = Modifier.fullWidthButton(false),
        text = stringResource(R.string.component_button_disabled),
        onClick = {},
        enabled = false,
        displayAppearance = displayAppearance
    )
}