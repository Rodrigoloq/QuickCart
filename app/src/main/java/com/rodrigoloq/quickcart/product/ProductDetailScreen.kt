package com.rodrigoloq.quickcart.product

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Redo
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.rodrigoloq.quickcart.R
import com.rodrigoloq.quickcart.common.CustomSnackBar
import com.rodrigoloq.quickcart.home.HomeView
import com.rodrigoloq.quickcart.product.viewmodel.ProductViewModel
import com.rodrigoloq.quickcart.retrofit.entities.product.Product
import com.rodrigoloq.quickcart.room.entities.CartProducts
import com.rodrigoloq.quickcart.room.entities.Favorites
import com.rodrigoloq.quickcart.ui.theme.BackgroundPrimary
import com.rodrigoloq.quickcart.ui.theme.BackgroundSecondary
import com.rodrigoloq.quickcart.ui.theme.GreenMain
import com.rodrigoloq.quickcart.ui.theme.QuickCartTheme
import com.rodrigoloq.quickcart.ui.theme.myFont
import kotlinx.coroutines.launch

@Preview(name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
fun ProductDetailPreview(){
    QuickCartTheme() {
        Surface() {
            ProductDetailView(rememberNavController(),"1")
        }
    }
}

@Composable
fun ProductDetailView(navController: NavController,
                      productId: String?,
                      modifier: Modifier = Modifier,
                      viewModel: ProductViewModel = viewModel()){
    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getProductDetail(productId!!)
        viewModel.isProductFavorite(productId)
    }

    fun getFinalPrice(price: Double, desc: Double): String{
        val result = price * (1.0 - (desc / 100.0))
        return String.format("%.2f", result)
    }

    Column(modifier.fillMaxSize().background(BackgroundSecondary)) {
        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            ImageCarousel(
                images = uiState.product.images
            )
            Row(Modifier.fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(8.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
                IconButton(onClick = {
                    navController.popBackStack()
                }, Modifier.clip(CircleShape)
                    .background(BackgroundPrimary)
                    .padding(4.dp)) {
                    Icon(imageVector = Icons.Default.ArrowBackIosNew,
                        "Back",
                        tint = Color.Black)
                }
                IconButton(onClick = {
                    if (uiState.isFavorite){
                        viewModel.removeFavorite(uiState.product.id)
                    } else {
                        viewModel.addProductToFavorites(Favorites(
                            product_id = uiState.product.id,
                            title = uiState.product.title,
                            price = uiState.product.price,
                            discount_percentage = uiState.product.discountPercentage,
                            price_with_discount = getFinalPrice(uiState.product.price,
                                uiState.product.discountPercentage).toDouble(),
                            thumbnail = uiState.product.thumbnail,
                            stock = uiState.product.stock,
                            rating = uiState.product.rating,
                            reviewsNumber = uiState.product.reviews.size,
                            sku = uiState.product.sku
                        ))
                    }
                }, Modifier.clip(CircleShape)
                    .background(BackgroundPrimary)
                    .padding(4.dp)) {
                    Icon(imageVector = if (uiState.isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                        "Back",
                        tint = if (uiState.isFavorite) Color.Red else Color.Black)
                }
            }
        }
        Column() {
            Box(Modifier.weight(1f)
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp))
                .background(BackgroundPrimary)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            ) {
                LazyColumn(Modifier.fillMaxSize()) {
                    item {
                        FlowRow(Modifier.fillMaxWidth().padding(bottom = 16.dp),
                            itemVerticalAlignment = Alignment.CenterVertically) {
                            Text(uiState.product.title,
                                color = Color.Black,
                                fontSize = 28.sp,
                                fontFamily = myFont,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Box(Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(color = Color.Red)
                                .padding(8.dp)) {
                                Text("${uiState.product.discountPercentage}% OFF",
                                    color = BackgroundPrimary,
                                    fontFamily = myFont,
                                    fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                    item {
                        Row() {
                            Box(Modifier.border(1.dp,
                                Color.DarkGray,
                                RoundedCornerShape(12.dp))
                                .padding(8.dp)) {
                                Row() {
                                    Icon(Icons.Default.Star,
                                        "rating star",
                                        tint = GreenMain
                                    )
                                    Text(uiState.product.rating.toString(), color = Color.Black,
                                        fontFamily = myFont,
                                        fontWeight = FontWeight.Bold)
                                    Text("|", color = Color.Black,
                                        modifier = Modifier.padding(horizontal = 4.dp),
                                        fontFamily = myFont)
                                    Text("${uiState.product.reviews.size} reviews", color = Color.DarkGray,
                                        fontSize = 14.sp,
                                        fontFamily = myFont,
                                        fontWeight = FontWeight.SemiBold)
                                }
                            }
                            Spacer(Modifier.padding(horizontal = 4.dp))
                            Box(Modifier.border(1.dp,
                                Color.DarkGray,
                                RoundedCornerShape(12.dp))
                                .padding(8.dp)) {
                                Row() {
                                    Text("In Stock", color = Color.Black,
                                        fontFamily = myFont,
                                        fontWeight = FontWeight.Bold)
                                    Text("|", color = Color.Black,
                                        modifier = Modifier.padding(horizontal = 4.dp),
                                        fontFamily = myFont)
                                    Text("${uiState.product.stock} available", color = Color.DarkGray,
                                        fontSize = 14.sp,
                                        fontFamily = myFont,
                                        fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                        Spacer(Modifier.padding(vertical = 8.dp))
                    }
                    item {
                        Text(uiState.product.description,
                            color = Color.Black, fontFamily = myFont)
                        Spacer(Modifier.padding(vertical = 4.dp))
                    }
                    item {
                        FlowRow() {
                            Image(painterResource(R.drawable.ic_package),
                                contentDescription = "return icon",
                                modifier = Modifier.size(24.dp))
                            Text("Shipping Information: ",
                                color = Color.Black,
                                fontFamily = myFont,
                                fontWeight = FontWeight.Bold)
                            Text("FREE SHIPPING",
                                fontFamily = myFont,
                                color = GreenMain,
                                textDecoration = TextDecoration.Underline,
                                fontWeight = FontWeight.SemiBold)
                            Text(uiState.product.shippingInformation,
                                fontFamily = myFont,
                                color = Color.Black)
                        }
                        Spacer(Modifier.padding(vertical = 4.dp))
                        Column() {
                            Row() {
                                Image(painterResource(R.drawable.ic_return),
                                    contentDescription = "return icon",
                                    modifier = Modifier.size(24.dp))
                                Text("Return Policy:",
                                    color = Color.Black,
                                    fontFamily = myFont,
                                    fontWeight = FontWeight.Bold)
                            }
                            Text(uiState.product.returnPolicy,
                                fontFamily = myFont,
                                color = Color.Black)
                        }
                        Spacer(Modifier.padding(vertical = 4.dp))
                        Column() {
                            Row() {
                                Image(painterResource(R.drawable.ic_shield),
                                    contentDescription = "return icon",
                                    modifier = Modifier.size(24.dp))
                                Text("Warranty Information:",
                                    color = Color.Black,
                                    fontFamily = myFont,
                                    fontWeight = FontWeight.Bold)
                            }
                            Text(uiState.product.warrantyInformation,
                                fontFamily = myFont,
                                color = Color.Black)
                        }
                    }
                    item {
                        Spacer(Modifier.padding(vertical = 4.dp))
                        Text("Reviews (${uiState.product.reviews.size})",
                            color = Color.Black,
                            fontFamily = myFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp)
                    }
                    item {
                        ReviewsComponentView(reviews = uiState.product.reviews)
                    }
                }
                SnackbarHost(
                    modifier=Modifier.align(Alignment.BottomStart),
                    hostState = snackState
                ) { snackbarData: SnackbarData ->
                    CustomSnackBar(
                        snackbarData.visuals.message,
                    ){
                        snackbarData.dismiss()
                    }
                }
            }
            Box(Modifier
                .background(BackgroundPrimary)) {
                HorizontalDivider(color = Color.LightGray)
                Row(Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(all = 16.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Column() {
                        Text("$${uiState.product.price}",
                            fontSize = 22.sp,
                            color = Color.Gray,
                            textDecoration = TextDecoration.LineThrough,
                            fontFamily = myFont,
                            fontWeight =FontWeight.SemiBold)
                        Text("$${getFinalPrice(uiState.product.price,
                            uiState.product.discountPercentage)}",
                            fontSize = 30.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = myFont)
                    }
                    Spacer(Modifier.padding(horizontal = 4.dp))
                    Button(modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                        colors = ButtonColors(
                            containerColor = GreenMain,
                            contentColor = BackgroundPrimary,
                            disabledContainerColor = GreenMain,
                            disabledContentColor = BackgroundPrimary,
                        ), shape = RoundedCornerShape(12.dp),
                        onClick = {
                            viewModel.addProductToCart(CartProducts(
                                product_id = uiState.product.id,
                                title = uiState.product.title,
                                price = uiState.product.price,
                                discount_percentage = uiState.product.discountPercentage,
                                price_with_discount = getFinalPrice(uiState.product.price,
                                    uiState.product.discountPercentage).toDouble(),
                                thumbnail = uiState.product.thumbnail,
                                stock = uiState.product.stock,
                                quantity = 1,
                                sku = uiState.product.sku
                            ))
                            coroutineScope.launch {
                                snackState.showSnackbar("Product added to cart",
                                    actionLabel = "Close",
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Short)
                            }
                        }) {
                        Box(Modifier.padding(vertical = 8.dp)) {
                            Text("Add to cart", fontSize = 24.sp,
                                fontFamily = myFont,
                                fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ImageCarousel(
    images: List<String>
) {
    val pagerState = rememberPagerState(
        pageCount = { images.size }
    )

    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { page ->
            AsyncImage(model = images[page],
                contentDescription = "product thumbnail",
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.ic_downloading),
                fallback = painterResource(R.drawable.ic_broken_image),
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 12.dp)
        ) {
            repeat(images.size) { index ->

                val selected = pagerState.currentPage == index

                Box(
                    modifier = Modifier
                        .size(if (selected) 8.dp else 6.dp)
                        .clip(CircleShape)
                        .background(
                            if (selected)
                                Color.DarkGray
                            else
                                Color.LightGray
                        )
                )
            }
        }
    }
}

