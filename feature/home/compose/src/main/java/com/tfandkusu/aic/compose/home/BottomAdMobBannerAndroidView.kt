package com.tfandkusu.aic.compose.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.tfandkusu.aic.home.compose.R

@Composable
fun BottomAdMobBannerAndroidView() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.ad_background))
            .padding(top = 1.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        AdMobBannerAndroidView()
    }
}
