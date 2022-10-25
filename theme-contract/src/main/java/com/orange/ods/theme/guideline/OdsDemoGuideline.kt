/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.theme.guideline

import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import com.orange.ods.theme.R

/**
 * This class defines what will be displayed in the ODS Demo Application guideline part.
 * Extend this class and override its properties to allow the Demo Application to display the guideline
 * elements (colors, typography) of your custom theme.
 */
open class OdsDemoGuideline {

    /**
     * The colors of the theme configuration guideline
     */
    open val guidelineColors: List<GuidelineColor>
        get() = emptyList()

    /**
     * The typography of the theme configuration guideline
     */
    open val guidelineTypography: List<GuidelineTextStyle>
        @Composable get() {
            with(typography) {
                val properties = listOf(::h1, ::h2, ::h3, ::h4, ::h5, ::h6, ::subtitle1, ::subtitle2, ::body1, ::body2, ::button, ::caption, ::overline)

                return properties.mapNotNull { property ->
                    val (name, xmlResource) = when (property) {
                        ::h1 -> "Headline 1" to R.attr.textAppearanceHeadline1
                        ::h2 -> "Headline 2" to R.attr.textAppearanceHeadline2
                        ::h3 -> "Headline 3" to R.attr.textAppearanceHeadline3
                        ::h4 -> "Headline 4" to R.attr.textAppearanceHeadline4
                        ::h5 -> "Headline 5" to R.attr.textAppearanceHeadline5
                        ::h6 -> "Headline 6" to R.attr.textAppearanceHeadline6
                        ::subtitle1 -> "Subtitle1" to R.attr.textAppearanceSubtitle1
                        ::subtitle2 -> "Subtitle2" to R.attr.textAppearanceSubtitle2
                        ::body1 -> "Body1" to R.attr.textAppearanceBody1
                        ::body2 -> "Body2" to R.attr.textAppearanceBody2
                        ::button -> "BUTTON" to R.attr.textAppearanceButton
                        ::caption -> "Caption" to R.attr.textAppearanceCaption
                        ::overline -> "Overline" to R.attr.textAppearanceOverline
                        else -> null to null
                    }
                    if (name != null && xmlResource != null) {
                        GuidelineTextStyle(
                            name = name,
                            textStyle = property.call(),
                            composeStyle = "OdsTheme.typography.${property.name}",
                            xmlResource = xmlResource
                        )
                    } else {
                        null
                    }
                }
            }
        }
}