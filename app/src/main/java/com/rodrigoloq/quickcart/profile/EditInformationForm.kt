package com.rodrigoloq.quickcart.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.rodrigoloq.quickcart.retrofit.entities.user.UserResponse
import com.rodrigoloq.quickcart.ui.theme.BackgroundPrimary
import com.rodrigoloq.quickcart.ui.theme.GreenMain
import com.rodrigoloq.quickcart.ui.theme.QuickCartTheme
import com.rodrigoloq.quickcart.ui.theme.myFont

@Preview
@Composable
fun EditInformationFormPreview(){
    QuickCartTheme() {
        EditInformationFormView(optionName = "Name",
            userInformation = UserResponse()){

        }
    }
}

@Composable
fun EditInformationFormView(modifier: Modifier = Modifier,
                            optionName: String,
                            userInformation: UserResponse,
                            onDismiss:(Boolean) -> Unit){

    var informationValue = ""

    val focus = LocalFocusManager.current
    val nameFocusRequester = remember { FocusRequester() }

    when(optionName){
        "First Name" -> {
            informationValue = userInformation.firstName
        }
        "Last Name" -> {
            informationValue = userInformation.lastName
        }
        "Phone" -> {
            informationValue = userInformation.phone
        }
        "Email" -> {
            informationValue = userInformation.email
        }
        "Username" -> {
            informationValue = userInformation.username
        }
        "Password" -> {
            informationValue = userInformation.password
        }
    }

    var informationTextValue by remember { mutableStateOf(informationValue) }

    Column(modifier
        .fillMaxWidth()
//        .height(400.dp)
        .background(BackgroundPrimary)) {
        Box(Modifier.fillMaxWidth().padding(bottom = 16.dp, start = 8.dp, end = 8.dp)){
            Text("Edit your ${optionName.lowercase()}",
                Modifier.align(Alignment.Center),
                fontFamily = myFont,
                color = Color.Black)
            Icon(Icons.Default.Close,
                "Close form",
                Modifier.align(Alignment.CenterEnd).clickable{
                    onDismiss(false)
                },
                Color.Black)
        }
        HorizontalDivider(color = Color.LightGray)
        Spacer(Modifier.padding(8.dp))
        Column(Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
            Text(optionName,
                fontFamily = myFont,
                color = Color.Black)
            OutlinedTextField(value = informationTextValue,
                textStyle = TextStyle(
                    fontFamily = myFont
                ),
                onValueChange = {
                    informationTextValue = it
                },
                trailingIcon = {
                    Icon(Icons.Default.Close,
                        "Clear field",
                        Modifier.clickable{
                            informationTextValue = ""
                        },
                        Color.DarkGray)
                },
                colors = TextFieldDefaults.colors(
                    // Text colors
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray,

                    // Background / Container colors
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,

                    // Indicator / Border colors
                    focusedIndicatorColor = Color.DarkGray,
                    unfocusedIndicatorColor = Color.DarkGray,

                    // Cursor color
                    cursorColor = Color.Black
                ), modifier = Modifier.fillMaxWidth().focusRequester(nameFocusRequester))
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
                    Text("Save", fontSize = 24.sp,
                        fontFamily = myFont,
                        fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}