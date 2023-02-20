/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.compose.component.progressindicator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.orange.ods.R
import com.orange.ods.compose.component.OdsComponentApi
import com.orange.ods.compose.component.utilities.BasicPreviewParameterProvider
import com.orange.ods.compose.component.utilities.Preview
import com.orange.ods.compose.component.utilities.UiModePreviews
import com.orange.ods.compose.text.OdsTextCaption
import com.orange.ods.compose.theme.OdsTheme
import com.orange.ods.utilities.extension.orElse

/**
 * Progress indicators express an unspecified wait time or display the length of a process.
 *
 * @see androidx.compose.material.LinearProgressIndicator
 *
 * @param modifier The modifier applied to this progress indicator
 * @param progress The progress of this progress indicator, where 0.0 represents no progress and 1.0
 * represents full progress. Values outside of this range are coerced into the range. If set to `null`,
 * the progress indicator is indeterminate.
 */
@Composable
@OdsComponentApi
fun OdsLinearProgressIndicator(
    modifier: Modifier = Modifier,
    label: String? = null,
    currentValue: String? = null,
    iconContentDescription: String? = null,
    icon: Painter? = null,
    progress: Float? = null,
) {
    val progressIndicatorColor = OdsTheme.colors.primary
    Column(
        modifier = modifier
            .padding(horizontal = dimensionResource(id = R.dimen.spacing_m))
            .padding(top = dimensionResource(id = R.dimen.spacing_m))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_s))
        ) {
            icon?.let { painter ->
                Icon(
                    painter = painter, contentDescription = iconContentDescription,
                    modifier = Modifier
                        .align(alignment = Alignment.Bottom)
                        .padding(bottom = dimensionResource(id = R.dimen.spacing_xs)),
                    tint = OdsTheme.colors.onSurface
                )
            }
            if (label != null) {
                OdsTextCaption(
                    text = label,
                    modifier = Modifier
                        .padding(bottom = dimensionResource(id = R.dimen.spacing_s))
                )
            }
        }
        progress?.let {
            LinearProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth(), color = progressIndicatorColor)

            if (currentValue != null) {
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    OdsTextCaption(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.spacing_xs)),
                        text = currentValue
                    )
                }
            }
        }.orElse {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = progressIndicatorColor)
        }
    }
}

@UiModePreviews.Default
@Composable
fun PreviewOdsLinearProgressIndicator(@PreviewParameter(OdsLinearProgressIndicatorPreviewParameterProvider::class) progress: Float?) = Preview {
    OdsLinearProgressIndicator(
        progress = progress,
        icon = painterResource(id = android.R.drawable.stat_sys_download),
        label = "Downloading …",
        currentValue = "70%"
    )
}

private class OdsLinearProgressIndicatorPreviewParameterProvider : BasicPreviewParameterProvider<Float?>(0.75f, null)
