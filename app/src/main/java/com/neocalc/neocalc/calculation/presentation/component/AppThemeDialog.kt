package com.neocalc.neocalc.calculation.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.neocalc.neocalc.ui.theme.ExtendedTheme

@Composable
fun AppThemeDialog(
	onDismiss: () -> Unit,
	onConfirm: () -> Unit
) {

	Dialog(onDismissRequest = onDismiss) {
		Card(
			modifier = Modifier
				.fillMaxWidth()
				.wrapContentHeight(),
			shape = RoundedCornerShape(10.dp),
			elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
			colors = CardDefaults.cardColors(containerColor = ExtendedTheme.colors.linkWhiteMediumDarkGrey)
		) {
			Column(
				Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
			) {
				Text(
					"Choose Theme",
					style = MaterialTheme.typography.titleLarge.copy(
						fontWeight = FontWeight.SemiBold
					),
				)

				Spacer(modifier = Modifier.size(10.dp))
			}


		}
	}


}