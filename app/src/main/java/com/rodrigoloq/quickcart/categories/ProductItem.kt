package com.rodrigoloq.quickcart.categories

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalContext
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
import com.rodrigoloq.quickcart.ui.theme.GreenMain
import com.rodrigoloq.quickcart.ui.theme.myFont
import kotlinx.coroutines.MainScope

@Preview(showBackground = true)
@Composable
fun ProductItemPreview(){
    ProductItemView(width = 150.dp, product = Product(title = "iPhone 5s Foreign Beggars feat Skrillex - 'Still Gettin It'",
        rating = 4.3,
        price = 299.99,
        discountPercentage = 13.9), onClick = {}){

    }
}

@Composable
fun ProductItemView(modifier: Modifier = Modifier,
                    width: Dp,
                    product: Product,
                    onClick: () -> Unit,
                    onAddProduct: () -> Unit){

    fun getFinalPrice(price: Double, desc: Double): String{
        val result = price * (1.0 - (desc / 100.0))
        return String.format("%.2f", result)
    }

    Column(modifier) {
        Box(Modifier.clip(RoundedCornerShape(12.dp))
            .background(BackgroundItem)) {
            AsyncImage(model = product.thumbnail,
                contentDescription = "product thumbnail",
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.ic_downloading),
                fallback = painterResource(R.drawable.ic_broken_image),
                modifier = Modifier.size(width).clickable{
                    onClick()
                })
            Icon(imageVector = Icons.Outlined.AddShoppingCart,
                contentDescription = "Add to cart",
                tint = Color.Black,
                modifier = Modifier.align(Alignment.BottomEnd)
                    .clickable{
                        onAddProduct()
                    })
        }
        RatingStars(rating = product.rating.toFloat(), starSize = 20.dp)
        Column(modifier = Modifier.width(width).padding(start = 4.dp)) {
            Text("Now $${getFinalPrice(product.price,product.discountPercentage)}",
                fontFamily = myFont,
                fontWeight = FontWeight.SemiBold,
                color = GreenMain)
            Text("$${product.price}",
                textDecoration = TextDecoration.LineThrough,
                fontFamily = myFont,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontSize = 10.sp)
        }
    }
}

@Composable
fun RatingStars(
    rating: Float,
    modifier: Modifier = Modifier,
    starSize: Dp = 24.dp,
    filledColor: Color = Color(0xFFFFC107),
    emptyColor: Color = Color.LightGray,
    spacing: Dp = 2.dp
) {
    val value = rating.coerceIn(0f, 5f)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        repeat(5) { index ->

            val fillFraction = (value - index)
                .coerceIn(0f, 1f)
            Box(
                modifier = Modifier.size(starSize)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null,
                    tint = emptyColor,
                    modifier = Modifier.fillMaxSize()
                )
                if (fillFraction > 0f) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = filledColor,
                        modifier = Modifier
                            .fillMaxSize()
                            .drawWithContent {
                                clipRect(
                                    left = 0f,
                                    top = 0f,
                                    right = size.width * fillFraction,
                                    bottom = size.height
                                ) {
                                    this@drawWithContent.drawContent()
                                }
                            }
                    )
                }
            }
        }
    }
}