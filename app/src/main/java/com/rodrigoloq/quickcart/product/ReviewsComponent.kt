package com.rodrigoloq.quickcart.product

import android.R
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rodrigoloq.quickcart.retrofit.entities.product.Review
import com.rodrigoloq.quickcart.ui.theme.GreenMain
import java.time.Instant
import java.time.ZoneId

@Preview(showBackground = true)
@Composable
fun ReviewsComponentPreview(){
    ReviewsComponentView(reviews = listOf())
}

@Composable
fun ReviewsComponentView(modifier: Modifier = Modifier,
                         reviews: List<Review>){
    Column(modifier) {
        reviews.forEach{
            ReviewsItem(it)
            if (it != reviews.last()){
                HorizontalDivider(color = Color.LightGray)
            }
        }
    }
}

@Composable
fun ReviewsItem(review: Review){
    val dateString = review.date

    val date = Instant.parse(dateString)
        .atZone(ZoneId.systemDefault())

    val month = date.month.toString().lowercase().replaceFirstChar{
        it.uppercaseChar()
    }

    val starRating = when(review.rating){
        1 -> "Poor" to Color.Black
        2 -> "Fair" to Color.Black
        3 -> "Average" to Color.Black
        4 -> "Good" to GreenMain
        5 -> "Excellent" to GreenMain
        else -> null
    }

    Column(Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(review.reviewerName,
                fontSize = 15.sp,
                color = Color.Black)
            Text(" on $month ${date.dayOfMonth}, ${date.year}",
                color = Color.DarkGray)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RatingStars(review.rating.toFloat(),Modifier.padding(vertical = 4.dp))
            Text(starRating!!.first, color = starRating!!.second)
        }
        Text(review.comment,
            fontSize = 15.sp,
            color = Color.Black)
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
    val clampedRating = rating.coerceIn(0f, 5f)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        repeat(5) { index ->

            val fillFraction = (clampedRating - index)
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
                            .clip(
                                RectangleShape
                            )
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