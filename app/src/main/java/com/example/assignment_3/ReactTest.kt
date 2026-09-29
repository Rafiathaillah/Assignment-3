package com.example.assignment_3

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun TrialBox(reactTimes: List<Long>, showAvg: Boolean = false){
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xffEFF5ED),
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                "Trial Results",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Text(
                        "1",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if(reactTimes.isNotEmpty()) Color(0xFF4CAF50) else Color.Gray
                    )
                    Text(
                        reactTimes.getOrNull(0)?.let { "${it}ms" } ?: "-",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "2",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (reactTimes.size >= 2) Color(0xFF4CAF50) else Color.Gray
                    )
                    Text(
                        text = reactTimes.getOrNull(1)?.let { "${it}ms" } ?: "-",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "3",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (reactTimes.size >= 3) Color(0xFF4CAF50) else Color.Gray
                    )
                    Text(
                        text = reactTimes.getOrNull(2)?.let { "${it}ms" } ?: "-",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                }

                if (showAvg && reactTimes.isNotEmpty()){
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        Text(
                            "Average",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2196F3)
                        )
                        Text(
                            "${reactTimes.average().toInt()}ms",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun ReactTestApp(){
    var gameState by remember { mutableStateOf("START") }
    var currentTrial by remember { mutableIntStateOf(1) }
    var reactTimes by remember { mutableStateOf(listOf<Long>())}
    var startTime by remember { mutableStateOf(0L) }
    var lastReactTime by remember { mutableLongStateOf(0L) }

    fun resetGame(){
        currentTrial = 1
        reactTimes = emptyList()
        gameState = "WAITING"
    }
    when (gameState){
        "START" -> {
            ReactTestView(
                reactTimes = reactTimes,
                onStartClick = { resetGame() }
            )
        }
        "WAITING" -> {
            ReactTestWaiting(
                onTimeUp = {
                    startTime = System.currentTimeMillis()
                    gameState = "GO"
                },
                onTooEarlyClick = { gameState = "FAIL"}
            )
        }
        "GO" -> {
            ReactTestStart(
                onFinishClick = {
                    val timeTaken = System.currentTimeMillis() - startTime
                    lastReactTime = timeTaken
                    reactTimes = reactTimes + lastReactTime + timeTaken

                    if (currentTrial < 3){
                        gameState = "TRIAL_RESULT"
                    } else {
                        val avg = reactTimes.average()
                        gameState = when{
                            avg < 180 -> "SUPER_FAST"
                            avg < 280 -> "FAST"
                            avg < 450 -> "AVERAGE"
                            else -> "SLOW"
                        }
                    }
                    gameState = "START"
                }
            )
        }
        "TRIAL_RESULT" -> {
            ReactTestTrialResult(
                trialNumber = currentTrial,
                timeMs = lastReactTime,
                reactTimes = reactTimes,
                onNextClick = {
                    currentTrial++
                    gameState = "WAITING"
                }
            )
        }
        "FAIL" -> {
            ReactTestEarly(
                reactTimes = reactTimes,
                onTooEarlyClick = {gameState = "START"}
            )
        }
        "SUPER_FAST" -> ReactTestSuperFast()
        "FAST" -> ReactTestFast()
        "AVERAGE" -> ReactTestAverage()
        "SLOW" -> ReactTestSlow()
    }
}

@Composable
fun ReactTestView(reactTimes: List<Long>, onStartClick: () -> Unit){
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable{
                    onStartClick()
                }
                .background(Color(0xff72cedd)),
            contentAlignment = Alignment.Center
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    "Reaction Test",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Icon(
                    painter = painterResource(R.drawable.thunder),
                    contentDescription = "Ligtning",
                    modifier = Modifier.size(300.dp),
                    tint = Color.White
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Text(
                    "Click Anywhere on The Screen",
                    fontSize = 14.sp,
                    color = Color.White
                )

                if(reactTimes.isNotEmpty()){
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    TrialBox(reactTimes = reactTimes)
                }
            }
        }
    }
}

@Composable
fun ReactTestWaiting(onTimeUp: () -> Unit, onTooEarlyClick: () -> Unit){
    LaunchedEffect(Unit){
        val randomDelayMs = Random.nextLong(500L, 4500L)
        delay(randomDelayMs.milliseconds)
        onTimeUp()
    }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xffdbdbdb))
                .clickable{
                    onTooEarlyClick()
                },
            contentAlignment = Alignment.Center
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    "Get Ready...",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Icon(
                    painter = painterResource(R.drawable.warning),
                    contentDescription = "Ligtning",
                    modifier = Modifier.size(300.dp),
                    tint = Color.White
                )

                Spacer(modifier = Modifier.padding(vertical = 15.dp))

                Text(
                    "Wait For The Green Light",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.padding(vertical = 15.dp))

                Text(
                    "DON'T CLICK YET!",
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ReactTestStart(onFinishClick: () -> Unit){
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xff4cb050))
                .clickable{
                    onFinishClick()
                },
            contentAlignment = Alignment.Center
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    "GO!",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.padding(vertical = 15.dp))

                Icon(
                    painter = painterResource(R.drawable.run),
                    contentDescription = "Ligtning",
                    modifier = Modifier.size(280.dp),
                    tint = Color.White
                )

                Spacer(modifier = Modifier.padding(vertical = 15.dp))

                Text(
                    "CLICK NOW!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.padding(vertical = 15.dp))

                Text(
                    "TAP AS FAST AS YOU CAN!",
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ReactTestEarly(reactTimes: List<Long>, onTooEarlyClick: () -> Unit){
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xfffe4445))
                .clickable{
                    onTooEarlyClick()
                },
            contentAlignment = Alignment.Center
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    "FAIL!",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.padding(vertical = 15.dp))

                Icon(
                    painter = painterResource(R.drawable.dislike),
                    contentDescription = "Ligtning",
                    modifier = Modifier.size(280.dp),
                    tint = Color.White
                )

                Spacer(modifier = Modifier.padding(vertical = 15.dp))

                Text(
                    "You click too early. TRY TO READ THE RULES BRO",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.padding(vertical = 15.dp))

                Text(
                    "TRY AGAIN",
                    fontSize = 14.sp,
                    color = Color.White
                )

                if (reactTimes.isNotEmpty()){
                    Spacer(modifier = Modifier.padding(10.dp))
                    TrialBox(reactTimes = reactTimes)
                }
            }
        }
    }
}

@Composable
fun ReactTestTrialResult(trialNumber: Int, timeMs: Long, reactTimes: List<Long>, onNextClick: () -> Unit){

}

@Composable
fun ReactTestSuperFast(){

}

@Composable
fun ReactTestFast(){

}

@Composable
fun ReactTestAverage(){

}

@Composable
fun ReactTestSlow(){
    
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReactTestPreview(){
    ReactTestApp()
}