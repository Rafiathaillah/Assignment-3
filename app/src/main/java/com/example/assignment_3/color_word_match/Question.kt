package com.example.assignment_3.color_word_match

data class Question (
    val mode: Mode,
    val wordOption: ColorOption,
    val colorOption: ColorOption,
    val options: List<String>,
    val correctAnswer: String
)

fun generateQuestion(): Question {
    val mode = Mode.entries.random()
    val wordOption = colorOptions.random()
    val colorOption = colorOptions.filter { it != wordOption }.random()

    val correctAnswer = if (mode == Mode.COLOR) colorOption.name else wordOption.name
    val options = listOf(wordOption.name, colorOption.name).shuffled()

    return Question(
        mode = mode,
        wordOption = wordOption,
        colorOption = colorOption,
        options = options,
        correctAnswer = correctAnswer
    )
}