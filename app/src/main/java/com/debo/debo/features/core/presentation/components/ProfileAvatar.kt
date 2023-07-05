package com.debo.debo.features.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun ProfileAvatar(imageUrl: String?, modifier: Modifier = Modifier, size: Dp?) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Box {
        GlideImage(
            imageModel = { imageUrl.orEmpty() },
            modifier = modifier
                .size(size ?: (screenWidth * 0.7).dp)
                .clip(CircleShape.copy(CornerSize(32.dp))),
        )
    }
}