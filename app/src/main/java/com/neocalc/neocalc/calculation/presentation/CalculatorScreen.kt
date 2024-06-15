package com.neocalc.neocalc.calculation.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neocalc.neocalc.R
import com.neocalc.neocalc.calculation.domain.entities.AppTheme
import com.neocalc.neocalc.core.presentation.component.AppThemeDialog
import com.neocalc.neocalc.calculation.presentation.component.AutoSizeText
import com.neocalc.neocalc.calculation.presentation.component.CalculatorButton
import com.neocalc.neocalc.core.presentation.ThemeEvent
import com.neocalc.neocalc.core.presentation.ThemeViewModel
import com.neocalc.neocalc.ui.theme.CyanBlue
import com.neocalc.neocalc.ui.theme.ExtendedTheme
import com.neocalc.neocalc.ui.theme.poppins

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    themeViewModel: ThemeViewModel,
    calculationViewModel: CalculationViewModel,
    buttonSpacing: Dp = 15.dp,
    navigateToHistory: () -> Unit = {}
) {
    val themeState = themeViewModel.appTheme.collectAsStateWithLifecycle()
    val uiState = calculationViewModel.uiState.collectAsStateWithLifecycle()

    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        AppThemeDialog(
            previousAppTheme = themeState.value,
            onDismiss = { openDialog = !openDialog },
            onConfirm = {appTheme ->
                themeViewModel.onEvent(ThemeEvent.SaveAppTheme(appTheme))
                openDialog = !openDialog
            }
        )
    }

    Box(modifier = modifier) {
        AppBarSection(
            theme = themeState,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            onNavigateToHistory = navigateToHistory,
            onDisplayDialog = { openDialog = true }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        ) {

            ResultSection(uiState = uiState)

            Spacer(modifier = Modifier.height(buttonSpacing))

            CalculatorInputSection(
                modifier,
                buttonSpacing,
                onCalculateInput = { calculationViewModel.onEvent(CalculationEvent.Calculate) },
                onClearInput = { calculationViewModel.onEvent(CalculationEvent.Clear) },
                onDecimalInput = { calculationViewModel.onEvent(CalculationEvent.InputDecimal) },
                onDeleteInput = { calculationViewModel.onEvent(CalculationEvent.Delete) },
                onNumberInput = { number ->
                    calculationViewModel.onEvent(CalculationEvent.InputNumber(number))
                },
                onOperationInput = { calculatorOperation ->
                    calculationViewModel.onEvent(CalculationEvent.Operation(calculatorOperation))
                }
            )
        }
    }

}

@Composable
fun AppBarSection(
    theme: State<AppTheme>,
    modifier: Modifier = Modifier,
    onNavigateToHistory: () -> Unit = {},
    onDisplayDialog: () -> Unit = {}
) {
    val currentThemeState by remember { theme }

    Surface {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onNavigateToHistory) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_calculator_history),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            Crossfade(
                targetState = currentThemeState,
                label = "",
                animationSpec = tween(durationMillis = 700)
            ) {
                val isDarkTheme = when (it) {
                    AppTheme.DARK_MODE -> true
                    AppTheme.LIGHT_MODE -> false
                    AppTheme.SYSTEM_DEFAULT_MODE -> isSystemInDarkTheme()
                }

                val uiModeIcon =
                    if (isDarkTheme) R.drawable.ic_calculator_light_mode else R.drawable.ic_calculator_dark_mode

                IconButton(onClick = onDisplayDialog) {
                    Icon(
                        modifier = Modifier.size(26.dp),
                        painter = painterResource(id = uiModeIcon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun ResultSection(
    uiState : State<CalculatorScreenUiState>
){
    val scrollState = rememberScrollState()

    AutoSizeText(
        text = uiState.value.input,
        maxLines = 1,
        minTextSize = dimensionResource(id = R.dimen.min_input_text_size).value.sp,
        maxTextSize = dimensionResource(id = R.dimen.input_text_size).value.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 6.dp),
        alignment = Alignment.TopEnd,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Medium,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.Both,
            )
        ),
        color = if (uiState.value.isError)
            MaterialTheme.colorScheme.error
        else
            MaterialTheme.colorScheme.onSurface
    )

    Text(
        text = uiState.value.result,
        textAlign = TextAlign.End,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = 15.dp, vertical = 4.dp),
        fontWeight = FontWeight.Medium,
        fontFamily = poppins,
        fontSize = dimensionResource(id = R.dimen.result_text_size).value.sp,
        color = if (uiState.value.isError)
            MaterialTheme.colorScheme.error
        else
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
        maxLines = 1
    )
}

