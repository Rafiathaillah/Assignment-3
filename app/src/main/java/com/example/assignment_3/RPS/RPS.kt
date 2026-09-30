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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun RpsApp(){
    var currState by rememberSaveable { mutableStateOf(GameState.INITIAL)}
    var currScore by rememberSaveable { mutableIntStateOf(0) }
    var currEnemyScore by rememberSaveable { mutableIntStateOf(0) }
    var userPick by rememberSaveable { mutableStateOf<RPSPick?>(null) }
    var enemyPick by rememberSaveable { mutableStateOf<RPSPick?>(null) }
    var lastResult by rememberSaveable {mutableStateOf<Result?>(null)}
    var bestScore by rememberSaveable { mutableIntStateOf(0) }

    val targetScore = 3

    fun pickHandler(pick: RPSPick){
        val enemy = RPSPick.entries.random()
        val result = pick.battle(enemy)
        userPick = pick
        enemyPick = enemy
        lastResult = result

        when (result){
            Result.WIN -> currScore++
            Result.LOSE -> currEnemyScore++
            Result.DRAW -> {}
        }

        currState = GameState.REVEAL
    }
    
    fun resetGame(){
        currScore = 0
        currEnemyScore = 0
        userPick = null
        enemyPick = null
        lastResult = null
        currState = GameState.PICK
    }

    when(currState){
        GameState.INITIAL -> {
            View(
                onStart = {
                    resetGame()
                }
            )
        }

        GameState.PICK -> {
            Pick(
                currScore,
                currEnemyScore,
                onPick = { pick ->
                    pickHandler(pick)
                }
            )
        }

        GameState.REVEAL -> {
            Reveal(
                currentScore = currScore,
                currentEnemyScore = currEnemyScore,
                userPick = userPick,
                enemyPick = enemyPick,
                result = lastResult,
                onTimeOut = {
                    if (currScore >= targetScore || currEnemyScore >= targetScore){
                        if (currScore > currEnemyScore){
                            bestScore = currScore
                        }
                        currState = GameState.FINISHED
                    } else {
                        currState = GameState.PICK
                    }
                }
            )
        }

        GameState.FINISHED -> {
            Finished(
                currentScore = currScore,
                currentEnemyScore = currEnemyScore,
                bestScore = bestScore,
                onRestart = {
                    resetGame()
                },
                onExit = {
                    currScore = 0
                    currEnemyScore = 0
                    currState = GameState.INITIAL
                }
            )
        }
    }
}

@Composable
fun View(onStart: () -> Unit){
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
                    "Best of 5"
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
                    onStart()
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
fun Pick(
    currentScore: Int,
    currentEnemyScore: Int,
    onPick: (RPSPick) -> Unit
){
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
                    "🧑🏻 $currentScore - $currentEnemyScore 🤖"
                )

                Spacer(Modifier.weight(1f))

                Text(
                    "Best of 5"
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
                val options = remember { RPSPick.entries.toList()}
                options.forEach { option ->
                    Button(
                        onClick = {
                            onPick(option)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBAC8D1),
                            contentColor = Color.Black
                        ),
                        modifier = Modifier
                            .padding(horizontal = 4.dp),
                        shape = RoundedCornerShape(30.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp,
                            focusedElevation = 0.dp,
                            pressedElevation = 0.dp
                        )
                    ){
                        Text(
                            option.displayText,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Reveal(
    currentScore: Int,
    currentEnemyScore: Int,
    userPick: RPSPick?,
    enemyPick: RPSPick?,
    result: Result?,
    onTimeOut: () -> Unit
){
    LaunchedEffect(userPick, enemyPick) {
        delay(2000L.milliseconds)
        onTimeOut()
    }

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
                    "🧑🏻 $currentScore - $currentEnemyScore 🤖"
                )

                Spacer(Modifier.weight(1f))

                Text(
                    "Best of 5"
                )
            }

            Spacer(Modifier.height(70.dp))

            Text(
                "${userPick?.emoji} VS ${enemyPick?.emoji}",
                fontSize = 36.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(50.dp))

            val resultText = when (result){
                Result.WIN -> "You Win!"
                Result.LOSE -> "You Lose!"
                Result.DRAW -> "Draw!"
                null -> ""
            }

            Text(
                resultText,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Finished(
    currentScore: Int,
    currentEnemyScore: Int,
    bestScore: Int,
    onRestart: () -> Unit,
    onExit: () -> Unit
){
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
                    "🧑🏻 $currentScore - $currentEnemyScore 🤖"
                )

                Spacer(Modifier.weight(1f))

                Text(
                    "Best of 5"
                )
            }

            Spacer(Modifier.height(100.dp))

            Text(
                "You Win the Match!",
                fontSize = 26.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            Text(
                "Best Score: $bestScore",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(50.dp))

            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Button(
                    onClick = {
                        onRestart()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBAC8D1),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(30.dp)
                ){
                    Text(
                        "Restart",
                        fontSize = 16.sp
                    )
                }

                Button(
                    onClick = {
                        onExit()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBAC8D1),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(30.dp)
                ){
                    Text(
                        "Exit",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview(){
    RpsApp()
}