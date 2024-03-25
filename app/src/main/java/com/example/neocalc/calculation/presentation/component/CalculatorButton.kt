package com.example.neocalc.calculation.presentation.component

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.neocalc.ui.theme.LightBlack
import com.example.neocalc.ui.theme.poppins

@Composable
fun CalculatorButton(
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    symbol: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    // similar to stack in flutter
    val view = LocalView.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                onClick()
            }
            .shadow(
                elevation = 40.dp,
                shape = CircleShape,
                ambientColor = LightBlack,
                spotColor = LightBlack
            )
            .then(modifier)

    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = if(symbol == "0") 40.dp else 0.dp)
                .fillMaxWidth(),
            text = symbol,
            fontSize = 22.sp,
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontFamily = poppins,
            textAlign = if(symbol == "0") TextAlign.Start else TextAlign.Center
        )
    }

}