@Composable
fun CalculatorInputSection(
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = 50.dp,
    onNumberInput: (Int) -> Unit = {},
    onClearInput: () -> Unit = {},
    onDeleteInput: () -> Unit = {},
    onDecimalInput: () -> Unit = {},
    onCalculateInput: () -> Unit = {},
    onOperationInput: (CalculatorOperation) -> Unit = {},
) {

    Column(
        modifier
            .fillMaxWidth()
            .background(ExtendedTheme.colors.linkWhiteLightBlack)
            .clip(
                RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .padding(20.dp),

        verticalArrangement = Arrangement.spacedBy(buttonSpacing)
    ) {
        // first row of calculator input
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                textColor = CyanBlue,
                symbol = "AC", modifier = Modifier
                    .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                onClearInput()
            }

            CalculatorButton(
                textColor = CyanBlue,
                symbol = "%", modifier = Modifier
                    .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                onOperationInput(CalculatorOperation.Percent)
            }

            CalculatorButton(
                textColor = CyanBlue,
                symbol = "Del", modifier = Modifier
                    .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                onDeleteInput()
            }

            CalculatorButton(
                symbol = "÷", modifier = Modifier
                    .background(ExtendedTheme.colors.whiteBlue)
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                onOperationInput(CalculatorOperation.Divide)
            }
        }
        // Second row in calculator input
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                symbol = "7", modifier = Modifier
                    .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                onNumberInput(7)
            }
            CalculatorButton(
                symbol = "8", modifier = Modifier
                    .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                onNumberInput(8)
            }
            CalculatorButton(
                symbol = "9", modifier = Modifier
                    .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                onNumberInput(9)
            }
            CalculatorButton(
                symbol = "x", modifier = Modifier
                    .background(ExtendedTheme.colors.whiteBlue)
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                onOperationInput(CalculatorOperation.Multiply)
            }
        }

        // third row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                symbol = "4", modifier = Modifier
                    .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                onNumberInput(4)
            }
            CalculatorButton(
                symbol = "5", modifier = Modifier
                    .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                onNumberInput(5)
            }
            CalculatorButton(
                symbol = "6", modifier = Modifier
                    .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                onNumberInput(6)
            }
            CalculatorButton(
                symbol = "-", modifier = Modifier
                    .background(ExtendedTheme.colors.whiteBlue)
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                onOperationInput(CalculatorOperation.Subtract)
            }
        }

        // fourth row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                symbol = "1", modifier = Modifier
                    .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                onNumberInput(1)
            }
            CalculatorButton(
                symbol = "2", modifier = Modifier
                    .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                onNumberInput(2)
            }

            CalculatorButton(
                symbol = "3", modifier = Modifier
                    .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                onNumberInput(3)
            }

            CalculatorButton(
                symbol = "+", modifier = Modifier
                    .background(ExtendedTheme.colors.whiteBlue)
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                onOperationInput(CalculatorOperation.Add)
            }
        }

        // last row
        Row(
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                symbol = "0", modifier = Modifier
                    .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                    .aspectRatio(2f)
                    .weight(2f)
            ) {
                onNumberInput(0)
            }
            CalculatorButton(
                symbol = "•", modifier = Modifier
                    .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                onDecimalInput()
            }
            CalculatorButton(
                symbol = "=", modifier = Modifier
                    .background(ExtendedTheme.colors.whiteBlue)
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                onCalculateInput()
            }
        }
    }

}