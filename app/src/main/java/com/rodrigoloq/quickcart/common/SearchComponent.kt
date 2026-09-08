package com.rodrigoloq.quickcart.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.rodrigoloq.quickcart.R
import com.rodrigoloq.quickcart.retrofit.entities.product.Product
import com.rodrigoloq.quickcart.ui.theme.BackgroundSecondary
import com.rodrigoloq.quickcart.ui.theme.QuickCartTheme

@Preview
@Composable
fun SearchComponentPreview(){
    QuickCartTheme() {
        SearchComponentView(products = emptyList(),
            navController = rememberNavController())
    }
}

@Composable
fun SearchComponentView(modifier: Modifier = Modifier,
                        products: List<Product>,
                        navController: NavController){
    Column(modifier
        .fillMaxSize()
        .padding(top = 8.dp)) {
        LazyVerticalGrid(columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)) {
            items(products.size){i ->
                val product = products[i]
                Box(Modifier
                    .background(BackgroundSecondary)
                    .clickable{
                        navController.navigate("product" + "/${product.id}")
                    },
                    contentAlignment = Alignment.Center) {
                    AsyncImage(model = product.thumbnail,
                        contentDescription = "product thumbnail",
                        error = painterResource(R.drawable.ic_broken_image),
                        placeholder = painterResource(R.drawable.ic_downloading),
                        fallback = painterResource(R.drawable.ic_broken_image))
                }
            }
        }
    }
}


