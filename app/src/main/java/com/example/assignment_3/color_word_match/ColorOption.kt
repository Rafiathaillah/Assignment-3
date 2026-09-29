package com.example.assignment_3.color_word_match

import androidx.compose.ui.graphics.Color

data class ColorOption(val name: String, val color: Color)

val colorOptions = listOf(
    ColorOption("RED", Color(0xFFE53935)),
    ColorOption("GREEN", Color(0xFF4CAF50)),
    ColorOption("BLUE", Color(0xFF1E88E5)),
    ColorOption("YELLOW", Color(0xFFFFBA1E)),
    ColorOption("PURPLE", Color(0xFF8E24AA)),
    ColorOption("ORANGE", Color(0xFFFB8C00)),
)