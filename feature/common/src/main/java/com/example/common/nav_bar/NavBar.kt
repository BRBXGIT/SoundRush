package com.example.common.nav_bar

import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.common.state.CommonIntent
import com.example.common.state.CommonState
import com.example.common.state.CommonVM
import com.example.design_system.theme.mColors

@Composable
fun BoxScope.NavBar(
    commonVM: CommonVM,
    commonState: CommonState,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .height(calculateNavBarBottomPadding())
    ) {
        val currentTrack = commonState.currentTrack
        TrackBar(
            posterPath = currentTrack.posterPath,
            author = currentTrack.author,
            name = currentTrack.name,
            isPlaying = currentTrack.isPlaying,
            onPlayClick = { commonVM.sendIntent(CommonIntent.ChangeIsPlaying) }
        )

        NavigationBar {
            navItems.forEachIndexed { index, navItem ->
                NavBarItem(
                    index = index,
                    navItem = navItem,
                    isSelected = index == commonState.currentNavIndex,
                    onNavItemClick = { index, destination ->
                        commonVM.sendIntent(CommonIntent.SetNavIndex(index))
                    }
                )
            }
        }
    }
}

@Composable
private fun RowScope.NavBarItem(
    index: Int,
    navItem: NavItem,
    isSelected: Boolean,
    onNavItemClick: (Int, Any) -> Unit
) {
    var animatedSelection by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isSelected) {
        animatedSelection = isSelected
    }

    val animatedImage = AnimatedImageVector.animatedVectorResource(navItem.icon)
    val animatedPainter = rememberAnimatedVectorPainter(
        animatedImageVector = animatedImage,
        atEnd = animatedSelection
    )

    NavigationBarItem(
        modifier = Modifier.testTag(BarsUtils.NAV_BAR_ITEM_TEST_TAG),
        selected = isSelected,
        onClick = { if (!isSelected) onNavItemClick(index, navItem.destination) },
        icon = {
            Image(
                painter = animatedPainter,
                contentDescription = null,
                colorFilter = ColorFilter.tint(mColors.onSecondaryContainer),
            )
        },
        label = {
            Text(
                text = navItem.label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
        }
    )
}