package com.example.unscramble.ui

data class GameUIState(
    val currentScrambledWord: String = "",
    val score: Int = 0,
    val currentWordCount: Int = 1,
    val isGuessedWordWrong: Boolean = false,
    val isGameOver: Boolean = false,
)
