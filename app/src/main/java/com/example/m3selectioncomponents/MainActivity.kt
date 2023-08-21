package com.example.m3selectioncomponents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.m3selectioncomponents.ui.theme.M3SelectionComponentsTheme

data class ToggleableInfo(
    val isChecked: Boolean,
    val text: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            M3SelectionComponentsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SelectionComponents(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun SelectionComponents(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Checkboxes()
        SampleSwitch()
        RadioButtons()
    }
}

@Composable
fun Checkboxes() {

    var triState by remember {
        mutableStateOf(ToggleableState.Indeterminate)
    }

    val checkboxes = remember {
        mutableStateListOf(
            ToggleableInfo(
                isChecked = false,
                text = "Photos"
            ),
            ToggleableInfo(
                isChecked = false,
                text = "Videos"
            ),
            ToggleableInfo(
                isChecked = false,
                text = "Memes"
            )
        )
    }

    val toggleTriState = {
        triState = when (triState) {
            ToggleableState.Indeterminate -> ToggleableState.On
            ToggleableState.On -> ToggleableState.Off
            else -> ToggleableState.On
        }
        checkboxes.indices.forEach { index ->
            checkboxes[index] = checkboxes[index].copy(
                isChecked = triState == ToggleableState.On
            )
        }
    }

    val toggleCheckState = { index: Int, info: ToggleableInfo ->
        checkboxes[index] = info.copy(isChecked = !info.isChecked)
    }

    Row(
        modifier = Modifier.clickable { toggleTriState() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        TriStateCheckbox(
            state = triState,
            onClick = toggleTriState
        )
        Text(text = "My types")
    }

    checkboxes.forEachIndexed { index, info ->
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .clickable { toggleCheckState(index, info) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = info.isChecked,
                onCheckedChange = { toggleCheckState(index, info) }
            )
            Text(text = info.text)
        }
    }
}

@Composable
fun SampleSwitch() {
    var switch by remember {
        mutableStateOf(
            ToggleableInfo(
                isChecked = false,
                text = "Dark mode"
            )
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = switch.text)
        Switch(
            checked = switch.isChecked,
            onCheckedChange = { switch = switch.copy(isChecked = it) }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = switch.text)
        Switch(
            checked = switch.isChecked,
            onCheckedChange = { switch = switch.copy(isChecked = it) },
            thumbContent = {
                Icon(
                    imageVector = if (switch.isChecked) Icons.Default.Close else Icons.Default.Check,
                    contentDescription = switch.text
                )
            }
        )
    }
}


@Composable
fun RadioButtons() {

    val radiobuttons = remember {
        mutableStateListOf(
            ToggleableInfo(
                isChecked = true,
                text = "Photos"
            ),
            ToggleableInfo(
                isChecked = false,
                text = "Videos"
            ),
            ToggleableInfo(
                isChecked = false,
                text = "Memes"
            )
        )
    }

    val toggleRadioButtons = { info: ToggleableInfo ->
        radiobuttons.replaceAll {
            it.copy(
                isChecked = it.text == info.text
            )
        }
    }

    radiobuttons.forEachIndexed { index, info ->
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .clickable { toggleRadioButtons(info) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = info.isChecked,
                onClick = { toggleRadioButtons(info) }
            )
            Text(text = info.text)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    M3SelectionComponentsTheme {
        SelectionComponents(modifier = Modifier.fillMaxSize())
    }
}