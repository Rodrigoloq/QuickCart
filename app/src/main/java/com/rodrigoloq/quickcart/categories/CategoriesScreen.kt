package com.rodrigoloq.quickcart.categories

import android.R.attr.top
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rodrigoloq.quickcart.SearchComponentView
import com.rodrigoloq.quickcart.categories.viewmodel.CategoriesViewModel
import com.rodrigoloq.quickcart.common.CustomSnackBar
import com.rodrigoloq.quickcart.common.LoadingIndicator
import com.rodrigoloq.quickcart.common.NotFound
import com.rodrigoloq.quickcart.home.SearchTextField
import com.rodrigoloq.quickcart.room.entities.CartProducts
import com.rodrigoloq.quickcart.ui.theme.BackgroundItem
import com.rodrigoloq.quickcart.ui.theme.BackgroundPrimary
import com.rodrigoloq.quickcart.ui.theme.QuickCartTheme
import com.rodrigoloq.quickcart.ui.theme.myFont
import kotlinx.coroutines.launch

@Preview(name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
fun CategoriesPreview(){
    QuickCartTheme(){
        Surface() {
            CategoriesView(rememberNavController())
        }
    }
}

@Composable
fun CategoriesView(navController: NavController,
                   modifier: Modifier = Modifier,
                   viewModel: CategoriesViewModel = viewModel()){
    val uiState by viewModel.uiState.collectAsState()
    val categories = listOf("Beauty", "Fragrances", "Furniture", "Groceries", "Home Decoration",
        "Kitchen Accessories", "Laptops", "Mens Shirts", "Mens Shoes", "Mens Watches",
        "Mobile Accessories", "Motorcycle", "Skin Care", "Smartphones", "Sports Accessories",
        "Sunglasses", "Tablets", "Tops", "Vehicle", "Womens Bags", "Womens Dresses",
        "Womens Jewellery", "Womens Shoes", "Womens Watches")
    var selectedCategory by remember { mutableStateOf(categories[0]) }
    var searchTextValue by remember { mutableStateOf("") }
    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()



    fun getFinalPrice(price: Double, desc: Double): String{
        val result = price * (1.0 - (desc / 100.0))
        return String.format("%.2f", result)
    }

    LaunchedEffect(Unit) {
        viewModel.getProductsByCategory(selectedCategory
            .lowercase().replace(" ","-"))
    }

    Box(modifier.fillMaxSize().background(BackgroundPrimary),
        contentAlignment = Alignment.TopCenter) {
        Column() {
            SearchTextField(value = searchTextValue,
                modifier = Modifier.padding(start = 8.dp,
                    end = 8.dp, top = 8.dp)) {
                searchTextValue = it
                viewModel.searchProducts(it)
            }
            if (searchTextValue.isEmpty()){
                Row(Modifier.padding(top = 8.dp)) {
                    LazyColumn() {
                        items(categories.size){i ->
                            val category = categories[i]
                            Box(Modifier.background(color = if (selectedCategory == category) Color. White else BackgroundItem)
                                .padding(8.dp).clickable{
                                    selectedCategory = category
                                    viewModel.getProductsByCategory(category
                                        .lowercase().replace(" ","-"))
                                }) {
                                Text(text = category,
                                    color = Color.Black,
                                    softWrap = true,
                                    fontFamily = myFont,
                                    fontWeight = if (selectedCategory == category) FontWeight.Bold else FontWeight.Light,
                                    modifier = Modifier.width(110.dp))
                            }
                        }
                    }
                    BoxWithConstraints() {
                        val itemWidth = (maxWidth / 2)
                        if (uiState.inProgressProducts){
                            LoadingIndicator(title = "products")
                        } else {
                            if (uiState.products.isEmpty()){
                                NotFound(title = "Not products found",
                                    modifier = Modifier.fillMaxSize())
                            } else {
                                LazyVerticalGrid(columns = GridCells.Fixed(2),
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    items(uiState.products.size){i ->
                                        val product = uiState.products[i]
                                        ProductItemView(width = itemWidth,
                                            product = product,
                                            onClick = {
                                                navController.navigate("product" + "/${product.id}")
                                            }){
                                            viewModel.addProductToCart(
                                                CartProducts(
                                                    product_id = product.id,
                                                    title = product.title,
                                                    price = product.price,
                                                    discount_percentage = product.discountPercentage,
                                                    price_with_discount = getFinalPrice(product.price,
                                                        product.discountPercentage).toDouble(),
                                                    thumbnail = product.thumbnail,
                                                    stock = product.stock,
                                                    quantity = 1,
                                                    sku = product.sku
                                                ))
                                            coroutineScope.launch {
                                                snackState.showSnackbar("Product added to cart",
                                                    actionLabel = "Close",
                                                    withDismissAction = true,
                                                    duration = SnackbarDuration.Short)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                if (uiState.searchProducts.isEmpty()){
                    NotFound(title = "Not matches found",
                        modifier = Modifier.fillMaxSize())
                } else {
                    SearchComponentView(products = uiState.searchProducts,
                        navController = navController)
                }
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
}