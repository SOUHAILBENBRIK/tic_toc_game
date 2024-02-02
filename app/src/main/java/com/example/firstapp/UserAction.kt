package com.example.firstapp

sealed class UserAction{
    object PlayAgainButtonClicked :UserAction()
    data class  BoardTapped(val cellNo:Int):UserAction()
}
