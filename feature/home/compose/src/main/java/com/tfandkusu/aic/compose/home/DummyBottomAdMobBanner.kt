package com.tfandkusu.aic.compose.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tfandkusu.aic.home.compose.R
import com.tfandkusu.aic.ui.theme.MyAppTheme

@Composable
fun DummyBottomAdMobBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.ad_background))
            .padding(top = 1.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        DummyAdMobBanner()
    }
}

@Composable
@Preview
fun DummyBottomAdMobBannerPreview() {
    MyAppTheme {
        DummyBottomAdMobBanner()
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun DummyBottomAdMobBannerPreviewDark() {
    MyAppTheme {
        DummyBottomAdMobBanner()
    }
}
