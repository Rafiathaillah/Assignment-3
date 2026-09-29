package com.example.assignment_3.RPS

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RpsApp(){
    var currState by remember { mutableStateOf(GameState.PICK)}

    when(currState){
        GameState.INITIAL -> {
            View()
        }

        GameState.PICK -> {
            Pick()
        }

        GameState.REVEAL -> {
            Reveal()
        }
    }
}

@Composable
fun View(){
    Surface(
        color = Color(0xfffafafa),
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    "🧑🏻 0 - 0 🤖"
                )

                Spacer(Modifier.weight(1f))

                Text(
                    "Best of 3"
                )
            }

            Spacer(Modifier.height(150.dp))

            Text(
                "Rock • Paper • Scissors",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(50.dp))

            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBAC8D1),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .width(200.dp),
                shape = RoundedCornerShape(30.dp)
            ){
                Text(
                    "Start",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun Pick(){
    Surface(
        color = Color(0xfffafafa),
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    "🧑🏻 0 - 0 🤖"
                )

                Spacer(Modifier.weight(1f))

                Text(
                    "Best of 3"
                )
            }

            Spacer(Modifier.height(70.dp))

            Text(
                "Pick your move!",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            Text(
                "❔ VS ❔",
                fontSize = 36.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(50.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                val options = listOf(RPSPick.ROCK.name, RPSPick.PAPER.name, RPSPick.SCISSOR.name).shuffled()
                options.forEach { option ->
                    Button(
                        onClick = {

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBAC8D1),
                            contentColor = Color.Black
                        ),
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                        shape = RoundedCornerShape(30.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp,
                            focusedElevation = 0.dp,
                            pressedElevation = 0.dp
                        )
                    ){
                        Text(
                            option,
                            fontSize = 16.sp
                        )
                    }
                }
            }



        }
    }
}

@Composable
fun Reveal(){}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview(){
    RpsApp()
}