package com.example.neocalc

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.neocalc.component.CalculatorButton
import com.example.neocalc.ui.theme.Blue_2
import com.example.neocalc.ui.theme.GreenShade
import com.example.neocalc.ui.theme.LightBlack
import com.example.neocalc.ui.theme.LightGray
import com.example.neocalc.ui.theme.MediumGray
import com.example.neocalc.ui.theme.Orange

@Composable
fun Calculator(
    viewModel: CalculatorViewModel,
    buttonSpacing: Dp = 15.dp,
    modifier: Modifier = Modifier
) {

    val state = viewModel.uiState
    val uiState = state.collectAsState()
//    val uiState = state.collectAsState()


    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        ) {
            // the result field shown to the user
            Text(
                text = uiState.value.firstNumber + (uiState.value.operation?.symbol
                    ?: "") + uiState.value.secondNumber,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                fontWeight = FontWeight.Medium,
                fontSize = 40.sp,
                color = Color.White,
                maxLines = 3
            )
            Spacer(modifier = modifier.height(buttonSpacing))
            Column(
                modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
                    .background(LightBlack)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                // first row of calculator input
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    // weight of this component in the row, if 2f
                    CalculatorButton(
                        textColor = GreenShade,
                        symbol = "AC", modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.Clear)
                    }

                    CalculatorButton(
                        textColor = GreenShade,
                        symbol = "%", modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.Operation(CalculatorOperation.Percent))
                    }

                    CalculatorButton(
                        textColor = GreenShade,
                        symbol = "Del", modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.Delete)
                    }

                    CalculatorButton(
                        symbol = "÷", modifier = Modifier
                            .background(Blue_2)
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
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(7))
                    }
                    CalculatorButton(
                        symbol = "8", modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(8))
                    }
                    CalculatorButton(
                        symbol = "9", modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(9))
                    }
                    CalculatorButton(
                        symbol = "x", modifier = Modifier
                            .background(Blue_2)
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
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(4))
                    }
                    CalculatorButton(
                        symbol = "5", modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(5))
                    }
                    CalculatorButton(
                        symbol = "6", modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(6))
                    }
                    CalculatorButton(
                        symbol = "-", modifier = Modifier
                            .background(Blue_2)
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
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(1))
                    }
                    CalculatorButton(
                        symbol = "2", modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(2))
                    }

                    CalculatorButton(
                        symbol = "3", modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(3))
                    }

                    CalculatorButton(
                        symbol = "+", modifier = Modifier
                            .background(Blue_2)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.Operation(CalculatorOperation.Add))
                    }
                }

                // last row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ){
                    CalculatorButton(
                        symbol = "0", modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(2f)
                            .weight(2f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.NumberEvent(0))
                    }
                    CalculatorButton(
                        symbol = ".", modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onEvent(CalculatorEvent.Decimal)
                    }
                    CalculatorButton(
                        symbol = "=", modifier = Modifier
                            .background(Blue_2)
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