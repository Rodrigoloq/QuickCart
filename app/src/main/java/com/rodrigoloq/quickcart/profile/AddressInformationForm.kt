package com.rodrigoloq.quickcart.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rodrigoloq.quickcart.retrofit.entities.user.Address
import com.rodrigoloq.quickcart.ui.theme.BackgroundPrimary
import com.rodrigoloq.quickcart.ui.theme.BlueMain
import com.rodrigoloq.quickcart.ui.theme.GreenMain
import com.rodrigoloq.quickcart.ui.theme.QuickCartTheme
import com.rodrigoloq.quickcart.ui.theme.myFont

@Preview
@Composable
fun AddressInformationFormPreview(){
    QuickCartTheme() {
        AddressInformationFormView(address = Address()) { }
    }
}

@Composable
fun AddressInformationFormView(modifier: Modifier = Modifier,
                               saveFormType: Boolean = false,
                               address: Address,
                               onDismiss:(Boolean) -> Unit){
    var informationTextValue by remember { mutableStateOf("") }
    val focus = LocalFocusManager.current
    val informationFocusRequester = remember { FocusRequester() }

    Column(modifier
        .fillMaxWidth()
//        .height(400.dp)
        .background(BackgroundPrimary)) {
        Box(Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)){
            Text(if (saveFormType) "Add a new address" else "Address information",
                Modifier.align(Alignment.Center),
                fontFamily = myFont,
                color = Color.Black)
            Icon(Icons.Default.Close,
                "Close form",
                Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                        onDismiss(false)
                    },
                Color.Black)
        }
        HorizontalDivider(color = Color.LightGray)
        Spacer(Modifier.padding(8.dp))
        Column(Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)) {
            Text("Address",
                fontFamily = myFont,
                color = Color.Black)
            OutlinedTextField(value = if (saveFormType) "" else address.address,
                enabled = saveFormType,
                textStyle = TextStyle(
                    fontFamily = myFont
                ),
                onValueChange = {
                    informationTextValue = it
                },
                colors = TextFieldDefaults.colors(
                    // Text colors
                    focusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,

                    // Background / Container colors
                    focusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,

                    // Indicator / Border colors
                    focusedIndicatorColor = Color.DarkGray,
                    disabledIndicatorColor = Color.DarkGray,
                    unfocusedIndicatorColor = Color.DarkGray,

                    // Cursor color
                    cursorColor = Color.Black
                ), modifier = Modifier.fillMaxWidth().focusRequester(informationFocusRequester))
            Spacer(Modifier.padding(8.dp))
            Row() {
                Column(Modifier.weight(0.5f)) {
                    Text("City",
                        fontFamily = myFont,
                        color = Color.Black)
                    OutlinedTextField(value = if (saveFormType) "" else address.city,
                        enabled = saveFormType,
                        textStyle = TextStyle(
                            fontFamily = myFont
                        ),
                        onValueChange = {
                            informationTextValue = it
                        },
                        colors = TextFieldDefaults.colors(
                            // Text colors
                            disabledTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,

                            // Background / Container colors
                            disabledContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,

                            // Indicator / Border colors
                            disabledIndicatorColor = Color.DarkGray,
                            unfocusedIndicatorColor = Color.DarkGray,
                            focusedIndicatorColor = Color.DarkGray,

                            // Cursor color
                            cursorColor = Color.Black
                        ),modifier = Modifier.focusRequester(informationFocusRequester))
                }
                Spacer(Modifier.padding(4.dp))
                Column(Modifier.weight(0.5f)) {
                    Text("State",
                        fontFamily = myFont,
                        color = Color.Black)
                    OutlinedTextField(if (saveFormType) "" else address.state,
                        enabled = saveFormType,
                        textStyle = TextStyle(
                            fontFamily = myFont
                        ),
                        onValueChange = {
                            informationTextValue = it
                        },
                        colors = TextFieldDefaults.colors(
                            // Text colors
                            disabledTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,

                            // Background / Container colors
                            disabledContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,

                            // Indicator / Border colors
                            disabledIndicatorColor = Color.DarkGray,
                            unfocusedIndicatorColor = Color.DarkGray,
                            focusedIndicatorColor = Color.DarkGray,

                            // Cursor color
                            cursorColor = Color.Black
                        ),modifier = Modifier.focusRequester(informationFocusRequester))
                }
            }
            Spacer(Modifier.padding(8.dp))
            Row() {
                Column(Modifier.weight(0.5f)) {
                    Text("Country",
                        fontFamily = myFont,
                        color = Color.Black)
                    OutlinedTextField(value = if (saveFormType) "" else address.country,
                        enabled = saveFormType,
                        textStyle = TextStyle(
                            fontFamily = myFont
                        ),
                        onValueChange = {
                            informationTextValue = it
                        },
                        colors = TextFieldDefaults.colors(
                            // Text colors
                            disabledTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,

                            // Background / Container colors
                            disabledContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,

                            // Indicator / Border colors
                            disabledIndicatorColor = Color.DarkGray,
                            unfocusedIndicatorColor = Color.DarkGray,
                            focusedIndicatorColor = Color.DarkGray,

                            // Cursor color
                            cursorColor = Color.Black
                        ),modifier = Modifier.focusRequester(informationFocusRequester))
                }
                Spacer(Modifier.padding(4.dp))
                Column(Modifier.weight(0.5f)) {
                    Text("Postal Code",
                        fontFamily = myFont,
                        color = Color.Black)
                    OutlinedTextField(if (saveFormType) "" else address.postalCode,
                        enabled = saveFormType,
                        textStyle = TextStyle(
                            fontFamily = myFont
                        ),
                        onValueChange = {
                            informationTextValue = it
                        },
                        colors = TextFieldDefaults.colors(
                            // Text colors
                            disabledTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,

                            // Background / Container colors
                            disabledContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,

                            // Indicator / Border colors
                            disabledIndicatorColor = Color.DarkGray,
                            unfocusedIndicatorColor = Color.DarkGray,
                            focusedIndicatorColor = Color.DarkGray,

                            // Cursor color
                            cursorColor = Color.Black
                        ),modifier = Modifier.focusRequester(informationFocusRequester))
                }
            }
            Spacer(Modifier.padding(8.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
                colors = ButtonColors(
                    containerColor = GreenMain,
                    contentColor = BackgroundPrimary,
                    disabledContainerColor = GreenMain,
                    disabledContentColor = BackgroundPrimary,
                ), shape = RoundedCornerShape(12.dp),
                onClick = {
                    focus.clearFocus()
                    onDismiss(false)
                }) {
                Box(Modifier.padding(vertical = 8.dp)) {
                    Text(if (saveFormType) "Save" else "Dismiss",
                        fontSize = 24.sp,
                        fontFamily = myFont,
                        fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}