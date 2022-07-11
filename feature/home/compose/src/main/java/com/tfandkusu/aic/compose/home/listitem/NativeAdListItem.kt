package com.tfandkusu.aic.compose.home.listitem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tfandkusu.aic.compose.home.NativeAdViewRecycler
import com.tfandkusu.aic.compose.home.RecyclableNativeAdAndroidView
import com.tfandkusu.aic.viewmodel.home.HomeStateNativeAdItemSource

@Composable
fun NativeAdListItem(
    nativeAdViewRecycler: NativeAdViewRecycler,
    nativeAdItemSource: HomeStateNativeAdItemSource
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        nativeAdItemSource.nativeAd?.let {
            RecyclableNativeAdAndroidView(
                nativeAdViewRecycler,
                it
            )
        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp)
            )
        }
        Divider()
    }
}

@Composable
@Preview
fun NativeAdListItemPreview() {
    val nativeAdViewRecycler = NativeAdViewRecycler()
    NativeAdListItem(nativeAdViewRecycler, HomeStateNativeAdItemSource())
}
