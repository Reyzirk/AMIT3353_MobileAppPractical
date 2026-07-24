package com.example.practical3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practical3.ui.theme.Practical3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practical3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Bmi_Calculator(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Bmi_Calculator(modifier: Modifier = Modifier) {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BMI Calculator",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        InputRow(
            label = "Weight (kg)",
            value = weight,
            onValueChange = { weight = it }
        )
        InputRow(
            label = "Height (m)",
            value = height,
            onValueChange = { height = it }
        )

        Button(
            onClick = {
                val w = weight.toDoubleOrNull()
                val h = height.toDoubleOrNull()
                result = when {
                    w == null || h == null -> "Please enter valid numbers."
                    h <= 0.0 -> "Height must be greater than 0."
                    else -> {
                        val bmi = w / (h * h)
                        val category = when {
                            bmi < 18.5 -> "Underweight"
                            bmi < 25.0 -> "Normal weight"
                            bmi < 30.0 -> "Overweight"
                            else -> "Obese"
                        }
                        "BMI: ${"%.1f".format(bmi)} — $category"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate BMI")
        }

        if (result.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = result,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun InputRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(
            keyboardType =
                KeyboardType.Decimal
        ),
        singleLine = true,
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun BMICalculatorPreview() {
    Practical3Theme {
        Bmi_Calculator()
    }
}
