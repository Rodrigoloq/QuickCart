package com.rodrigoloq.quickcart.feature.cart

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreHoriz
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.rodrigoloq.quickcart.R
import com.rodrigoloq.quickcart.feature.cart.viewmodel.CartUIState
import com.rodrigoloq.quickcart.feature.cart.viewmodel.CartViewModel
import com.rodrigoloq.quickcart.ui.common.CustomSnackBar
import com.rodrigoloq.quickcart.ui.common.NotFound
import com.rodrigoloq.quickcart.data.room.entities.CartProducts
import com.rodrigoloq.quickcart.ui.theme.BackgroundPrimary
import com.rodrigoloq.quickcart.ui.theme.BackgroundSecondary
import com.rodrigoloq.quickcart.ui.theme.GreenMain
import com.rodrigoloq.quickcart.ui.theme.QuickCartTheme
import com.rodrigoloq.quickcart.ui.theme.myFont
import kotlinx.coroutines.launch

@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun CartPreview() {
    QuickCartTheme() {
        Surface() {
            CartViewContent(
                state = CartUIState(),
                modifier = Modifier,
                removeProductFromCart = { },
                modifyQuantity = { id, isSum -> }
            ) { }
        }
    }
}

@Composable
fun CartViewContent(
    state: CartUIState,
    modifier: Modifier,
    removeProductFromCart: (Int) -> Unit,
    modifyQuantity: (Int, Boolean) -> Unit,
    clearCart: () -> Unit
) {
    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }

    fun sumProductsPrices(priceType: String): String {
        var sum = 0.0
        when (priceType) {
            "subtotal" -> {
                state.cartProducts.forEach { product ->
                    val total = product.price * product.quantity
                    sum += total
                }
            }

            "discount" -> {
                state.cartProducts.forEach { product ->
                    val total = (product.price - product.price_with_discount) * product.quantity
                    sum += total
                }
            }

            "total" -> {
                state.cartProducts.forEach { product ->
                    val total = product.price_with_discount * product.quantity
                    sum += total
                }
            }
        }
        return String.format("%.2f", sum)
    }

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
                    "My cart",
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
                                    "Clear cart",
                                    color = Color.Black
                                )
                            },
                            onClick = {
                                clearCart()
                                expanded = false
                                coroutineScope.launch {
                                    snackState.showSnackbar(
                                        "Your cart has been cleared",
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
            if (state.cartProducts.isEmpty()) {
                NotFound(
                    title = "Your cart is empty", modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
            } else {
                LazyColumn(
                    Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(state.cartProducts.size) { i ->
                        val cartProduct = state.cartProducts[i]
                        CartItem(
                            onQuantitySum = {
                            modifyQuantity(cartProduct.product_id, true)
                        }, onQuantitySubtract = {
                            modifyQuantity(cartProduct.product_id, false)
                        }, cartProduct = cartProduct,
                            onDeleteProduct = { removeProductFromCart(cartProduct.product_id) })
                        if (i != state.cartProducts.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(vertical = 8.dp),
                                color = Color.LightGray
                            )
                        }
                    }
                }
            }
            Column(Modifier.padding(all = 8.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(
                        "Subtotal", fontFamily = myFont,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Text(
                        sumProductsPrices("subtotal"), fontFamily = myFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(
                        "Discount", fontFamily = myFont,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Text(
                        sumProductsPrices("discount"), fontFamily = myFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(
                        "Total", fontFamily = myFont,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Text(
                        sumProductsPrices("total"), fontFamily = myFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }
            HorizontalDivider(color = Color.LightGray)
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                colors = ButtonColors(
                    containerColor = GreenMain,
                    contentColor = BackgroundPrimary,
                    disabledContainerColor = GreenMain,
                    disabledContentColor = BackgroundPrimary,
                ), shape = RoundedCornerShape(12.dp),
                onClick = {

                }) {
                Box(Modifier.padding(vertical = 4.dp)) {
                    Text(
                        "Checkout for $${sumProductsPrices("total")}", fontSize = 24.sp,
                        fontFamily = myFont,
                        fontWeight = FontWeight.SemiBold
                    )
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
fun CartView(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    CartViewContent(
        state = uiState,
        modifier = modifier,
        removeProductFromCart = { productId ->
            viewModel.removeProductFromCart(productId)
        },
        modifyQuantity = { productId, isSum ->
            viewModel.modifyQuantity(productId, isSum)
        },
        clearCart = {
            viewModel.clearCart()
        })
}

@Composable
fun CartItem(
    cartProduct: CartProducts,
    onQuantitySum: () -> Unit,
    onQuantitySubtract: () -> Unit,
    onDeleteProduct: () -> Unit
) {

    Box(
        Modifier
            .fillMaxWidth()
            .background(BackgroundPrimary)
    ) {
        Row() {
            Box(
                Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(BackgroundSecondary)
            ) {
                AsyncImage(
                    model = cartProduct.thumbnail,
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
                    .padding(all = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            cartProduct.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = myFont,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = {
                            onDeleteProduct()
                        }) {
                            Icon(
                                Icons.Default.Close,
                                "Delete product",
                                tint = Color.Gray
                            )
                        }
                    }
                    Text(
                        cartProduct.sku,
                        fontFamily = myFont,
                        color = Color.DarkGray
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "$${cartProduct.price * cartProduct.quantity}",
                        fontFamily = myFont,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    QuantitySelector(
                        quantity = cartProduct.quantity,
                        onQuantitySum = {
                            onQuantitySum()
                        },
                        onQuantitySubtract = {
                            onQuantitySubtract()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun QuantityButton(
    symbol: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val borderColor = if (enabled) {
        GreenMain// Verde
    } else {
        Color.LightGray
    }

    val contentColor = if (enabled) {
        GreenMain
    } else {
        Color.LightGray
    }

    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = symbol,
            color = contentColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun QuantitySelector(
    quantity: Int,
    onQuantitySum: () -> Unit,
    onQuantitySubtract: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        QuantityButton(
            symbol = "−",
            enabled = quantity > 1,
            onClick = {
                if (quantity > 1) {
                    onQuantitySubtract()
                }
            }
        )

        Text(
            text = quantity.toString(),
            modifier = Modifier.width(20.dp),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontFamily = myFont,
            color = Color.Black
        )

        QuantityButton(
            symbol = "+",
            enabled = true,
            onClick = {
                onQuantitySum()
            }
        )
    }
}