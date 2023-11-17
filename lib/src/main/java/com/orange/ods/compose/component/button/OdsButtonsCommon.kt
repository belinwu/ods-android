/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.compose.component.button

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import com.orange.ods.compose.component.content.OdsComponentContent
import com.orange.ods.compose.component.content.OdsComponentIcon
import com.orange.ods.compose.theme.OdsDisplaySurface

/**
 * Contains classes to build an [com.orange.ods.compose.component.button.OdsButton].
 */
object OdsButton {

    /**
     * A button icon in an [OdsButton].
     * It is non-clickable and no content description is needed cause a button label is always present.
     */
    class Icon : OdsComponentIcon<Nothing> {

        /**
         * Creates an instance of [OdsButton.Icon].
         *
         * @param painter Painter of the icon.
         */
        constructor(painter: Painter) : super(painter, "")

        /**
         * Creates an instance of [OdsButton.Icon].
         *
         * @param imageVector Image vector of the icon.
         */
        constructor(imageVector: ImageVector) : super(imageVector, "")

        /**
         * Creates an instance of [OdsButton.Icon].
         *
         * @param bitmap Image bitmap of the icon.
         */
        constructor(bitmap: ImageBitmap) : super(bitmap, "")

        @Composable
        override fun Content(modifier: Modifier) {
            super.Content(modifier = modifier.size(ButtonDefaults.IconSize))
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        }
    }

}

/**
 * Contains classes to build an [com.orange.ods.compose.component.button.OdsIconButton].
 */
object OdsIconButton {

    /**
     * An icon in an [OdsIconButton].
     */
    class Icon : OdsComponentIcon<Icon.ExtraParameters> {

        data class ExtraParameters(
            val enabled: Boolean,
            val displaySurface: OdsDisplaySurface
        ) : OdsComponentContent.ExtraParameters()

        /**
         * Creates an instance of [OdsIconButton.Icon].
         *
         * @param painter Painter of the icon.
         * @param contentDescription The content description associated to this [OdsIconButton.Icon].
         */
        constructor(painter: Painter, contentDescription: String) : super(painter, contentDescription)

        /**
         * Creates an instance of [OdsIconButton.Icon].
         *
         * @param imageVector Image vector of the icon.
         * @param contentDescription The content description associated to this [OdsIconButton.Icon].
         */
        constructor(imageVector: ImageVector, contentDescription: String) : super(imageVector, contentDescription)

        /**
         * Creates an instance of [OdsIconButton.Icon].
         *
         * @param bitmap Image bitmap of the icon.
         * @param contentDescription The content description associated to this [OdsIconButton.Icon].
         */
        constructor(bitmap: ImageBitmap, contentDescription: String) : super(bitmap, contentDescription)

        override val tint: Color
            @Composable
            get() = iconButtonTintColor(displaySurface = displaySurface)

        @Composable
        override fun Content(modifier: Modifier) {
            enabled = extraParameters.enabled
            displaySurface = extraParameters.displaySurface
            super.Content(modifier)
        }
    }

}

@Composable
internal fun iconButtonTintColor(displaySurface: OdsDisplaySurface) = displaySurface.themeColors.onSurface