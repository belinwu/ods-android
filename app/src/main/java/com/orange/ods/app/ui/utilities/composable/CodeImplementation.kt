/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.app.ui.utilities.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import com.orange.ods.app.R
import com.orange.ods.app.ui.LocalUiFramework
import com.orange.ods.app.ui.UiFramework
import com.orange.ods.compose.component.menu.OdsExposedDropdownMenu
import com.orange.ods.compose.component.menu.OdsExposedDropdownMenuItem
import com.orange.ods.compose.theme.OdsTheme
import com.orange.ods.extension.fullName
import com.orange.ods.extension.orElse

const val IconPainterValue = "<icon painter>"
const val ImagePainterValue = "<image painter>"
const val PainterValue = "<painter>"
const val VectorValue = "<vector>"
const val CardTextValue = "<card text>"

private abstract class CodeParameter(val name: String) {
    abstract val code: @Composable () -> Unit
}

private open class SimpleParameter(name: String, val value: String) : CodeParameter(name) {
    override val code
        get() = @Composable {
            TechnicalText(text = "$name = $value,")
        }
}

private open class StringRepresentationParameter<T>(name: String, value: T) : SimpleParameter(name, value.toString())
private open class StringParameter(name: String, textValue: String) : SimpleParameter(name, "\"$textValue\"")
private open class LambdaParameter(name: String) : SimpleParameter(name, "{ }")
private class FloatParameter(name: String, value: Float) : SimpleParameter(name, value.toString().plus("f"))
private class MutableStateParameter(name: String, stateValue: String) : SimpleParameter(name, "remember { mutableStateOf($stateValue) }")
private class EnumParameter<T>(name: String, value: T) : SimpleParameter(name, value.fullName) where T : Enum<T>

private class ComposableParameter(name: String, val value: @Composable () -> Unit) : CodeParameter(name) {
    override val code
        get() = @Composable {
            TechnicalText(text = "$name = {")
            IndentCodeColumn(value)
            TechnicalText(text = "},")
        }
}

private open class FunctionParameter(name: String, val value: Function) : CodeParameter(name) {
    override val code
        get() = @Composable {
            TechnicalText(text = "$name = ${value.name}(")
            FunctionParametersCode(parameters = value.parameters, exhaustiveParameters = true)
            TechnicalText(text = "),")
        }
}

private class ClassInstanceParameter(name: String, value: ClassInstance) : FunctionParameter(name, with(value) { Function(clazz.simpleName, parameters) })

private class ListParameter(name: String, val value: List<Function>) : CodeParameter(name) {
    override val code
        get() = @Composable {
            TechnicalText(text = "$name = listOf(")
            IndentCodeColumn {
                value.forEach { item ->
                    FunctionCallCode(name = item.name, parameters = item.parameters, trailingComma = true, exhaustiveParameters = true)
                }
            }
            TechnicalText(text = "),")
        }
}

class ClassInstance(val clazz: Class<*>, parameters: ParametersBuilder.() -> Unit = {}) : Function(clazz.simpleName, parameters)
open class Function(val name: String, val parameters: ParametersBuilder.() -> Unit = {})

private sealed class PredefinedParameter {
    object Icon : SimpleParameter("icon", IconPainterValue)
    object Image : SimpleParameter("image", ImagePainterValue)
    object Painter : SimpleParameter("painter", PainterValue)
    object ImageVector : SimpleParameter("imageVector", VectorValue)
    object CardText : SimpleParameter("text", CardTextValue)
    object FillMaxWidth : SimpleParameter("modifier", "Modifier.fillMaxWidth()")

    class Enabled(enabled: Boolean) : StringRepresentationParameter<Boolean>("enabled", enabled)
    class Checked(checked: Boolean) : StringRepresentationParameter<Boolean>("checked", checked)
    class Selected(selected: Boolean) : StringRepresentationParameter<Boolean>("selected", selected)

    class Text(text: String) : StringParameter("text", text)
    class Title(text: String) : StringParameter("title", text)
    class Subtitle(text: String) : StringParameter("subtitle", text)
    class Label(text: String) : StringParameter("label", text)
    class Placeholder(text: String) : StringParameter("placeholder", text)
    class ContentDescription(text: String) : StringParameter("contentDescription", text)

