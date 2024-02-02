package com.example.firstapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import  androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.firstapp.ui.theme.BlueCustom

@Composable
fun FirstPage(navController: NavHostController) {
    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

            Image(painter = painterResource(id = R.drawable.img), contentDescription ="image" , modifier = Modifier.background(Color.Transparent).size(200.dp,200.dp).padding(bottom = 20.dp))

        ButtonWidget(text = "Play","game",navController=navController)
        ButtonWidget(text = "Settings", route = "settings", navController = navController)


        
    }
}
@Composable
fun ButtonWidget(text:String,route:String,navController:NavHostController){
    Button(
        modifier = Modifier.padding(vertical = 10.dp),
        onClick = {
            navController.navigate(route)
        }, shape =
        RoundedCornerShape(5.dp),
        elevation = ButtonDefaults.buttonElevation(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = BlueCustom,
            contentColor = Color.White
        )
    ) {
        Text(text, fontSize = 16.sp, modifier = Modifier.fillMaxWidth(0.4f), textAlign = TextAlign.Center)
    }
}

