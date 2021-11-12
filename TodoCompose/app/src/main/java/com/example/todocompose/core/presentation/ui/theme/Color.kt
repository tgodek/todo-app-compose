package com.example.todocompose.core.presentation.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)


val SealBrown = Color(0xFF591f0A)
val TeneOrange = Color(0xFFd65108)
val MariGold = Color(0xFFEFA00B)
val FrenchBlue = Color(0xFF0075C4)
val GreenBlue = Color(0xFF0267c1)

val SnowWhite = Color(0xFFFBFBFB)
val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)

val LowPriorityColor = Color(0xFF00C980)
val MediumPriorityColor = Color(0xFFFFC114)
val HighPriorityColor = Color(0XFFFF4646)
val NonePriorityColor = Color(0xFFFFFFFF)

val Colors.iconColor: Color
    @Composable
    get() = if (isLight) Color.Black else SnowWhite

val Colors.inputTextColor: Color
    @Composable
    get() = if (isLight) Color.DarkGray else SnowWhite

val Colors.splashScreenBackground: Color
    @Composable
    get() = if (isLight) Purple700 else Color.Black

val Colors.taskItemTextColor: Color
    @Composable
    get() = if (isLight) DarkGray else LightGray

val Colors.taskItemBackgroundColor: Color
    @Composable
    get() = if (isLight) Color.White else DarkGray

val Colors.fabBackgroundColor: Color
    @Composable
    get() = if (isLight) Teal200 else Purple700

val Colors.fabIconColor: Color
    @Composable
    get() = if (isLight) Color.White else Color.Black