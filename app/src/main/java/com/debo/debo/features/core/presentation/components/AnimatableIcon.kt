package com.debo.debo.features.core.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val ICON_SIZE = 24.dp

@Composable
fun AnimatableIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    size: Dp = ICON_SIZE,
    scale: Float = 1f,
    contentDescription: String,
    onClick: () -> Unit
) {
    val animatedScale: Float by animateFloatAsState(
        targetValue = scale,
        animationSpec = TweenSpec(durationMillis = 2000, easing = FastOutSlowInEasing)
    )

    val animatedColor by animateColorAsState(
        targetValue = color,
        animationSpec = TweenSpec(durationMillis = 2000, easing = FastOutSlowInEasing)
    )

    IconButton(onClick = onClick, modifier = modifier.size(size)) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = animatedColor,
            modifier = modifier.scale(animatedScale)
        )
    }
}

@Preview
@Composable
fun PreviewIcon() {
    Surface {
        var selected by remember {
            mutableStateOf(false)
        }

        AnimatableIcon(
            imageVector = Icons.Default.Home,
            contentDescription = "Home",
            scale = if (selected) 1.5f else 1f,
            onClick = {
                selected = !selected
            })

    }
}