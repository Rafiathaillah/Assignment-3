package com.example.assignment_3

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun ClickerView(){
    val cardShape = RoundedCornerShape(16.dp)
    var currCoin by remember { mutableDoubleStateOf(0.0) }
    var currCoinPerTap by remember { mutableDoubleStateOf(1.0) }
    var currCoinPerTapUpgrade by remember { mutableIntStateOf(10)}
    var isCatOpen by remember { mutableStateOf(false) }

    LaunchedEffect(isCatOpen) {
        if (isCatOpen){
            delay(100.milliseconds)
            isCatOpen = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(R.drawable.clicker),
            "Background App",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.3f), blendMode = BlendMode.Darken )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(Color.White.copy(alpha = 0.3f)),
                modifier = Modifier
                    .dropShadow(
                        shape = cardShape,
                        shadow = Shadow(
                            radius = 16.dp,
                            spread = 0.dp,
                            offset = DpOffset(0.dp, 8.dp),
                            color = Color.Black.copy(alpha = 0.25f)
                        )
                    )
                    .size(170.dp, 170.dp)
            ) {
                Column(
                    Modifier
                        .padding(15.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        "Your Coins",
                        Modifier.padding(bottom = 15.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )

                    Text(
                        "${currCoin.toInt()}",
                        Modifier.padding(bottom = 15.dp),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color(0xff00e779)
                    )

                    Text(
                        "${currCoinPerTap.toInt()} coins per tap",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.padding(vertical = 20.dp))

            Column(
                Modifier
                    .padding(15.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Tap the Cat!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Image(
                    painter = painterResource(
                        if (isCatOpen) R.drawable.cat_open else R.drawable.cat_close),
                    contentDescription = null,
                    Modifier
                        .padding(top = 15.dp)
                        .size(200.dp)
                        .clickable(
                            onClick = {
                                currCoin += currCoinPerTap
                                isCatOpen = true
                            }
                        )
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.FillBounds
                )

                Text(
                    if (isCatOpen) "MEOW!" else "Purr~",
                    Modifier.padding(top = 15.dp),
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 20.dp))

            Card(
                colors = CardDefaults.cardColors(Color.White),
                modifier = Modifier
                    .dropShadow(
                        shape = cardShape,
                        shadow = Shadow(
                            radius = 16.dp,
                            spread = 0.dp,
                            offset = DpOffset(0.dp, 8.dp),
                            color = Color.Black.copy(alpha = 0.25f)
                        )
                    )
                    .fillMaxWidth()
            ){
                Column(
                    Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        "Give Me Your Coin",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        "Next upgrade: +${(currCoinPerTap * 1.5).toInt()} coins per tap"
                    )

                    Spacer(Modifier.height(10.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        enabled = currCoin >= currCoinPerTapUpgrade,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff4cb050),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            currCoin -= currCoinPerTapUpgrade
                            currCoinPerTap *= 1.5
                            currCoinPerTapUpgrade *= 2
                        }
                    ) {
                        Text(
                            if (currCoin < currCoinPerTapUpgrade) {
                            "Find ${(currCoinPerTapUpgrade - currCoin).toInt()} more coins"
                            } else {
                            "Pay for $currCoinPerTapUpgrade coins"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClickerPreview(){
    ClickerView()
}
