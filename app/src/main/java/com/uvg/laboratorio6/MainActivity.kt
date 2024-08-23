package com.uvg.laboratorio6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.uvg.laboratorio6.ui.theme.Laboratorio6Theme
import kotlin.math.max
import kotlin.math.min

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Laboratorio6Theme {
                enableEdgeToEdge()
                ContadorApp()
            }
        }
    }
}

@Composable
fun ContadorApp() {
    var contador by remember { mutableIntStateOf(0) }
    var totalIncrementos by remember { mutableIntStateOf(0) }
    var totalDecrementos by remember { mutableIntStateOf(0) }
    var valorMaximo by remember { mutableIntStateOf(Int.MIN_VALUE) }
    var valorMinimo by remember { mutableIntStateOf(Int.MAX_VALUE) }
    var totalCambios by remember { mutableIntStateOf(0) }
    val historial = remember { mutableStateListOf<Pair<Int, Boolean>>() }

    fun reiniciar() {
        contador = 0
        totalIncrementos = 0
        totalDecrementos = 0
        valorMaximo = Int.MIN_VALUE
        valorMinimo = Int.MAX_VALUE
        totalCambios = 0
        historial.clear()
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Juan Carlos Durini",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                IconButton(onClick = {
                    contador--
                    totalDecrementos++
                    valorMinimo = min(valorMinimo, contador)
                    totalCambios++
                    historial.add(contador to false)
                }) {
                    Icon(Icons.Default.Remove, contentDescription = "Decrementar")
                }
                Text(
                    text = contador.toString(),
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                IconButton(onClick = {
                    contador++
                    totalIncrementos++
                    valorMaximo = max(valorMaximo, contador)
                    totalCambios++
                    historial.add(contador to true)
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Incrementar")
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 8.dp)
            ) {
                CounterRow(label = "Total incrementos:", value = totalIncrementos)
                CounterRow(label = "Total decrementos:", value = totalDecrementos)
                CounterRow(label = "Valor máximo:", value = valorMaximo)
                CounterRow(label = "Valor mínimo:", value = valorMinimo)
                CounterRow(label = "Total cambios:", value = totalCambios)
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.weight(1f, fill = false)
            ) {
                items(historial.size) { index ->
                    val (value, isIncrement) = historial[index]
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(4.dp)
                            .background(
                                if (isIncrement) Color.Green else Color.Red,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(8.dp)
                    ) {
                        Text(value.toString(), color = Color.White)
                    }
                }
            }

            Button(
                onClick = { reiniciar() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Reiniciar")
            }
        }
    }
}

@Composable
fun CounterRow(label: String, value: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value.toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContadorAppPreview() {
    Laboratorio6Theme {
        ContadorApp()
    }
}
