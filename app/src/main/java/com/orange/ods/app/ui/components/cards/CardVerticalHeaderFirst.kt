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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.orange.ods.app.R
import com.orange.ods.app.domain.recipes.LocalRecipes
import com.orange.ods.app.ui.components.utilities.clickOnElement
import com.orange.ods.app.ui.utilities.DrawableManager
import com.orange.ods.app.ui.utilities.code.CodeImplementationColumn
import com.orange.ods.app.ui.utilities.code.FunctionCallCode
import com.orange.ods.compose.OdsComposable
import com.orange.ods.compose.component.card.OdsCard
import com.orange.ods.compose.component.card.OdsVerticalHeaderFirstCard

@Composable
fun CardVerticalHeaderFirst(customizationState: CardCustomizationState) {
    val context = LocalContext.current
    val recipes = LocalRecipes.current
    val recipe = rememberSaveable { recipes.filter { it.description.isNotBlank() }.random() }

    with(customizationState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .padding(dimensionResource(id = com.orange.ods.R.dimen.spacing_m))
        ) {
            val firstButtonText = stringResource(id = R.string.component_element_first_button)
            val secondButtonText = stringResource(id = R.string.component_element_second_button)
            val cardText = stringResource(id = R.string.component_card_element_card)
            val imagePainter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(recipe.imageUrl)
                    .size(Size.ORIGINAL)
                    .build(),
                placeholder = painterResource(id = DrawableManager.getPlaceholderResId()),
                error = painterResource(id = DrawableManager.getPlaceholderResId(error = true))
            )

            OdsVerticalHeaderFirstCard(
                title = recipe.title,
                image = OdsCard.Image(imagePainter, ""),
                thumbnail = if (hasThumbnail) OdsCard.Thumbnail(imagePainter, "") else null,
                subtitle = if (hasSubtitle) recipe.subtitle else null,
                text = if (hasText) recipe.description else null,
                onClick = if (isClickable) {
                    { clickOnElement(context, cardText) }
                } else null,
                firstButton = if (hasFirstButton) OdsCard.Button(firstButtonText) { clickOnElement(context, firstButtonText) } else null,
                secondButton = if (hasSecondButton) OdsCard.Button(secondButtonText) { clickOnElement(context, secondButtonText) } else null,
            )

            CodeImplementationColumn(
                modifier = Modifier.padding(top = dimensionResource(com.orange.ods.R.dimen.spacing_s))
            ) {
                FunctionCallCode(
                    name = OdsComposable.OdsVerticalHeaderFirstCard.name,
                    exhaustiveParameters = false,
                    parameters = {
                        title(recipe.title)
                        classInstance("image", OdsCard.Image::class.java) {
                            painter()
                            contentDescription("")
                        }
                        if (hasThumbnail) {
                            classInstance("thumbnail", OdsCard.Thumbnail::class.java) {
                                painter()
                                contentDescription("")
                            }
                        }
                        if (hasSubtitle) subtitle(recipe.subtitle)
                        if (hasText) cardText()
                        if (isClickable) onClick()
                        if (hasFirstButton) {
                            classInstance("firstButton", OdsCard.Button::class.java) {
                                text(firstButtonText)
                                onClick()
                            }
                        }
                        if (hasSecondButton) {
                            classInstance("secondButton", OdsCard.Button::class.java) {
                                text(secondButtonText)
                                onClick()
                            }
                        }
                    }
                )
            }
        }
    }
}