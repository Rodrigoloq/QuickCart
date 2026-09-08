package com.rodrigoloq.quickcart.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.rodrigoloq.quickcart.R
import com.rodrigoloq.quickcart.retrofit.entities.product.Product
import com.rodrigoloq.quickcart.ui.theme.BackgroundItem
import com.rodrigoloq.quickcart.ui.theme.BackgroundSecondary
import com.rodrigoloq.quickcart.ui.theme.GreenMain
import com.rodrigoloq.quickcart.ui.theme.myFont

@Preview(showBackground = true)
@Composable
fun CategoryProductItemPreview(){
    CategoryProductItemView(width = 150.dp,
        product = Product(title = "iPhone 5s Foreign Beggars feat Skrillex - 'Still Gettin It'",
            rating = 4.9,
            price = 299.99,
            discountPercentage = 13.9)){}
}

@Composable
fun CategoryProductItemView(modifier: Modifier = Modifier,
                            width: Dp,
                            product: Product,
                            onClick:() -> Unit){

    fun getFinalPrice(price: Double, desc: Double): String{
        val result = price * (1.0 - (desc / 100.0))
        return String.format("%.2f", result)
    }


    Column(modifier.clickable{
        onClick()
    }) {
        Box(Modifier.clip(RoundedCornerShape(12.dp))
            .background(BackgroundItem)) {
            AsyncImage(model = product.thumbnail,
                contentDescription = "product thumbnail",
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.ic_downloading),
                fallback = painterResource(R.drawable.ic_broken_image),
                modifier = Modifier.size(width))
        }
        Row(modifier = Modifier.width(width),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(product.title,
                fontFamily = myFont,
                maxLines = 1,
                color = Color.DarkGray,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painterResource(R.drawable.ic_star),"Rating")
                Text("${product.rating}",
                    fontFamily = myFont,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black)
            }
        }
        FlowRow(verticalArrangement = Arrangement.Bottom) {
            Text("Now $${getFinalPrice(product.price,product.discountPercentage)}",
                fontFamily = myFont,
                fontWeight = FontWeight.SemiBold,
                color = GreenMain)
            Text("$${product.price}",
                textDecoration = TextDecoration.LineThrough,
                fontFamily = myFont,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontSize = 10.sp,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .align(Alignment.Bottom))
        }
    }
}