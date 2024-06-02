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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neocalc.neocalc.calculation.presentation.component.AutoResizedText
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
            AutoResizedText(
                text = uiState.value.input,
                minFontSize = 30.sp,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 6.dp),
                maxLines = 1,
                textAlign = TextAlign.End,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppins,
                    fontSize = 50.sp
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

            Spacer(modifier = modifier.height(buttonSpacing))
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
                        viewModel.onEvent(CalculatorEvent.Clear)
                    }

                    CalculatorButton(
                        textColor = CyanBlue,
                        symbol = "%", modifier = Modifier
                            .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.Operation(CalculatorOperation.Percent))
                    }

                    CalculatorButton(
                        textColor = CyanBlue,
                        symbol = "Del", modifier = Modifier
                            .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.Delete)
                    }

                    CalculatorButton(
                        symbol = "÷", modifier = Modifier
                            .background(ExtendedTheme.colors.whiteBlue)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.Operation(CalculatorOperation.Divide))
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
                        viewModel.onEvent(CalculatorEvent.NumberEvent(7))
                    }
                    CalculatorButton(
                        symbol = "8", modifier = Modifier
                            .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(8))
                    }
                    CalculatorButton(
                        symbol = "9", modifier = Modifier
                            .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(9))
                    }
                    CalculatorButton(
                        symbol = "x", modifier = Modifier
                            .background(ExtendedTheme.colors.whiteBlue)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.Operation(CalculatorOperation.Multiply))
                    }
                }

                // third row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ){
                    CalculatorButton(
                        symbol = "4", modifier = Modifier
                            .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(4))
                    }
                    CalculatorButton(
                        symbol = "5", modifier = Modifier
                            .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(5))
                    }
                    CalculatorButton(
                        symbol = "6", modifier = Modifier
                            .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(6))
                    }
                    CalculatorButton(
                        symbol = "-", modifier = Modifier
                            .background(ExtendedTheme.colors.whiteBlue)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.Operation(CalculatorOperation.Subtract))
                    }
                }

                // fourth row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ){
                    CalculatorButton(
                        symbol = "1", modifier = Modifier
                            .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(1))
                    }
                    CalculatorButton(
                        symbol = "2", modifier = Modifier
                            .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(2))
                    }

                    CalculatorButton(
                        symbol = "3", modifier = Modifier
                            .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(3))
                    }

                    CalculatorButton(
                        symbol = "+", modifier = Modifier
                            .background(ExtendedTheme.colors.whiteBlue)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.Operation(CalculatorOperation.Add))
                    }
                }

                // last row
                Row(
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ){
                    CalculatorButton(
                        symbol = "0", modifier = Modifier
                            .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                            .aspectRatio(2f)
                            .weight(2f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(0))
                    }
                    CalculatorButton(
                        symbol = "•", modifier = Modifier
                            .background(ExtendedTheme.colors.linkWhiteMediumDarkGrey)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.Decimal)
                    }
                    CalculatorButton(
                        symbol = "=", modifier = Modifier
                            .background(ExtendedTheme.colors.whiteBlue)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.Calculate)
                    }
                }
            }
        }
    }
}