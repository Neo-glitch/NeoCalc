package com.example.neocalc.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorButton(
    textColor: Color = Color.White,
    symbol: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    // similar to stack in flutter
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(95.dp)
            .clip(CircleShape)
            .clickable { onClick() }
            .shadow(
                elevation = 30.dp,
                shape = CircleShape,
            )
            .then(modifier)

    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = if(symbol == "0") 40.dp else 0.dp)
                .fillMaxWidth(),
            text = symbol,
            fontSize = 25.sp,
            color = textColor,
            fontWeight = FontWeight.Medium,
            textAlign = if(symbol == "0") TextAlign.Start else TextAlign.Center
        )
    }

}