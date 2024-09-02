package com.example.myprogressbar


import android.media.MediaPlayer
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin


@Preview(showBackground = true)
@Composable
fun CreativeProgressBarPreview() {
    Column(modifier = Modifier.fillMaxWidth()) {
        SuperCreativeProgressBar()
        EnhancedParticleProgressBar()

        Spacer(modifier = Modifier.height(16.dp))

       EnhancedWaveProgressBar()


        // Spacer(modifier = Modifier.height(16.dp))
    }
}





@Composable
fun SuperCreativeProgressBar() {
    var progress by remember { mutableStateOf(0f) }
    var isPaused by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val infiniteTransition = rememberInfiniteTransition()
    val rotation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val backgroundColor by animateColorAsState(
        targetValue = if (progress < 0.5f) Color.LightGray else Color.Green,
        animationSpec = tween(durationMillis = 500)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier
                .size(150.dp)
                .padding(16.dp)) {
                drawArc(
                    brush = Brush.sweepGradient(
                        colors = listOf(Color.Magenta, Color.Cyan, Color.Yellow, Color.Green)
                    ),
                    startAngle = 0f,
                    sweepAngle = 360 * progress,
                    useCenter = false,
                    style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round),
                    topLeft = Offset(0f, 0f),
                    size = Size(size.width, size.height)
                )
                drawArc(
                    color = Color.Gray.copy(alpha = 0.3f),
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round),
                    topLeft = Offset(0f, 0f),
                    size = Size(size.width, size.height)
                )
            }
            Text(
                text = "${(progress * 100).toInt()}%",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = {
                scope.launch {
                    progress = 0f // Reset progress
                    while (progress < 1f) {
                        if (!isPaused) {
                            progress += 0.01f
                            delay(100)
                        }
                    }
                }
            }) {
                Text("Start Progress")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { isPaused = !isPaused }) {
                Text(if (isPaused) "Resume" else "Pause")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = if (progress < 0.5f) R.drawable.baseline_sen else R.drawable.baseline_ha),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
    }
}


@Composable
fun EnhancedWaveProgressBar() {
    var progress by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()
    val infiniteTransition = rememberInfiniteTransition()
    val waveOffset = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp)) {
                val wavePath = Path().apply {
                    val waveHeight = size.height * progress
                    val waveLength = size.width / 2
                    moveTo(0f, size.height)
                    for (x in 0..size.width.toInt()) {
                        val y = waveHeight * kotlin.math.sin((x + waveOffset.value * waveLength) * (2 * kotlin.math.PI / waveLength)).toFloat()
                        lineTo(x.toFloat(), size.height - y)
                    }
                    lineTo(size.width, size.height)
                    close()
                }
                drawPath(
                    path = wavePath,
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Cyan, Color.Blue)
                    )
                )
            }
            Text(
                text = "${(progress * 100).toInt()}%",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            scope.launch {
                progress = 0f // Reset progress
                while (progress < 1f) {
                    progress += 0.01f
                    delay(100)
                }
            }
        }) {
            Text("Start Progress")
        }
    }
}

fun createStarPath(points: Int, outerRadius: Float, innerRadius: Float): Path {
    val path = Path()
    val angle = Math.PI / points
    for (i in 0 until 2 * points) {
        val r = if (i % 2 == 0) outerRadius else innerRadius
        val x = (r * cos(i * angle)).toFloat()
        val y = (r * sin(i * angle)).toFloat()
        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()
    return path
}

@Composable
fun EnhancedParticleProgressBar() {
    var progress by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()
    val infiniteTransition = rememberInfiniteTransition()
    val particleAnimation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val colorTransition = infiniteTransition.animateColor(
        initialValue = Color.Cyan,
        targetValue = Color.Magenta,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val backgroundColor by animateColorAsState(
        targetValue = if (progress < 0.5f) Color(0xFFBBDEFB) else Color(0xFF8E24AA),
        animationSpec = tween(durationMillis = 500)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier
                .size(200.dp)
                .padding(16.dp)) {
                val radius = size.minDimension / 2
                val particleCount = 50
                val angleStep = 360f / particleCount

                // Draw progress bar with gradient
                drawArc(
                    brush = Brush.sweepGradient(
                        colors = listOf(Color.Cyan, Color.Blue, Color.Magenta)
                    ),
                    startAngle = -90f,
                    sweepAngle = 360 * progress,
                    useCenter = false,
                    style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
                )

                // Draw multi-layered particles (stars)
                for (i in 0 until particleCount) {
                    val angle = angleStep * i + particleAnimation.value * 360
                    val x = radius + radius * cos(Math.toRadians(angle.toDouble())).toFloat()
                    val y = radius + radius * sin(Math.toRadians(angle.toDouble())).toFloat()
                    val starPath = createStarPath1(5, 10f, 5f)
                    translate(left = x, top = y) {
                        drawPath(
                            path = starPath,
                            color = colorTransition.value,
                            style = Stroke(width = 2.dp.toPx())
                        )
                    }
                }
            }
            Text(
                text = "${(progress * 100).toInt()}%",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            scope.launch {
                progress = 0f // Reset progress
                while (progress < 1f) {
                    progress += 0.01f
                    delay(100)
                }
            }
        }) {
            Text("Start Progress")
        }
    }
}