    object OnClick : LambdaParameter("onClick")
    object OnCheckedChange : LambdaParameter("onCheckedChange")
}

@Composable
fun CodeImplementationColumn(
    modifier: Modifier = Modifier,
    xmlAvailable: Boolean = false, // Temporary parameter: Indicates whether the XML version of the component is available
    contentBackground: Boolean = true,
    content: @Composable () -> Unit
) {
    var currentUiFramework by LocalUiFramework.current
    // Reset current UI framework to Compose when displaying the content
    // shouldResetUiFramework is used to avoid calling LaunchedEffect on configuration changes (for instance on device rotation)
    var shouldResetUiFramework by rememberSaveable { mutableStateOf(true) }
    if (shouldResetUiFramework) {
        LaunchedEffect(Unit) {
            shouldResetUiFramework = false
            currentUiFramework = UiFramework.Compose
        }
    }

    Column(
        modifier = modifier.padding(
            vertical = dimensionResource(id = com.orange.ods.R.dimen.spacing_s)
        )
    ) {
        UiFrameworkChoice(xmlAvailable)
        if (currentUiFramework == UiFramework.Compose) {
            if (contentBackground) {
                CodeBackgroundColumn(content)
            } else {
                content()
            }
        } else {
            CodeBackgroundColumn {
                TechnicalText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = com.orange.ods.R.dimen.spacing_xs)),
                    text = stringResource(id = R.string.soon_available)
                )
            }
        }
    }
}

@Composable
private fun UiFrameworkChoice(xmlAvailable: Boolean) {
    val context = LocalContext.current
    val currentUiFramework = LocalUiFramework.current
    val uiFrameworkItems = UiFramework.values().map { uiFramework ->
        OdsExposedDropdownMenuItem(label = stringResource(id = uiFramework.labelResId), iconResId = uiFramework.iconResId)
    }
    val selectedUiFramework = rememberSaveable(currentUiFramework.value) {
        val selectedUiFramework = if (xmlAvailable) currentUiFramework.value else UiFramework.Compose
        val selectedUiFrameworkIndex = UiFramework.values().indexOf(selectedUiFramework)
        mutableStateOf(uiFrameworkItems[selectedUiFrameworkIndex])
    }

    OdsExposedDropdownMenu(
        label = stringResource(id = R.string.code_implementation),
        items = uiFrameworkItems,
        selectedItem = selectedUiFramework,
        onItemSelectionChange = { selectedItem ->
            currentUiFramework.value = UiFramework.values().first { context.getString(it.labelResId) == selectedItem.label }
        },
        enabled = xmlAvailable
    )
}

@Composable
fun CodeBackgroundColumn(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .background(OdsTheme.colors.onSurface.copy(alpha = 0.12f))
            .padding(horizontal = dimensionResource(id = com.orange.ods.R.dimen.spacing_s), vertical = dimensionResource(id = com.orange.ods.R.dimen.spacing_s))
            .semantics(mergeDescendants = true) {}) {
        content()
    }
}

@Composable
fun IndentCodeColumn(content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(start = dimensionResource(id = com.orange.ods.R.dimen.spacing_s))) {
        content()
    }
}

@Composable
fun FunctionCallCode(
    name: String,
    parameters: (ParametersBuilder.() -> Unit)? = null,
    exhaustiveParameters: Boolean = true,
    trailingComma: Boolean = false,
    trailingClosure: @Composable (() -> Unit)? = null
) {
    when {
        parameters == null && trailingClosure != null -> FunctionCallWithTrailingClosureOnly(name) { trailingClosure() }
        parameters == null && trailingClosure == null -> TechnicalText(text = "$name()".withTrailingComma(trailingComma))
        else -> {
            TechnicalText(text = "$name(")
            FunctionParametersCode(parameters = parameters.orElse { {} }, exhaustiveParameters = exhaustiveParameters)

            if (trailingClosure != null) {
                TechnicalText(text = ") {")
                IndentCodeColumn {
                    trailingClosure()
                    TechnicalText(text = "//...")
                }
                TechnicalText(text = "}".withTrailingComma(trailingComma))
            } else {
                TechnicalText(text = ")".withTrailingComma(trailingComma))
            }
        }
    }
}

/**
 * Add a trailing comma to the String if [comma] is true
 */
