package com.example.assignment_3.RPS

enum class RPSPick (val emoji: String, val label: String) {
    ROCK("✊", "Rock"),
    PAPER("✋", "Paper"),
    SCISSOR("✌", "Scissor");

    val displayText: String
        get() = "$emoji $label"

    fun battle(other: RPSPick): Result{
        if (this == other) return Result.DRAW
        return when (this){
            ROCK -> if (other == SCISSOR) Result.WIN else Result.LOSE
            PAPER -> if (other == ROCK) Result.WIN else Result.LOSE
            SCISSOR -> if (other == PAPER) Result.WIN else Result.LOSE
        }
    }
}