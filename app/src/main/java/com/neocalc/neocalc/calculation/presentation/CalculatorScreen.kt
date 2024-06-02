package com.neocalc.neocalc.calculation.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neocalc.neocalc.calculation.presentation.component.AutoSizeText
import com.neocalc.neocalc.calculation.presentation.component.CalculatorButton
import com.neocalc.neocalc.ui.theme.CyanBlue
import com.neocalc.neocalc.ui.theme.ExtendedTheme
import com.neocalc.neocalc.ui.theme.poppins

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel,
    buttonSpacing: Dp = 15.dp,
    modifier: Modifier = Modifier
) {

    val state = viewModel.uiState
    val uiState = state.collectAsState()

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        ) {
            ResultSection(uiState = uiState)

            Spacer(modifier = modifier.height(buttonSpacing))

            CalculatorInputSection(
                modifier,
                buttonSpacing,
                onCalculateInput = { viewModel.onEvent(CalculatorEvent.Calculate) },
                onClearInput = { viewModel.onEvent(CalculatorEvent.Clear) },
                onDecimalInput = { viewModel.onEvent(CalculatorEvent.Decimal) },
                onDeleteInput = { viewModel.onEvent(CalculatorEvent.Delete) },
                onNumberInput = { number ->
                    viewModel.onEvent(CalculatorEvent.NumberEvent(number))
                },
                onOperationInput = { calculatorOperation ->
                    viewModel.onEvent(CalculatorEvent.Operation(calculatorOperation))
                }
            )
        }
    }
}

@Composable
fun ResultSection(
    uiState : State<CalculatorScreenUiState>
){
    AutoSizeText(
        text = uiState.value.input,
        maxLines = 1,
        minTextSize = 30.sp,
        maxTextSize = 50.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 6.dp),
        alignment = Alignment.TopEnd,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Medium,
            fontFamily = poppins,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.Both,
            )
        ),
        color = if (uiState.value.isError)
            MaterialTheme.colorScheme.error
        else
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
    )

    Text(
        text = uiState.value.result,
        textAlign = TextAlign.End,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 4.dp),
        fontWeight = FontWeight.Medium,
        fontFamily = poppins,
        fontSize = 40.sp,
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
            .clip(
                RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .background(ExtendedTheme.colors.linkWhiteLightBlack)
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