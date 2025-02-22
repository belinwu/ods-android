/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.xml.component.button

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.withStyledAttributes
import com.orange.ods.compose.component.button.OdsIconToggleButtonsRow
import com.orange.ods.compose.theme.OdsDisplaySurface
import com.orange.ods.xml.R
import com.orange.ods.xml.component.OdsAbstractComposeView
import com.orange.ods.xml.utilities.extension.fromXmlAttrValue

class OdsIconToggleButtonsRow @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : OdsAbstractComposeView(context, attrs) {

    var icons by mutableStateOf<List<OdsIconToggleButtonsRow.Icon>>(emptyList())
    var selectedIndex by mutableStateOf(0)
    var onSelectedIndexChange by mutableStateOf<(Int) -> Unit>({})
    var displaySurface by mutableStateOf(OdsDisplaySurface.Default)

    init {
        context.withStyledAttributes(attrs, R.styleable.OdsIconToggleButtonsRow) {
            selectedIndex = getInt(R.styleable.OdsIconToggleButtonsRow_selectedIndex, 0)
            displaySurface = OdsDisplaySurface.fromXmlAttrValue(getInteger(R.styleable.OdsIconToggleButtonsRow_displaySurface, 0))
        }
    }

    @Composable
    override fun OdsContent() {
        OdsIconToggleButtonsRow(
            icons = icons,
            selectedIndex = selectedIndex,
            onSelectedIndexChange = onSelectedIndexChange,
            displaySurface = displaySurface
        )
    }
}
