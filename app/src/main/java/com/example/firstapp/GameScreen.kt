package com.example.firstapp


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.firstapp.ui.theme.Aqua
import com.example.firstapp.ui.theme.BlueCustom
import com.example.firstapp.ui.theme.GrayBackground
import com.example.firstapp.ui.theme.GreenishYellow
import com.synac.tictactoe.BoardBase
import com.synac.tictactoe.Circle
import com.synac.tictactoe.Cross
import com.synac.tictactoe.WinDiagonalLine1
import com.synac.tictactoe.WinDiagonalLine2
import com.synac.tictactoe.WinHorizontalLine1
import com.synac.tictactoe.WinHorizontalLine2
import com.synac.tictactoe.WinHorizontalLine3
import com.synac.tictactoe.WinVerticalLine1
import com.synac.tictactoe.WinVerticalLine2
import com.synac.tictactoe.WinVerticalLine3

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FullScreen(
    viewModel: GameViewModel,
    navController: NavHostController
){
    val state = viewModel.state

    Column(
        modifier= Modifier
            .fillMaxSize()
            .background(GrayBackground)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow", tint = Color.Black, modifier = Modifier.size(width = 30.dp, height = 30.dp).align(Alignment.Start).clickable(indication = null,
                    interactionSource = MutableInteractionSource()) { navController.navigate("home") } )

        Row(
            modifier= Modifier
                .fillMaxWidth()
                ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            PlayerBody(text = "Player 1",state= state)
            PlayerBody(text = "Player 2", state = state)

        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp))
            .clip(
                RoundedCornerShape(20.dp)
            )
            .background(GrayBackground),
            contentAlignment = Alignment.Center
        ) {
            BoardBase()
            LazyVerticalGrid(
                modifier= Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(1f)
                    .clickable {
                    },
                columns = GridCells.Fixed(3),
                ){
                viewModel.boardItems.forEach{
                    (cellNo,boardCellValue)->
                    item{
                        Column (
                            horizontalAlignment=Alignment.CenterHorizontally,
                            verticalArrangement= Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clickable(
                                    indication = null,
                                    interactionSource = MutableInteractionSource()
                                ) {
                                    viewModel.onAction(
                                        UserAction.BoardTapped(cellNo)
                                    )
                                }

                                ){
                            AnimatedVisibility(
                                enter = scaleIn(tween(300)),
                                visible = viewModel.boardItems[cellNo] != BoardCellValue.NONE) {
                                if(boardCellValue == BoardCellValue.CIRLCE){
                                    Circle()
                                }else if(boardCellValue == BoardCellValue.CROSS){
                                    Cross()
                                }
                            }

                        }
                    }
                }
            }
            Column(
                modifier= Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    enter = scaleIn(tween(400)),
                    visible = state.hasWon) {
                    DrawVictoryLine(state = state)
                }
            }
            }

        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(state.hintText, fontSize = 24.sp, fontStyle = FontStyle.Italic)
            //Text("Drawer 0")
            Button(onClick = {
                             viewModel.onAction(UserAction.PlayAgainButtonClicked)
            }, shape =
            RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.buttonElevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlueCustom,
                    contentColor = Color.White
                )
            ) {
                Text("Play Again", fontSize = 16.sp)
            }

        }
    }
}

@Composable
fun PlayerBody(
    text:String,state: GameState,
){

        Box(
            modifier = Modifier
                .defaultMinSize(minHeight = 120.dp, minWidth = 120.dp)
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp))
                .clip(
                    RoundedCornerShape(20.dp)
                )
                .background(GrayBackground), contentAlignment = Alignment.Center
        ) {
            Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                Text(text, fontSize = 15.sp)
                when(text){
                    "Player 1" -> Text("${state.playerCircleCount}" ,fontSize = 25.sp, modifier = Modifier.padding(vertical = 5.dp), color = Aqua)
                    "Player 2" -> Text("${state.playerCrossCount}" ,fontSize = 25.sp, modifier = Modifier.padding(vertical = 5.dp), color =GreenishYellow )
                }
            }
        }









    
}

@Composable
fun DrawVictoryLine(
    state: GameState
){
    when(state.victoryType){
        VictoryType.HORIZONTAL1 -> WinHorizontalLine1()
        VictoryType.HORIZONTAL2 -> WinHorizontalLine2()
        VictoryType.HORIZONTAL3 -> WinHorizontalLine3()
        VictoryType.VERTICAL1 -> WinVerticalLine1()
        VictoryType.VERTICAL2 -> WinVerticalLine2()
        VictoryType.VERTICAL3 -> WinVerticalLine3()
        VictoryType.DIAGONAL1 -> WinDiagonalLine1()
        VictoryType.DIAGONAL2 -> WinDiagonalLine2()
        VictoryType.None -> {}
    }
}
