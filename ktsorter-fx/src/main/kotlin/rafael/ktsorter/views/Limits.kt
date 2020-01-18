package rafael.ktsorter.views

import rafael.ktsorter.util.Descriptable

val limitsValues = listOf(
    // @formatter:off
    Limits(2 shl 2, 2.0,  4,  2),
    Limits(2 shl 3, 2.0,  4,  4),
    Limits(2 shl 4, 2.0,  8,  4),
    Limits(2 shl 5, 2.0,  8,  8),
    Limits(2 shl 6, 2.0, 16,  8),
    Limits(2 shl 7, 1.0, 16, 16),
    Limits(2 shl 8, 1.0, 32, 16),
    Limits(2 shl 9, 0.5, 32, 32, false)
    // @formatter:on
)

data class Limits(
        val quantity: Int,
        val radius: Double,
        val columns: Int,
        val rows: Int,
        val allowedForImages: Boolean = true
) : Descriptable {

    override val description: String
        get() = quantity.toString()

}

