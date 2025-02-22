/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.compose.component.chip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ChipDefaults.LeadingIconOpacity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orange.ods.R
import com.orange.ods.compose.component.OdsComposable
import com.orange.ods.compose.component.utilities.DisabledInteractionSource
import com.orange.ods.compose.component.utilities.Preview
import com.orange.ods.compose.component.utilities.UiModePreviews
import com.orange.ods.compose.theme.OdsTheme
import com.orange.ods.theme.OdsComponentsConfiguration

/**
 * <a href="https://system.design.orange.com/0c1af118d/p/81aa91-chips/b/13c40e" target="_blank">ODS Chips</a>.
 *
 * Chips are small components containing a number of elements that represent a calendar event or contact.
 * Note that [OdsFilterChip] is displayed outlined or filled according to your [OdsTheme] component configuration, outlined by default.
 *
 * Use Accompanist's [Flow Layouts](https://google.github.io/accompanist/flowlayout/) to wrap chips to a new line.
 *
 * @param text Text to be displayed into the chip.
 * @param onClick Callback called on chip click.
 * @param modifier [Modifier] to be applied to the chip.
 * @param enabled Controls the enabled state of the chip. When `false`, this chip will not respond to user input. It also appears visually
 * disabled and is disabled to accessibility services.
 * @param selected Controls the selected state of the chip. When `true`, the chip is highlighted.
 * @param leadingAvatar [OdsChip.LeadingAvatar] to be displayed in a circle shape at the start of the chip, preceding the content text.
 */
@Composable
@OdsComposable
fun OdsFilterChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selected: Boolean = false,
    leadingAvatar: OdsChip.LeadingAvatar? = null
) {
    OdsFilterChip(
        text = text,
        onClick = onClick,
        modifier = modifier,
        outlined = OdsTheme.componentsConfiguration.chipStyle == OdsComponentsConfiguration.ComponentStyle.Outlined,
        enabled = enabled,
        selected = selected,
        leadingAvatar = leadingAvatar
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@OdsComposable
private fun OdsFilterChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    outlined: Boolean = true,
    enabled: Boolean = true,
    selected: Boolean = false,
    leadingAvatar: OdsChip.LeadingAvatar? = null,
) {
    val emptyAction = {}

    FilterChip(
        selected = selected,
        onClick = if (enabled) onClick else emptyAction,
        modifier = modifier,
        border = if (outlined) {
            BorderStroke(1.dp, OdsTheme.colors.onSurface.copy(alpha = OdsChipDefaults.SurfaceOverlayOpacity))
        } else null,
        enabled = enabled,
        interactionSource = if (enabled) remember { MutableInteractionSource() } else remember { DisabledInteractionSource() },
        colors = odsFilterChipColors(outlined = outlined),
        leadingIcon = when {
            leadingAvatar != null -> {
                {
                    Box(contentAlignment = Alignment.Center) {
                        leadingAvatar.Content(enabled)
                        if (selected) {
                            OdsImageCircleShape(
                                painter = ColorPainter(color = Color.Black),
                                contentDescription = null,
                                circleSize = dimensionResource(id = R.dimen.icon_size),
                                alpha = LeadingIconOpacity
                            )
                            OdsChipSelectedIcon(tint = OdsTheme.colors.onSurface)
                        }
                    }
                }
            }
            selected -> {
                { OdsChipSelectedIcon() }
            }
            else -> null
        }
    ) {
        Text(
            text = text,
            style = OdsTheme.typography.body2
        ) // Don't use an `OdsText` here cause the color of the chip content is already managed by `OdsChipDefaults.filterChipColors()`
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun odsFilterChipColors(outlined: Boolean) = if (outlined) OdsChipDefaults.outlinedFilterChipColors() else OdsChipDefaults.filterChipColors()

@Composable
private fun OdsChipSelectedIcon(tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)) {
    Icon(
        modifier = Modifier.size(dimensionResource(id = R.dimen.chip_icon_size)),
        painter = painterResource(id = R.drawable.ic_check),
        contentDescription = stringResource(id = R.string.state_selected),
        tint = tint
    )
}

@Composable
private fun OdsImageCircleShape(
    painter: Painter,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    circleSize: Dp = dimensionResource(id = R.dimen.avatar_size),
    alpha: Float = DefaultAlpha
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(circleSize)
            .clip(CircleShape),
        alpha = alpha
    )
}

@UiModePreviews.Chip
@Composable
private fun PreviewOdsFilterChip() = Preview {
    var selected by remember { mutableStateOf(false) }
    OdsFilterChip(
        text = "Text",
        selected = selected,
        onClick = { selected = !selected },
        leadingAvatar = OdsChip.LeadingAvatar(painterResource(id = R.drawable.ic_check), "selected"),
    )
}