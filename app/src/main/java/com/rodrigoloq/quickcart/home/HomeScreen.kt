package com.rodrigoloq.quickcart.home

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rodrigoloq.quickcart.R
import com.rodrigoloq.quickcart.common.SearchComponentView
import com.rodrigoloq.quickcart.common.LoadingIndicator
import com.rodrigoloq.quickcart.common.NotFound
import com.rodrigoloq.quickcart.common.SearchTextField
import com.rodrigoloq.quickcart.home.viewmodel.HomeUIState
import com.rodrigoloq.quickcart.home.viewmodel.HomeViewModel
import com.rodrigoloq.quickcart.ui.theme.BackgroundPrimary
import com.rodrigoloq.quickcart.ui.theme.BackgroundSecondary
import com.rodrigoloq.quickcart.ui.theme.GreenMain
import com.rodrigoloq.quickcart.ui.theme.QuickCartTheme
import com.rodrigoloq.quickcart.ui.theme.myFont

@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun HomePreview() {
    QuickCartTheme() {
        Surface() {
            HomeViewContent(
                state = HomeUIState(),
                modifier = Modifier,
                navHostController = rememberNavController(),
                navController = rememberNavController(),
                getProductsByCategory = {},
                searchProducts = {},
                onClickCart = {}
            )
        }
    }
}

@Composable
fun HomeViewContent(
    state: HomeUIState,
    modifier: Modifier,
    navHostController: NavHostController,
    navController: NavController,
    getProductsByCategory: (String) -> Unit,
    searchProducts: (String) -> Unit,
    onClickCart: () -> Unit
) {

    var searchTextValue by remember { mutableStateOf("") }
    val categories = listOf(
        "Beauty", "Fragrances", "Furniture", "Groceries", "Home Decoration",
        "Kitchen Accessories", "Laptops", "Mens Shirts", "Mens Shoes", "Mens Watches",
        "Mobile Accessories", "Motorcycle", "Skin Care", "Smartphones", "Sports Accessories",
        "Sunglasses", "Tablets", "Tops", "Vehicle", "Womens Bags", "Womens Dresses",
        "Womens Jewellery", "Womens Shoes", "Womens Watches"
    )
    var selectedCategory by remember { mutableStateOf(categories[0]) }
    var shownCategories by remember { mutableIntStateOf(4) }
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.95f else 1f,
        label = "scale"
    )

    LaunchedEffect(Unit) {
        getProductsByCategory(
            selectedCategory
                .lowercase().replace(" ", "-")
        )
    }

    Box(
        modifier
            .fillMaxSize()
            .background(BackgroundPrimary),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(Modifier.padding(horizontal = 8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painterResource(R.drawable.logo_portada),
                    "portada",
                    Modifier.width(200.dp)
                )
                Box(Modifier.align(Alignment.CenterVertically)) {
                    IconButton(
                        onClick = {
                            navHostController.navigate("nav_cart")
                            onClickCart()
                        }, Modifier
                            .border(2.dp, BackgroundSecondary, CircleShape)
                            .clip(CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingBag,
                            contentDescription = "Cart",
                            tint = Color.Black
                        )
                    }
                    if (state.cartSize != 0) {
                        Box(
                            Modifier
                                .clip(CircleShape)
                                .size(22.dp)
                                .background(GreenMain)
                                .align(Alignment.TopEnd),
                            Alignment.TopCenter
                        ) {
                            Text(
                                text = "${state.cartSize}",
                                fontFamily = myFont,
                                color = BackgroundPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            SearchTextField(searchTextValue, onClearText = {
                searchTextValue = ""
            }) {
                searchTextValue = it
                searchProducts(it)
            }
            Column() {
                if (searchTextValue.isEmpty()) {
                    Box(Modifier.padding(top = 8.dp)) {
                        Column(
                            Modifier
                                .align(Alignment.BottomStart)
                                .clip(RoundedCornerShape(12.dp))
                                .background(GreenMain)
                                .padding(top = 8.dp, start = 10.dp, end = 8.dp)
                                .fillMaxWidth()
                                .height(150.dp)
                        ) {
                            Text(
                                "Clearence",
                                fontFamily = myFont,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Sales",
                                fontFamily = myFont,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Row(
                                Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(BackgroundPrimary)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Percent,
                                    contentDescription = "Percent",
                                    tint = GreenMain
                                )
                                Text(
                                    "Up to 50%",
                                    fontFamily = myFont,
                                    color = GreenMain
                                )
                            }
                        }
                        Image(
                            painterResource(R.drawable.thumbnail_ad2),
                            "advertising thumbnail",
                            Modifier
                                .align(Alignment.CenterEnd)
                                .height(180.dp)
                                .padding(end = 10.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Categories",
                            fontFamily = myFont,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.DarkGray
                        )
                        Text(
                            "See all",
                            fontFamily = myFont,
                            fontWeight = FontWeight.SemiBold,
                            color = GreenMain,
                            modifier = Modifier
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                }
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onPress = {
                                            pressed = true

                                            tryAwaitRelease()

                                            pressed = false
                                        },
                                        onTap = {
                                            shownCategories = 24
                                        }
                                    )
                                })
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.horizontalScroll(rememberScrollState())
                    ) {
                        categories.take(shownCategories).forEach { category ->
                            FilterChip(
                                selected = selectedCategory == category,
                                onClick = {
                                    selectedCategory = category
                                    getProductsByCategory(category.lowercase().replace(" ", "-"))
                                },
                                label = { Text(category, fontFamily = myFont) },
                                colors = FilterChipDefaults.filterChipColors(
                                    labelColor = Color.Black,
                                    selectedLabelColor = Color.White,
                                    selectedContainerColor = GreenMain
                                ),
                                leadingIcon = if (selectedCategory == category) {
                                    {
                                        Icon(
                                            imageVector = Icons.Filled.Done,
                                            contentDescription = "Seleccionado",
                                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                                            tint = Color.White
                                        )
                                    }
                                } else null
                            )
                        }
                    }
                    BoxWithConstraints() {
                        val itemWidth = (maxWidth / 2)
                        if (state.inProgressProducts) {
                            LoadingIndicator(title = "products")
                        } else {
                            if (state.products.isEmpty()) {
                                NotFound(
                                    title = "Not products found",
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(state.products.size) { i ->
                                        val product = state.products[i]
                                        CategoryProductItemView(
                                            width = itemWidth,
                                            product = product
                                        ) {
                                            navController.navigate("product" + "/${product.id}")
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (state.searchProducts.isEmpty()) {
                        NotFound(
                            title = "Not matches found",
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        SearchComponentView(
                            products = state.searchProducts,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeView(
    navHostController: NavHostController,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onClickCart: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeViewContent(
        state = uiState,
        modifier = modifier,
        navHostController = navHostController,
        navController = navController,
        getProductsByCategory = { categoryName ->
            viewModel.getProductsByCategory(categoryName)
        },
        searchProducts = { searchValue ->
            viewModel.searchProducts(searchValue)
        },
        onClickCart = {
            onClickCart()
        }
    )
}