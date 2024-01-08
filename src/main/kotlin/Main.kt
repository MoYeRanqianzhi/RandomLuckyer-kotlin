import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Crop54
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random


object AppFonts {
    object HarmonyOSSans {
        val SC = FontFamily(
            Font("font/HarmonyOS_Sans_SC_Black.ttf", FontWeight.Black),
            Font("font/HarmonyOS_Sans_SC_Bold.ttf", FontWeight.Bold),
            Font("font/HarmonyOS_Sans_SC_Medium.ttf", FontWeight.Medium),
            Font("font/HarmonyOS_Sans_SC_Regular.ttf", FontWeight.Normal),
        )
    }
}

@Composable
@Preview
fun App(
    scope: CoroutineScope,
    snack: SnackbarHostState,
    student: List<String>
) {
    val survivor = student.toMutableList()
    val rememberSurvivorNumber = remember { mutableIntStateOf(survivor.size) }
    val rememberLuckyer = remember { mutableStateOf("RL") }
    val survivorNumber by rememberSurvivorNumber
    val luckyer by rememberLuckyer

        Box {
            Luckyer(scope, snack, student, survivor, rememberSurvivorNumber, rememberLuckyer)
        }
    }

@Composable
@Preview
fun Luckyer(
    scope: CoroutineScope,
    snack: SnackbarHostState,
    student: List<String>,
    survivor: MutableList<String>,
    rememberSurvivorNumber: MutableState<Int>,
    rememberLuckyer: MutableState<String>
) {
    var survivorNumber by rememberSurvivorNumber
    var luckyer by rememberLuckyer

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "剩余${survivorNumber}幸运儿",
                    modifier = Modifier
                        .align(Alignment.Center),
                    fontSize = 16.sp,
                    fontFamily = AppFonts.HarmonyOSSans.SC,
                    fontWeight = FontWeight.Medium
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = luckyer,
                    modifier = Modifier
                        .align(Alignment.Center),
                    fontSize = 120.sp,
                    fontFamily = AppFonts.HarmonyOSSans.SC,
                    fontWeight = FontWeight.Black
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        luckyer = survivor.removeAt(
                            Random.nextInt(
                                from = 0,
                                until = survivor.size
                            )
                        )

                        if (survivor.size == 0) {
                            survivor.addAll(student)
                            scope.launch {
                                snack.showSnackbar(
                                    "奖池已见底啦~新的一批幸运儿已刷新~尽情的抽取吧!"
                                )
                            }
                        }

                        survivorNumber = survivor.size
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    Text(
                        text = "抽取同学",
                        fontSize = 24.sp,
                        fontFamily = AppFonts.HarmonyOSSans.SC,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    val undecoratedState = remember { mutableStateOf(true) }
    val transparentState = remember { mutableStateOf(true) }
    val windowState = rememberWindowState(
        width = 300.dp,
        height = 300.dp
    )
    val undecorated by undecoratedState
    val transparent by transparentState

    var inMinimize by remember { mutableStateOf(false) }
    var inMaximize by remember { mutableStateOf(false) }
    var inExit by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val snack = remember { SnackbarHostState() }

    Window(
        onCloseRequest = ::exitApplication,
        title = "RandomLuckyer",
        icon = painterResource("luckyer.png"),
        state = windowState,
        undecorated = undecorated,
        transparent = transparent,
        resizable = false
    ) {
        MaterialTheme {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.8f),
                shape = if (undecorated) {
                    MaterialTheme.shapes.extraLarge
                } else {
                    MaterialTheme.shapes.medium
                }
            ) {
                Column {
                    WindowDraggableArea(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .padding(24.dp, 6.dp)
                                .background(MaterialTheme.colorScheme.background)
                        ) {
                            Text(
                                text = "RandomLuckyer",
                                modifier = Modifier
                                    .align(Alignment.CenterStart),
                                fontSize = 16.sp,
                                fontFamily = AppFonts.HarmonyOSSans.SC,
                                fontWeight = FontWeight.Bold
                            )

                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                            ) {
                                Row {
                                    Box(
                                        modifier = Modifier
                                            .padding(6.dp, 0.dp)
                                            .align(Alignment.CenterVertically)
                                    ) {
                                        IconButton(
                                            onClick = {
                                                windowState.isMinimized = true
                                            },
                                            modifier = Modifier
                                                .size(18.dp)
                                                .clip(CircleShape)
                                                .onPointerEvent(PointerEventType.Enter) {
                                                    inMinimize = true
                                                }
                                                .onPointerEvent(PointerEventType.Exit) {
                                                    inMinimize = false
                                                },
                                            colors = IconButtonDefaults.iconButtonColors(
                                                containerColor = Color(0xFF3DE1AD),
                                            )
                                        ) {
                                            if (inMinimize) {
                                                Icon(
                                                    Icons.Outlined.Remove,
                                                    contentDescription = "Minimize",
                                                    modifier = Modifier
                                                        .size(12.dp),
                                                    tint = Color.Black
                                                )
                                            }
                                        }
                                    }
                                    Box(
                                        modifier = Modifier
                                            .padding(6.dp, 0.dp)
                                            .align(Alignment.CenterVertically)
                                    ) {
                                        IconButton(
                                            onClick = {
                                                scope.launch {
                                                    snack.showSnackbar("嘿嘿~没想到吧, 我是装饰品~")
                                                }
                                            },
                                            modifier = Modifier
                                                .size(18.dp)
                                                .clip(CircleShape)
                                                .onPointerEvent(PointerEventType.Enter) {
                                                    inMaximize = true
                                                }
                                                .onPointerEvent(PointerEventType.Exit) {
                                                    inMaximize = false
                                                },
                                            colors = IconButtonDefaults.iconButtonColors(
                                                containerColor = Color(0xFFFF7500),
                                            )
                                        ) {
                                            if (inMaximize) {
                                                Icon(
                                                    Icons.Outlined.Crop54,
                                                    contentDescription = "Maximize-disabled",
                                                    modifier = Modifier
                                                        .size(12.dp),
                                                    tint = Color.Black
                                                )
                                            }
                                        }
                                    }
                                    Box(
                                        modifier = Modifier
                                            .padding(6.dp, 0.dp)
                                            .align(Alignment.CenterVertically)
                                    ) {
                                        IconButton(
                                            onClick = {
                                                exitApplication()
                                            },
                                            modifier = Modifier
                                                .size(18.dp)
                                                .clip(CircleShape)
                                                .onPointerEvent(PointerEventType.Enter) {
                                                    inExit = true
                                                }
                                                .onPointerEvent(PointerEventType.Exit) {
                                                    inExit = false
                                                },
                                            colors = IconButtonDefaults.iconButtonColors(
                                                containerColor = Color(0xFFFF00056),
                                            )
                                        ) {
                                            if (inExit) {
                                                Icon(
                                                    Icons.Outlined.Close,
                                                    contentDescription = "Exit",
                                                    modifier = Modifier
                                                        .size(12.dp),
                                                    tint = Color.Black
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Scaffold(
                        snackbarHost = {
                            SnackbarHost(hostState = snack)
                        }
                    ) {
                        App(
                            scope,
                            snack,
                            IntRange(1, 50).map {
                                it.toString()
                            }
                        )
                    }
                }
            }
        }
    }
}
