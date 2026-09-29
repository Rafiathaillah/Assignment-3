package com.example.assignment_3.color_word_match

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun ColorWordMatchApp(){
    var currState by remember { mutableStateOf(GameState.WELCOME)}
    var currScore by remember { mutableIntStateOf(0)}
    var currBestScore by remember { mutableIntStateOf(0)}

    when (currState){
        GameState.WELCOME -> {
            View(
                onStartClick = {
                    currState = GameState.COUNTDOWN
                }
            )
        }

        GameState.COUNTDOWN -> {
            CountDown(
                onCountdownFinish = {
                    currState = GameState.PLAY
                }
            )
        }

        GameState.PLAY -> {
            Play(
                currentScore = currScore,
                onScoreChange = {
                    newScore -> currScore = newScore
                    if (newScore > currBestScore){
                        currBestScore = newScore
                    }
                },
                onGameOver = {
                    currState = GameState.GAMEOVER
                }
            )
        }

        GameState.GAMEOVER -> {
            GameOver(
                score = currScore,
                bestScore = currBestScore,
                onRestartClick = {
                    currScore = 0
                    currState = GameState.COUNTDOWN
                },
                onExitClick = {
                    currScore = 0
                    currState = GameState.WELCOME
                }
            )
        }
    }

}

@Composable
fun View(onStartClick: () -> Unit){
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
            Text(
                "Welcome \nto \nColor Word Matching",
                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            Button(
                {
                    onStartClick()
                },
                colors = ButtonDefaults.buttonColors(Color(0xffbac8d1), Color.Black),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    focusedElevation = 0.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Text(
                    "Start Game",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun CountDown(onCountdownFinish: () -> Unit){
    var textToShow by remember { mutableStateOf("3")}

    LaunchedEffect(Unit) {
        delay(1000L.milliseconds)
        textToShow = "2"

        delay(1000L.milliseconds)
        textToShow = "1"

        delay(1000L.milliseconds)
        textToShow = "START!"

        delay(700L.milliseconds)
        onCountdownFinish()
    }
    Surface(
        color = Color(0xfffafafa),
        modifier = Modifier.fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = textToShow,
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun Play(
    currentScore: Int,
    onScoreChange: (Int) -> Unit,
    onGameOver: () -> Unit
){
    var lives by remember { mutableIntStateOf(0) }
    var questionIndex by remember { mutableIntStateOf(0) }
    var currQuestion by remember(questionIndex) { mutableStateOf(generateQuestion()) }
    var timeLeft by remember(questionIndex) { mutableIntStateOf(5) }

    LaunchedEffect(questionIndex){
        timeLeft = 5
        while (timeLeft > 0){
            delay(1000L.milliseconds)
            timeLeft--
        }

        lives++
        if (lives >= 3){
            onGameOver()
        } else {
            questionIndex++
        }
    }

    fun answerHandler(selectedAnswer: String){
        if (selectedAnswer == currQuestion.correctAnswer){
            onScoreChange(currentScore + 1)
        } else {
            lives++
        }

        if (lives >= 3){
            onGameOver()
        } else {
            questionIndex++
        }
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
                    "Mode: ${currQuestion.mode}"
                )

                Spacer(Modifier.weight(1f))

                Text(
                    "✅ $currentScore"
                )

                Spacer(Modifier.padding(10.dp))

                Text(
                    "❌ $lives/3"
                )
            }

            Spacer(Modifier.height(100.dp))

            Text(
                "${timeLeft}s",
                fontSize = 18.sp
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = currQuestion.wordOption.name,
                color = currQuestion.colorOption.color,
                fontSize = 52.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(150.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                currQuestion.options.forEach { option ->
                    Button(
                        onClick = { answerHandler(option) },
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
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GameOver(
    score: Int,
    bestScore: Int,
    onRestartClick: () -> Unit,
    onExitClick: () -> Unit
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
            Text(
                "Game Over!",
                fontSize = 36.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(40.dp))

            Text(
                "You're Score \n$score",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            Text(
                "Best Score \n$bestScore",
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            Button(
                {
                    onRestartClick()
                },
                colors = ButtonDefaults.buttonColors(Color(0xffbac8d1), Color.Black),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    focusedElevation = 0.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Text(
                    "Restart Game",
                    fontSize = 16.sp
                )
            }

            Spacer(Modifier.height(10.dp))

            Button(
                {
                    onExitClick()
                },
                colors = ButtonDefaults.buttonColors(Color(0xffbac8d1), Color.Black),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    focusedElevation = 0.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Text(
                    "Exit",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview(){
    ColorWordMatchApp()
}