fun createStarPath1(points: Int, outerRadius: Float, innerRadius: Float): Path {
    val path = Path()
    val angle = Math.PI / points
    for (i in 0 until 2 * points) {
        val r = if (i % 2 == 0) outerRadius else innerRadius
        val x = (r * cos(i * angle)).toFloat()
        val y = (r * sin(i * angle)).toFloat()
        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()
    return path
}


@Composable
fun CreativeRadialProgressBar() {
    var progress by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()
    val infiniteTransition = rememberInfiniteTransition()
    val rotation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(modifier = Modifier
            .size(200.dp)
            .padding(16.dp)) {
            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(Color.Magenta, Color.Cyan, Color.Yellow, Color.Green)
                ),
                startAngle = 0f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round),
                topLeft = Offset(0f, 0f),
                size = Size(size.width, size.height)
            )
            drawArc(
                color = Color.Gray.copy(alpha = 0.3f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round),
                topLeft = Offset(0f, 0f),
                size = Size(size.width, size.height)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            scope.launch {
                progress = 0f // Reset progress
                while (progress < 1f) {
                    progress += 0.01f
                    delay(100)
                }
            }
        }) {
            Text("Start Progress")
        }
    }
}
@Composable
fun EnhancedRadialProgressBar() {
    var progress by remember { mutableStateOf(0f) }
    var isPaused by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val infiniteTransition = rememberInfiniteTransition()
    val rotation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Column(
        modifier = Modifier.size(200.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier
                .size(200.dp)
                .padding(16.dp)) {
                drawArc(
                    brush = Brush.sweepGradient(
                        colors = listOf(Color.Magenta, Color.Cyan, Color.Yellow, Color.Green)
                    ),
                    startAngle = 0f,
                    sweepAngle = 360 * progress,
                    useCenter = false,
                    style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round),
                    topLeft = Offset(0f, 0f),
                    size = Size(size.width, size.height)
                )
                drawArc(
                    color = Color.Gray.copy(alpha = 0.3f),
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round),
                    topLeft = Offset(0f, 0f),
                    size = Size(size.width, size.height)
                )
            }
            Text(
                text = "${(progress * 100).toInt()}%",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = {
                scope.launch {
                    progress = 0f // Reset progress
                    while (progress < 1f && !isPaused) {
                        progress += 0.01f
                        delay(100)
                    }
                }
            }) {
                Text("Start Progress")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { isPaused = !isPaused }) {
                Text(if (isPaused) "Resume" else "Pause")
            }
        }
    }
}
@Composable
fun ProgressBarWithTooltips() {
    var progress by remember { mutableStateOf(0f) }
    var showTooltip by remember { mutableStateOf(false) }
    var tooltipText by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        showTooltip = true
                        tooltipText = "Progress: ${(progress * 100).toInt()}%"
                    }
                )
            }
        ) {
            LinearProgressIndicator(progress = progress, modifier = Modifier
                .fillMaxWidth()
                .height(8.dp))
            if (showTooltip) {
                Popup(alignment = Alignment.TopCenter, offset = IntOffset(0, -50)) {
                    Box(
                        modifier = Modifier
                            .background(Color.Gray)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = tooltipText, fontSize = 14.sp, color = Color.White)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            scope.launch {
                progress = 0f // Reset progress
                while (progress < 1f) {
                    progress += 0.01f
                    delay(100)
                }
                showTooltip = false
            }
        }) {
            Text("Start Progress")
        }
    }
}

@Composable
fun CustomShapeParticleProgressBar() {
    var progress by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()
    val infiniteTransition = rememberInfiniteTransition()
    val particleAnimation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier
                .size(200.dp)
                .padding(16.dp)) {
                val radius = size.minDimension / 2
                val particleCount = 50
                val angleStep = 360f / particleCount

                // Draw progress bar with gradient
                drawArc(
                    brush = Brush.sweepGradient(
                        colors = listOf(Color.Cyan, Color.Blue, Color.Magenta)
                    ),
                    startAngle = -90f,
                    sweepAngle = 360 * progress,
                    useCenter = false,
                    style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
                )

                // Draw custom shape particles (stars)
                for (i in 0 until particleCount) {
                    val angle = angleStep * i + particleAnimation.value * 360
                    val x = radius + radius * cos(Math.toRadians(angle.toDouble())).toFloat()
                    val y = radius + radius * sin(Math.toRadians(angle.toDouble())).toFloat()
                    val starPath = createStarPath(5, 10f, 5f)
                    translate(left = x, top = y) {
                        drawPath(
                            path = starPath,
                            color = Color.Magenta
                        )
                    }
                }
            }
            Text(
                text = "${(progress * 100).toInt()}%",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            scope.launch {
                progress = 0f // Reset progress
                while (progress < 1f) {
                    progress += 0.01f
                    delay(100)
                }
            }
        }) {
            Text("Start Progress")
        }
    }
}
