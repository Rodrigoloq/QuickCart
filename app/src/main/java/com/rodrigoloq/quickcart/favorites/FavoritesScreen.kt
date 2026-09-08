package com.rodrigoloq.quickcart.favorites

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.rodrigoloq.quickcart.R
import com.rodrigoloq.quickcart.common.CustomSnackBar
import com.rodrigoloq.quickcart.common.NotFound
import com.rodrigoloq.quickcart.favorites.viewmodel.FavoritesUIState
import com.rodrigoloq.quickcart.favorites.viewmodel.FavoritesViewModel
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
fun FavoritesPreview() {
    QuickCartTheme() {
        Surface() {
            FavoritesViewContent(
                state = FavoritesUIState(),
                modifier = Modifier,
                navController = rememberNavController(),
                removeFavorite = {},
                clearFavorites = {}
            ) {}
        }
    }
}

@Composable
fun FavoritesViewContent(
    state: FavoritesUIState,
    modifier: Modifier,
    navController: NavController,
    removeFavorite: (Int) -> Unit,
    clearFavorites: () -> Unit,
    addProductToCart: (CartProducts) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        Column(Modifier.fillMaxSize()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    "My favorites",
                    fontFamily = myFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Box(Modifier.align(Alignment.TopEnd)) {
                    IconButton(
                        onClick = {
                            expanded = !expanded
                        }, Modifier
                            .border(2.dp, BackgroundSecondary, CircleShape)
                            .clip(CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreHoriz,
                            contentDescription = "More options",
                            tint = Color.Black
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        containerColor = Color.White
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Clear favorites",
                                    color = Color.Black
                                )
                            },
                            onClick = {
                                clearFavorites()
                                expanded = false
                                coroutineScope.launch {
                                    snackState.showSnackbar(
                                        "Your favorites have been cleared",
                                        actionLabel = "Close",
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.padding(4.dp))
            HorizontalDivider(color = Color.LightGray)
            if (state.favorites.isEmpty()) {
                NotFound(
                    title = "No favorites yet. Start exploring to add some",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                )
            } else {
                LazyColumn(
                    Modifier.padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(count = state.favorites.size) { i ->
                        val favorite = state.favorites[i]
                        FavoriteItem(
                            favorite,
                            onRemove = {
                                removeFavorite(favorite.product_id)
                                coroutineScope.launch {
                                    snackState.showSnackbar(
                                        "The product has been removed",
                                        actionLabel = "Close",
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            },
                            onShowProduct = {
                                navController.navigate("product" + "/${favorite.product_id}")
                            },
                            onAddToCart = {
                                addProductToCart(
                                    CartProducts(
                                        product_id = favorite.product_id,
                                        title = favorite.title,
                                        price = favorite.price,
                                        discount_percentage = favorite.discount_percentage,
                                        price_with_discount = favorite.price_with_discount,
                                        thumbnail = favorite.thumbnail,
                                        stock = favorite.stock,
                                        quantity = 1,
                                        sku = favorite.sku
                                    )
                                )
                                coroutineScope.launch {
                                    snackState.showSnackbar(
                                        "Product added to cart",
                                        actionLabel = "Close",
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            })
                    }
                }
            }
        }
        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomStart),
            hostState = snackState
        ) { snackbarData: SnackbarData ->
            CustomSnackBar(
                snackbarData.visuals.message,
            ) {
                snackbarData.dismiss()
            }
        }
    }
}

@Composable
fun FavoritesView(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    FavoritesViewContent(
        state = uiState,
        modifier = modifier,
        navController = navController,
        removeFavorite = { productId ->
            viewModel.removeFavorite(productId)
        },
        clearFavorites = {
            viewModel.clearFavorites()
        },
        addProductToCart = { product ->
            viewModel.addProductToCart(product)
        }
    )
}

@Composable
fun FavoriteItem(
    favorite: Favorites,
    onRemove: () -> Unit,
    onShowProduct: () -> Unit,
    onAddToCart: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(BackgroundPrimary)
    ) {
        Row() {
            Box(Modifier.background(BackgroundSecondary)) {
                AsyncImage(
                    model = favorite.thumbnail,
                    contentDescription = "product thumbnail",
                    error = painterResource(R.drawable.ic_broken_image),
                    placeholder = painterResource(R.drawable.ic_downloading),
                    fallback = painterResource(R.drawable.ic_broken_image),
                    modifier = Modifier.size(150.dp)
                )
            }
            Column(
                Modifier
                    .height(150.dp)
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(
                        favorite.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = myFont,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "${favorite.stock} left",
                            fontFamily = myFont,
                            color = Color.DarkGray
                        )
                        Text(
                            " | ",
                            fontFamily = myFont,
                            color = Color.Gray
                        )
                        RatingStars(favorite.rating.toFloat(), starSize = 18.dp)
                        Text(
                            "(${favorite.reviewsNumber})",
                            fontFamily = myFont,
                            color = Color.Black
                        )
                    }
                }
                Column() {
                    Row() {
                        Text(
                            "$${favorite.price_with_discount}",
                            fontFamily = myFont,
                            fontWeight = FontWeight.SemiBold,
                            color = GreenMain,
                            fontSize = 18.sp
                        )
                        Text(
                            "${favorite.price}",
                            textDecoration = TextDecoration.LineThrough,
                            fontFamily = myFont,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            modifier = Modifier
                                .weight(1f),
                            colors = ButtonColors(
                                containerColor = GreenMain,
                                contentColor = BackgroundPrimary,
                                disabledContainerColor = GreenMain,
                                disabledContentColor = BackgroundPrimary,
                            ), shape = RoundedCornerShape(12.dp),
                            onClick = {
                                onAddToCart()
                            }) {
                            Box(Modifier.padding(vertical = 4.dp)) {
                                Text(
                                    "Add to cart", fontSize = 18.sp,
                                    fontFamily = myFont,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        MinimalDropdownMenu(
                            onRemove = { onRemove() },
                            onShowProduct = { onShowProduct() })
                    }
                    HorizontalDivider(
                        Modifier.padding(top = 8.dp),
                        1.dp, Color.LightGray
                    )
                }
            }
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

@Composable
fun MinimalDropdownMenu(
    onRemove: () -> Unit,
    onShowProduct: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box() {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                Icons.Default.MoreHoriz,
                contentDescription = "More options",
                tint = Color.Black,
                modifier = Modifier.padding(start = 12.dp, end = 4.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = Color.White
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        "Remove",
                        color = Color.Black
                    )
                },
                onClick = {
                    expanded = false
                    onRemove()
                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        "Show product",
                        color = Color.Black
                    )
                },
                onClick = {
                    expanded = false
                    onShowProduct()
                }
            )
        }
    }
}