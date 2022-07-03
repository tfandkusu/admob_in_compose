package com.tfandkusu.aic.compose.home.listitem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tfandkusu.aic.compose.home.AdViewRecycler
import com.tfandkusu.aic.compose.home.DummyAdMobBanner
import com.tfandkusu.aic.compose.home.RecyclableAdMobBannerAndroidView
import com.tfandkusu.aic.ui.theme.MyAppTheme

@Composable
fun AdListItem(adViewRecycler: AdViewRecycler, index: Int, isPreview: Boolean = false) {
    Column {
        Box(
            Modifier
                .fillMaxWidth()
                .height(82.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isPreview) {
                DummyAdMobBanner()
            } else {
                RecyclableAdMobBannerAndroidView(adViewRecycler, index)
            }
        }
        Divider()
    }
}

@Composable
@Preview
fun AdListItemPreview() {
    val adViewRecycler = AdViewRecycler()
    MyAppTheme {
        AdListItem(adViewRecycler, index = 0, isPreview = true)
    }
}
