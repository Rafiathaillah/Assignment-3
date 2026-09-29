package com.example.assignment_3.RPS

data class RPSOption(val name: String, val option: RPSPick)

val rpsOptions = listOf(
    RPSOption("✊ ROCK", RPSPick.ROCK),
    RPSOption("✋ PAPER", RPSPick.PAPER),
    RPSOption("✌ SCISSOR", RPSPick.SCISSOR)
)