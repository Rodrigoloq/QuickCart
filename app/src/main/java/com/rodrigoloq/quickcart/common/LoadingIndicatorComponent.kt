package com.rodrigoloq.quickcart.common

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rodrigoloq.quickcart.ui.theme.BackgroundPrimary
import com.rodrigoloq.quickcart.ui.theme.GreenMain
import com.rodrigoloq.quickcart.ui.theme.myFont

@Preview(showBackground = true)
@Composable
fun LoadingIndicatorPreview(){
    LoadingIndicator(title = "products")
}


@Composable
fun LoadingIndicator(backgroundColor: Color = BackgroundPrimary,
                     title: String){
    Box(Modifier
        .fillMaxSize()
        .background(backgroundColor),
        contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Loading $title",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                fontFamily = myFont
            )
            Spacer(Modifier.padding(vertical = 8.dp))
            CircularProgressIndicator(color = GreenMain)
        }
    }
}