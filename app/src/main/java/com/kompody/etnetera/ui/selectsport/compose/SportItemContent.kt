package com.kompody.etnetera.ui.selectsport.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kompody.etnetera.ui.selectsport.model.SportItem
import com.kompody.etnetera.ui.theme.Divider

@Composable
fun SportItemContent(item: SportItem) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = item.name,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterStart)
        )

        Box(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(start = 16.dp)
                .background(Color.Divider)
                .align(Alignment.BottomStart)
        )
    }
}