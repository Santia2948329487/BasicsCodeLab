package com.example.basicscodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicscodelab.ui.theme.BasicsCodeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicsCodeLabTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    val names = List(1000) { "Hello $it" }

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        GreetingList(names = names)
    }
}

@Composable
fun GreetingList(names: List<String>, modifier: Modifier = Modifier) {
    // 👇 ahora usamos rememberSaveable para que el estado sobreviva
    var expandedStates by rememberSaveable {
        mutableStateOf(List(names.size) { false })
    }

    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        itemsIndexed(names) { index, name ->
            GreetingCard(
                name = name,
                expanded = expandedStates[index],
                onExpandChange = { isExpanded ->
                    expandedStates = expandedStates.toMutableList().also {
                        it[index] = isExpanded
                    }
                }
            )
        }
    }
}

@Composable
fun GreetingCard(
    name: String,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val extraPadding by animateDpAsState(
        targetValue = if (expanded) 48.dp else 0.dp, label = ""
    )

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            )

            ElevatedButton(onClick = { onExpandChange(!expanded) }) {
                Text(if (expanded) "Show less" else "Show more")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingListPreview() {
    BasicsCodeLabTheme {
        MyApp()
    }
}
