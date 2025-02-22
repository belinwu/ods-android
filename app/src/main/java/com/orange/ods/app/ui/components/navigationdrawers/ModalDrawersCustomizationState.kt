/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.app.ui.components.navigationdrawers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun rememberNavigationDrawersCustomizationState(
    subtitleChecked: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) },
    listIconChecked: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    contentExampleChecked: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) },
    headerImage: MutableState<ComponentNavigationDrawersContentState.HeaderImage> = rememberSaveable { mutableStateOf(ComponentNavigationDrawersContentState.HeaderImage.Avatar) },
    sectionListExample: MutableState<ComponentNavigationDrawersContentState.SectionListExample> = rememberSaveable {
        mutableStateOf(
            ComponentNavigationDrawersContentState.SectionListExample.None
        )
    }
) =
    remember(subtitleChecked, listIconChecked, contentExampleChecked, headerImage, sectionListExample) {
        ComponentNavigationDrawersContentState(
            subtitleChecked,
            listIconChecked,
            contentExampleChecked,
            sectionListExample,
            headerImage
        )
    }

class ComponentNavigationDrawersContentState(
    val subtitleChecked: MutableState<Boolean>,
    val listIconChecked: MutableState<Boolean>,
    val contentExampleChecked: MutableState<Boolean>,
    val content: MutableState<SectionListExample>,
    val header: MutableState<HeaderImage>
) {
    val isSubTitleChecked
        get() = subtitleChecked.value

    val isListIconChecked
        get() = listIconChecked.value

    val isContentExampleChecked
        get() = contentExampleChecked.value

    enum class HeaderImage {
        Avatar, Background, None
    }

    enum class SectionListExample {
        Divider, Label, None
    }

    val hasAvatar
        get() = header.value == HeaderImage.Avatar

    val hasBackground
        get() = header.value == HeaderImage.Background

    val hasDivider
        get() = content.value == SectionListExample.Divider

    val hasLabel
        get() = content.value == SectionListExample.Label
}