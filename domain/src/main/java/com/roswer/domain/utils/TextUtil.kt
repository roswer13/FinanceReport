package com.roswer.domain.utils

import kotlin.random.Random

/**
 * Generates a random paragraph with a specified number of words.
 *
 * @param wordCount The number of words to include in the paragraph.
 * @param allowEmpty If true, the function may return an empty string.
 * @return A randomly generated paragraph as a String.
 */
fun generateRandomParagraph(wordCount: Int, allowEmpty: Boolean = false): String {
    if (allowEmpty && Random.nextBoolean()) return ""

    val words = listOf(
        "innovador",
        "tecnología",
        "inteligente",
        "rápido",
        "eficiente",
        "sistema",
        "producto",
        "plataforma",
        "servicio",
        "digital",
        "optimizado",
        "dinámico",
        "integrado",
        "seguro",
        "automatizado",
        "experiencia",
        "usuario",
        "versátil",
        "escalable",
        "moderno"
    )

    return (1..wordCount).map { words.random() }
        .chunked(Random.nextInt(6, 12)) // Divide en oraciones de 6 a 12 palabras
        .joinToString(". ") { it.joinToString(" ").replaceFirstChar { it.uppercase() } } + "."
}