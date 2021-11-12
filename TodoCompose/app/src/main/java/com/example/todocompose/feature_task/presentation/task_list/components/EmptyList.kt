package com.example.todocompose.feature_task.presentation.task_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.todocompose.R

@Composable
fun EmptyList() {

    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.task_anim))
    val textContentId = "inlineContent"
    val text = buildAnnotatedString {
        append(stringResource(id = R.string.empty_list_text1) + "\n")
        append(stringResource(id = R.string.empty_list_text2) + " ")
        appendInlineContent(textContentId, "[icon]")
        append(" " + stringResource(id = R.string.empty_list_text3))
    }

    val inlineContent = mapOf(
        Pair(
            textContentId,
            InlineTextContent(
                Placeholder(
                    width = 12.sp,
                    height = 12.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.AboveBaseline
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add Task Icon",
                    tint = Color.Unspecified
                )
            }
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (composition.isComplete) {
            EmptyTaskAnim(composition.value)
            Text(
                text = text,
                fontWeight = FontWeight.Medium,
                inlineContent = inlineContent,
                color = Color.Gray,
                lineHeight = 23.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
private fun EmptyTaskAnim(composition: LottieComposition?) {
    LottieAnimation(
        composition = composition,
        modifier = Modifier
            .size(180.dp)
            .offset(y = (-20).dp),
        iterations = LottieConstants.IterateForever
    )
}

@Preview
@Composable
fun EmptyListPreview() {
    EmptyList()
}