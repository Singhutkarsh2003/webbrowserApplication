package com.example.webbrowserapplication.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.webbrowserapplication.R

@Composable
fun ImageCarousel() {

    val image = listOf(
        R.drawable.banner,
        R.drawable.banner2,
        R.drawable.banner3
    )
    val pagerState  = rememberPagerState(pageCount = {image.size})

    Column{
        HorizontalPager(
            state = pagerState
        ) { page ->

            Card(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                Image(
                    painter = painterResource(
                        image[page]
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(image.size){index ->
                Box(modifier = Modifier.padding(4.dp)
                    .size(if (pagerState.currentPage ==index)12.dp else 8.dp)
                    .clip(CircleShape)
                    .background(
                        if (pagerState.currentPage == index) Color.Black else Color.LightGray
                    )
                )
            }
        }
    }

}