package com.neocalc.neocalc.core.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.neocalc.neocalc.ui.theme.Blue_2

data class RadioButtonItem(val id: Int, val title: String)


@Composable
fun RadioGroup(
	items: List<RadioButtonItem>,
	selected: Int,
	onItemSelect: (Int) -> Unit,
	modifier: Modifier = Modifier
) {
	Column(
		modifier = modifier.selectableGroup()
	) {
		items.forEach { item ->
			RadioGroupItem(
				item = item,
				selected = selected == item.id,
				onClick = { onItemSelect(it)},
				modifier = Modifier.fillMaxWidth()
			)
		}
	}

}

@Composable
fun RadioGroupItem(
	item: RadioButtonItem,
	selected: Boolean,
	onClick: (Int) -> Unit,
	modifier: Modifier = Modifier
) {
	// to make this composable selectable (where only one item is selectable)
	// for use in parent composable that have a composable selectable group
	Row(
		modifier = modifier
			.selectable(
				selected = selected,
				onClick = { onClick(item.id) },
				role = Role.RadioButton
			)
			.padding(vertical = 16.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		RadioButton(
			selected = selected,
			onClick = null,
			colors = RadioButtonDefaults.colors(selectedColor = Blue_2)
		)

		Spacer(modifier = Modifier.width(15.dp))

		Text(text = item.title, style = MaterialTheme.typography.bodyLarge)
	}
}
