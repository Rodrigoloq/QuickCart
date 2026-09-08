package com.rodrigoloq.quickcart.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rodrigoloq.quickcart.R
import com.rodrigoloq.quickcart.ui.theme.BackgroundPrimary
import com.rodrigoloq.quickcart.ui.theme.GreenMain
import com.rodrigoloq.quickcart.ui.theme.myFont

@Preview(showBackground = true)
@Composable
fun NotFoundPreview(){
    NotFound(title = "Oops! No results found")
}


@Composable
fun NotFound(backgroundColor: Color = BackgroundPrimary,
             modifier: Modifier = Modifier,
                     title: String){
    Box(modifier
        .background(backgroundColor),
        contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("$title",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                fontFamily = myFont,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.padding(vertical = 8.dp))
            Image(painter = painterResource(R.drawable.ic_empty),
                contentDescription = "No results found",
                modifier = Modifier.size(64.dp),
                colorFilter = ColorFilter.tint(GreenMain))
        }
    }
}