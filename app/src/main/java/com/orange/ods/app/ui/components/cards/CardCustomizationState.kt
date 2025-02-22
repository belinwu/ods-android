/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.app.ui.components.cards

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.orange.ods.compose.component.card.OdsCard

@Composable
fun rememberCardCustomizationState(
    clickable: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) },
    thumbnailChecked: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) },
    textChecked: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) },
    subtitleChecked: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) },
    buttonCount: MutableIntState = rememberSaveable { mutableIntStateOf(CardCustomizationState.MinButtonCount) },
    dividerChecked: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) },
    imagePosition: MutableState<OdsCard.Image.Position> = rememberSaveable { mutableStateOf(OdsCard.Image.Position.Start) }
) =
    remember(clickable, thumbnailChecked, textChecked, subtitleChecked, buttonCount, dividerChecked, imagePosition) {
        CardCustomizationState(clickable, thumbnailChecked, textChecked, subtitleChecked, buttonCount, dividerChecked, imagePosition)
    }

class CardCustomizationState(
    val clickable: MutableState<Boolean>,
    val thumbnailChecked: MutableState<Boolean>,
    val textChecked: MutableState<Boolean>,
    val subtitleChecked: MutableState<Boolean>,
    val buttonCount: MutableIntState,
    val dividerChecked: MutableState<Boolean>,
    val imagePosition: MutableState<OdsCard.Image.Position>
) {

    companion object {
        const val MinButtonCount = 0
        const val MaxButtonCount = 2
    }

    val isClickable
        get() = clickable.value

    val hasThumbnail
        get() = thumbnailChecked.value

    val hasText
        get() = textChecked.value

    val hasSubtitle
        get() = subtitleChecked.value

    val hasFirstButton
        get() = buttonCount.intValue > 0

    val hasSecondButton
        get() = buttonCount.intValue > 1

    val hasDivider
        get() = if (!hasFirstButton) false else dividerChecked.value
}