package com.example.assignment_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment_3.ui.theme.Assignment3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding->
                    TestView(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TestView(modifier: Modifier = Modifier){
    var count by rememberSaveable{ mutableIntStateOf(0) }
    var text by rememberSaveable { mutableStateOf("Count: ") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "$count",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = modifier.padding(top = 10.dp)
        ) {
            Button(
                onClick = {
                    count--
                }
            ) {
                Text("Decrease Count")
            }

            Spacer(modifier.padding(horizontal = 10.dp))

            Button(
                onClick = {
                    count++
                }
            ){
                Text("Increase Count")
            }
        }

        Spacer(modifier.padding(vertical = 10.dp))

        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = {
                Text("Title")
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TestPreview(){
    Assignment3Theme {
        TestView()
    }
}