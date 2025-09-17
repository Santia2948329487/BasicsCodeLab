package com.example.basicscodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        // Usamos un solo Greeting por ahora (luego convertimos a lista)
        Greeting(name = "Android")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    // estado local que controla si la tarjeta está "expandida"
    var isExpanded by remember { mutableStateOf(false) }

    // animamos la separación (la tarjeta "crece" hacia abajo cuando isExpanded = true)
    val extraSpace by animateDpAsState(targetValue = if (isExpanded) 22.dp else 0.dp)

    Surface(
        color = MaterialTheme.colorScheme.primary,
        // animateContentSize suaviza cualquier cambio en el tamaño del contenido
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Hello", fontSize = 16.sp)
                Text(text = name, fontWeight = FontWeight.Bold, fontSize = 18.sp)

                // Aquí NO mostramos texto extra; solo agregamos espacio que se anima.
                Spacer(modifier = Modifier.height(extraSpace))
            }

            ElevatedButton(
                onClick = { isExpanded = !isExpanded } // alterna el estado
            ) {
                Text(if (isExpanded) "Show less" else "Show more")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BasicsCodeLabTheme {
        Column {
            Greeting("World")
            Spacer(modifier = Modifier.height(8.dp))
            Greeting("Compose")
        }
    }
}