private fun String.withTrailingComma(comma: Boolean) = if (comma) plus(",") else this

@Composable
private fun FunctionParametersCode(parameters: ParametersBuilder.() -> Unit, exhaustiveParameters: Boolean) {
    IndentCodeColumn {
        ParametersBuilder().apply(parameters).Build()
        if (!exhaustiveParameters) TechnicalText(text = "//...")
    }
}

@Composable
private fun FunctionCallWithTrailingClosureOnly(name: String, trailingClosure: @Composable () -> Unit) {
    TechnicalText(text = "$name {")
    IndentCodeColumn {
        trailingClosure()
        TechnicalText(text = "//...")
    }
    TechnicalText(text = "}")
}

@DslMarker
annotation class CodeImplementationDslMarker

@CodeImplementationDslMarker
class ListParameterValueBuilder {

    private val functions = mutableListOf<Function>()

    inline fun <reified T> classInstance(noinline parameters: ParametersBuilder.() -> Unit = {}) =
        classInstance(T::class.java, parameters)

    fun classInstance(clazz: Class<*>, parameters: ParametersBuilder.() -> Unit = {}) = apply { functions.add(ClassInstance(clazz, parameters)) }

    fun function(functionName: String, parameters: ParametersBuilder.() -> Unit = {}) = apply { functions.add(Function(functionName, parameters)) }

    fun build() = functions.toList()
}

@CodeImplementationDslMarker
class ParametersBuilder {

    private val parameters = mutableListOf<CodeParameter>()

    private fun add(parameter: CodeParameter) = apply { parameters.add(parameter) }

    fun simple(name: String, value: String) = add(SimpleParameter(name, value))
    fun <T> stringRepresentation(name: String, value: T) = add(StringRepresentationParameter(name, value))
    fun string(name: String, textValue: String) = add(StringParameter(name, textValue))
    fun lambda(name: String) = add(LambdaParameter(name))
    fun float(name: String, value: Float) = add(FloatParameter(name, value))
    fun mutableState(name: String, stateValue: String) = add(MutableStateParameter(name, stateValue))
    fun <T : Enum<T>> enum(name: String, value: T) = add(EnumParameter(name, value))
    fun composable(name: String, value: @Composable () -> Unit) = add(ComposableParameter(name, value))
    inline fun <reified T> classInstance(name: String, noinline parameters: ParametersBuilder.() -> Unit) =
        classInstance(name, T::class.java, parameters)

    fun classInstance(name: String, clazz: Class<*>, parameters: ParametersBuilder.() -> Unit) =
        add(ClassInstanceParameter(name, ClassInstance(clazz, parameters)))

    fun function(name: String, functionName: String, parameters: ParametersBuilder.() -> Unit) =
        add(FunctionParameter(name, Function(functionName, parameters)))

    fun list(name: String, value: ListParameterValueBuilder.() -> Unit) = add(ListParameter(name, ListParameterValueBuilder().apply(value).build()))
    fun icon() = add(PredefinedParameter.Icon)
    fun painter() = add(PredefinedParameter.Painter)
    fun image() = add(PredefinedParameter.Image)
    fun imageVector() = add(PredefinedParameter.ImageVector)
    fun cardText() = add(PredefinedParameter.CardText)
    fun fillMaxWidth() = add(PredefinedParameter.FillMaxWidth)

    fun enabled(enabled: Boolean) = add(PredefinedParameter.Enabled(enabled))
    fun checked(checked: Boolean) = add(PredefinedParameter.Checked(checked))
    fun selected(selected: Boolean) = add(PredefinedParameter.Selected(selected))

    fun text(text: String) = add(PredefinedParameter.Text(text))
    fun title(text: String) = add(PredefinedParameter.Title(text))
    fun subtitle(text: String) = add(PredefinedParameter.Subtitle(text))
    fun label(text: String) = add(PredefinedParameter.Label(text))
    fun placeholder(text: String) = add(PredefinedParameter.Placeholder(text))
    fun contentDescription(text: String) = add(PredefinedParameter.ContentDescription(text))

    fun onClick() = add(PredefinedParameter.OnClick)
    fun onCheckedChange() = add(PredefinedParameter.OnCheckedChange)

    @Composable
    fun Build() = parameters.forEach { it.code() }
}
