package com.tfandkusu.aic.compose.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tfandkusu.aic.home.compose.R
import com.tfandkusu.aic.ui.theme.MyAppTheme

@Composable
fun DummyAdMobBanner() {
    Box(
        modifier = Modifier
            .background(color = colorResource(R.color.white))
            .width(320.dp)
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.preview_admob),
            fontSize = 12.sp,
            color = colorResource(
                R.color.ad_text
            ),
            style = TextStyle(fontWeight = FontWeight.SemiBold)
        )
    }
}

@Composable
@Preview
fun DummyAdMobBannerPreview() {
    MyAppTheme {
        DummyAdMobBanner()
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun DummyAdMobBannerPreviewDark() {
    MyAppTheme {
        DummyAdMobBanner()
    }
}
