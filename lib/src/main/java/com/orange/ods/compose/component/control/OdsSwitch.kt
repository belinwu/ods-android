/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.compose.component.control

import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import com.orange.ods.compose.component.OdsComponentApi
import com.orange.ods.compose.component.utilities.Preview
import com.orange.ods.compose.component.utilities.UiModePreviews
import com.orange.ods.compose.theme.OdsPrimaryRippleTheme
import com.orange.ods.compose.theme.OdsTheme
import com.orange.ods.utilities.extension.disabled

/**
 * <a href="https://system.design.orange.com/0c1af118d/p/14638a-selection-controls/b/352c00" class="external" target="_blank">ODS switch</a>.
 *
 * Switches toggle the state of a single item on or off.
 *
 * @param checked whether or not this component is checked
 * @param onCheckedChange callback to be invoked when Switch is being clicked,
 * therefore the change of checked state is requested.  If null, then this is passive
 * and relies entirely on a higher-level component to control the "checked" state.
 * @param modifier Modifier to be applied to the switch layout
 * @param enabled whether the component is enabled or grayed out
 */
@Composable
@OdsComponentApi
fun OdsSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    CompositionLocalProvider(LocalRippleTheme provides OdsPrimaryRippleTheme) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = modifier,
            enabled = enabled,
            colors = OdsSwitchDefaults.colors(uncheckedThumbColor = OdsTheme.colors.switch.uncheckedThumb)
        )
    }
}

private object OdsSwitchDefaults {

    @Composable
    fun colors(
        checkedThumbColor: Color = OdsTheme.colors.secondaryVariant,
        checkedTrackColor: Color = checkedThumbColor,
        uncheckedThumbColor: Color = OdsTheme.colors.surface,
        uncheckedTrackColor: Color = OdsTheme.colors.onSurface,
        disabledCheckedThumbColor: Color = checkedThumbColor.disabled.compositeOver(OdsTheme.colors.surface),
        disabledCheckedTrackColor: Color = checkedTrackColor.disabled.compositeOver(OdsTheme.colors.surface),
        disabledUncheckedThumbColor: Color = uncheckedThumbColor.disabled.compositeOver(OdsTheme.colors.surface),
        disabledUncheckedTrackColor: Color = uncheckedTrackColor.disabled.compositeOver(OdsTheme.colors.surface)
    ) = SwitchDefaults.colors(
        checkedThumbColor = checkedThumbColor,
        checkedTrackColor = checkedTrackColor,
        uncheckedThumbColor = uncheckedThumbColor,
        uncheckedTrackColor = uncheckedTrackColor,
        disabledCheckedThumbColor = disabledCheckedThumbColor,
        disabledCheckedTrackColor = disabledCheckedTrackColor,
        disabledUncheckedThumbColor = disabledUncheckedThumbColor,
        disabledUncheckedTrackColor = disabledUncheckedTrackColor
    )

}

@UiModePreviews.Default
@Composable
private fun PreviewOdsSwitch() = Preview {
    var checked by remember { mutableStateOf(false) }
    OdsSwitch(
        checked = checked,
        onCheckedChange = { checked = it }
    )
}
