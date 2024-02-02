package com.example.firstapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel:ViewModel() {
    var state by mutableStateOf(GameState())
    val boardItems : MutableMap<Int,BoardCellValue> = mutableMapOf(
        1 to BoardCellValue.NONE,
        2 to BoardCellValue.NONE,
        3 to BoardCellValue.NONE,
        4 to BoardCellValue.NONE,
        5 to BoardCellValue.NONE,
        6 to BoardCellValue.NONE,
        7 to BoardCellValue.NONE,
        8 to BoardCellValue.NONE,
        9 to BoardCellValue.NONE,




    )
    fun onAction(action: UserAction){
        when(action){
            is UserAction.BoardTapped -> {
                addValueToBoard(action.cellNo)
            }
            UserAction.PlayAgainButtonClicked ->{
                gameReset()
            }
        }
    }

    private fun gameReset() {
        boardItems.forEach{
            (i,_)-> boardItems[i] = BoardCellValue.NONE
        }
        state = state.copy(
            hintText = "Player 'O' turn" ,
            currentTurn = BoardCellValue.CIRLCE,
            victoryType = VictoryType.None,
            hasWon = false
        )
        }


    private fun addValueToBoard(cellNo: Int) {
        if(boardItems[cellNo] != BoardCellValue.NONE){
            return
        }
        if(state.currentTurn == BoardCellValue.CIRLCE){
            boardItems[cellNo] = BoardCellValue.CIRLCE
            if(checkForVictory(BoardCellValue.CIRLCE)){
                state = state.copy(
                    hintText = "Player 'O' Won",
                    playerCircleCount = state.playerCircleCount+1,
                    currentTurn = BoardCellValue.NONE,
                    hasWon = true
                )
            }
            else if (hasBoardFillAll()){
                state=state.copy(
                    hintText = "Game Draw",
                    drawCount = state.drawCount+1,

                )
            }else{
                state = state.copy(
                    hintText = "Player 'X' turn",
                    currentTurn = BoardCellValue.CROSS
                )
            }
        }
        else if(state.currentTurn == BoardCellValue.CROSS){
            boardItems[cellNo] = BoardCellValue.CROSS
            if(checkForVictory(BoardCellValue.CROSS)){
                state = state.copy(
                    hintText = "Player 'X' Won",
                    playerCrossCount = state.playerCrossCount+1,
                    currentTurn = BoardCellValue.NONE,
                    hasWon = true
                )
            }
            else if (hasBoardFillAll()){
                state=state.copy(
                    hintText = "Game Draw",
                    drawCount = state.drawCount+1,

                    )
            }else{
                state = state.copy(
                    hintText = "Player 'O' turn",
                    currentTurn = BoardCellValue.CIRLCE
                )
            }

        }

    }

    private fun checkForVictory(value: BoardCellValue): Boolean {
        when{
            boardItems[1] == value && boardItems[2]==value && boardItems[3]==value ->{
                state=state.copy(
                    victoryType = VictoryType.HORIZONTAL1)
                return true

            }
            boardItems[4] == value && boardItems[5]==value && boardItems[6]==value ->{
                state=state.copy(
                    victoryType = VictoryType.HORIZONTAL2)
                return true

            }
            boardItems[7] == value && boardItems[8]==value && boardItems[9]==value ->{
                state=state.copy(
                    victoryType = VictoryType.HORIZONTAL3)
                return true
            }
            boardItems[1] == value && boardItems[4]==value && boardItems[7]==value ->{
                state=state.copy(
                    victoryType = VictoryType.VERTICAL1)
                return true
            }
            boardItems[2] == value && boardItems[5]==value && boardItems[8]==value ->{
                state=state.copy(
                    victoryType = VictoryType.VERTICAL2)
                return true
            }
            boardItems[3] == value && boardItems[6]==value && boardItems[9]==value ->{
                state=state.copy(
                    victoryType = VictoryType.VERTICAL3)
                return true
            }
            boardItems[1] == value && boardItems[5]==value && boardItems[9]==value ->{
                state=state.copy(
                    victoryType = VictoryType.DIAGONAL1)
                return true
            }
            boardItems[3] == value && boardItems[5]==value && boardItems[7]==value ->{
                state=state.copy(
                    victoryType = VictoryType.DIAGONAL2)
                return true
            }
            else -> return false } }

    private fun hasBoardFillAll(): Boolean {

        if (boardItems.containsValue(BoardCellValue.NONE)) return false
        return  true
    }
}