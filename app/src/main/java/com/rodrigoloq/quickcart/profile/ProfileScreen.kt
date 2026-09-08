package com.rodrigoloq.quickcart.profile

import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rodrigoloq.quickcart.R
import com.rodrigoloq.quickcart.common.CustomSnackBar
import com.rodrigoloq.quickcart.profile.viewmodel.ProfileViewModel
import com.rodrigoloq.quickcart.ui.theme.BackgroundPrimary
import com.rodrigoloq.quickcart.ui.theme.GreenMain
import com.rodrigoloq.quickcart.ui.theme.myFont
import kotlinx.coroutines.launch
import kotlin.math.max

@Preview
@Composable
fun ProfilePreview(){
    ProfileView(rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(navController: NavController,
                modifier: Modifier = Modifier,
                viewModel: ProfileViewModel = viewModel()){
    var optionSelected by remember { mutableStateOf("") }
    var informationOptionSelected by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false)}
    var typeForm by remember { mutableStateOf("") }
    var isSaveCardForm by remember { mutableStateOf(false) }
    var isSaveAddressForm by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val uiState by viewModel.uiState.collectAsState()
    Box(modifier.fillMaxSize().background(BackgroundPrimary)) {
        Column(Modifier.fillMaxSize()) {
            Column(Modifier
                .background(GreenMain)
                .fillMaxWidth()
                .padding(vertical = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Box(Modifier
                    .clip(CircleShape)
                    .border(2.dp, Color.Black, CircleShape)) {
                    Image(painterResource(R.drawable.user_image),
                        contentDescription = "user image",
                        modifier = Modifier.size(128.dp).background(BackgroundPrimary))
                }
                Text(uiState.user.firstName + " " + uiState.user.lastName,
                    fontFamily = myFont,
                    fontSize = 30.sp)
                Text(uiState.user.email,
                    fontFamily = myFont,
                    fontSize = 20.sp)
            }
            Column() {
                when(optionSelected){
                    "" -> {
                        OptionsComponent(listOf(OptionItem("Profile information", Icons.Default.PersonOutline),
                            OptionItem("Cards", Icons.Default.CreditCard),
                            OptionItem("Addresses", Icons.Default.LocationOn),
                            OptionItem("Sign out", Icons.Default.ExitToApp))){onOptionSelected, onShowBottomSheet ->
                            optionSelected = onOptionSelected
                        }
                    }
                    "Profile information" -> {
                        Row(Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ArrowBack,
                                "Icon option",
                                tint = Color.Black,
                                modifier = Modifier.clickable{
                                    optionSelected = ""
                                })
                            Spacer(Modifier.padding(horizontal = 8.dp))
                            Text("Profile information",
                                fontFamily = myFont,
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,)
                        }
                        HorizontalDivider()
                        OptionsComponent(listOf(OptionItem("First Name"),
                            OptionItem("Last Name"),
                            OptionItem("Phone"),
                            OptionItem("Email"),
                            OptionItem("Username"),
                            OptionItem("Password"))){onOptionSelected, onShowBottomSheet ->
                            showBottomSheet = onShowBottomSheet
                            informationOptionSelected = onOptionSelected
                            typeForm = "Profile information"
                        }
                    }
                    "Cards" -> {
                        Row(Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ArrowBack,
                                "Icon option",
                                tint = Color.Black,
                                modifier = Modifier.clickable{
                                    optionSelected = ""
                                })
                            Spacer(Modifier.padding(horizontal = 8.dp))
                            Text("Cards",
                                fontFamily = myFont,
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,)
                        }
                        HorizontalDivider()
                        OptionsComponent(
                            listOf(OptionItem("${uiState.user.bank.cardType}-${uiState.user.bank.cardNumber.takeLast(4)}",
                                Icons.Outlined.CreditCard),
                                OptionItem("Add credit card",
                                    Icons.Default.AddCard))) { onOptionSelected, onShowBottomSheet ->
                            if(onOptionSelected == "Add credit card") {isSaveCardForm = true}
                            showBottomSheet = onShowBottomSheet
                            typeForm = "Card information"
                        }
                    }
                    "Addresses" -> {
                        Row(Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ArrowBack,
                                "Icon option",
                                tint = Color.Black,
                                modifier = Modifier.clickable{
                                    optionSelected = ""
                                })
                            Spacer(Modifier.padding(horizontal = 8.dp))
                            Text("Addresses",
                                fontFamily = myFont,
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,)
                        }
                        HorizontalDivider()
                        OptionsComponent(
                            listOf(OptionItem("${uiState.user.address.address}, ${uiState.user.address.city}, ${uiState.user.address.stateCode}",
                                Icons.Outlined.CreditCard),
                                OptionItem("Add address", Icons.Default.AddCard))) { onOptionSelected, onShowBottomSheet ->
                            if(onOptionSelected == "Add address") {isSaveAddressForm = true}
                            showBottomSheet = onShowBottomSheet
                            typeForm = "Address information"

                        }
                    }
                }
            }
        }
        if (showBottomSheet){
            ModalBottomSheet(modifier = Modifier
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenHeightDp.dp * 0.75f)
                .align(Alignment.BottomCenter),
                sheetState = sheetState,
                onDismissRequest = {
                    showBottomSheet = false
                }, containerColor = BackgroundPrimary,
                dragHandle = {
                    BottomSheetDefaults.DragHandle(
                        color = Color.Black
                    )
                }) {
                when(typeForm){
                    "Profile information" -> {
                        EditInformationFormView(optionName = informationOptionSelected,
                            userInformation = uiState.user){
                            showBottomSheet = it
                            coroutineScope.launch {
                                snackState.showSnackbar("Changes successfully saved",
                                    actionLabel = "Close",
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Short)
                            }
                        }
                    }
                    "Card information" -> {
                        CardInformationFormView(saveFormType = isSaveCardForm,
                            bank = uiState.user.bank,
                            name = "${uiState.user.firstName} ${uiState.user.lastName}") {
                            if (isSaveCardForm){
                                coroutineScope.launch {
                                    snackState.showSnackbar("Changes successfully saved",
                                        actionLabel = "Close",
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Short)
                                }
                            }
                            showBottomSheet = it
                            isSaveCardForm = false
                        }
                    }
                    "Address information" -> {
                        AddressInformationFormView(saveFormType = isSaveAddressForm,
                            address = uiState.user.address) {
                            if (isSaveAddressForm){
                                coroutineScope.launch {
                                    snackState.showSnackbar("Changes successfully saved",
                                        actionLabel = "Close",
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Short)
                                }
                            }
                            showBottomSheet = it
                            isSaveAddressForm = false
                        }
                    }
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

data class OptionItem(val name: String,
                      val icon: ImageVector? = null)

@Composable
fun OptionsComponent(optionsList: List<OptionItem>,
                     onClick:(String, Boolean) -> Unit){
    Column(Modifier.verticalScroll(rememberScrollState())) {
        optionsList.forEach { option ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
                .clickable{
                    onClick(option.name, true)
                },
                horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (option.icon != null){
                        Icon(option.icon,
                            "Icon option",
                            tint = Color.Black)
                        Spacer(Modifier.padding(horizontal = 8.dp))
                    }
                    Text(option.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = myFont,
                        color = Color.Black,
                        fontSize = 20.sp)
                }
                Icon(Icons.Default.ArrowForwardIos,
                    "Icon option",
                    tint = Color.DarkGray)
            }
        }
    }
}

