package com.neocalc.neocalc.core.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.neocalc.neocalc.calculation.domain.entities.AppTheme

@Composable
fun AppThemeDialog(
	previousAppTheme: AppTheme = AppTheme.DARK_MODE,
	onDismiss: () -> Unit = {},
	onConfirm: (AppTheme) -> Unit = {}
) {
	val themeItems = listOf(
		RadioButtonItem(
			id = AppTheme.LIGHT_MODE.ordinal,
			title = "Light Theme"
		),
		RadioButtonItem(
			id = AppTheme.DARK_MODE.ordinal,
			title = "Dark Theme"
		),
		RadioButtonItem(
			id = AppTheme.SYSTEM_DEFAULT_MODE.ordinal,
			title = "System Default"
		),
	)

	var selectedItemId by remember { mutableStateOf(previousAppTheme) }

	Dialog(onDismissRequest = onDismiss) {
		Card(
			modifier = Modifier
				.fillMaxWidth()
				.wrapContentHeight(),
			shape = RoundedCornerShape(25.dp),
			elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
			colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
		) {
			Column(
				Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
			) {
				Text(
					"Select Theme",
					style = MaterialTheme.typography.titleLarge.copy(
						fontWeight = FontWeight.SemiBold
					),
				)

				Spacer(modifier = Modifier.size(10.dp))
				RadioGroup(
					modifier = Modifier.fillMaxWidth(1f),
					items = themeItems,
					selected = selectedItemId.ordinal,
					onItemSelect = { itemId ->
						selectedItemId = AppTheme.fromOrdinal(itemId)
					},
				)

				Spacer(Modifier.height(20.dp))

				Row(
					modifier = Modifier.fillMaxWidth(),
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(10.dp)
				) {
					Spacer(modifier = Modifier.weight(1f))

					TextButton(onClick = onDismiss ) {
						Text(text = "Cancel", style = MaterialTheme.typography.labelLarge)
					}

					TextButton(onClick = { onConfirm(selectedItemId) }) {
						Text(text = "Apply", style = MaterialTheme.typography.labelLarge)
					}
				}
			}


		}
	}


}