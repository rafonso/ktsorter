package rafael.ktsorter.views.plot

import rafael.ktsorter.util.Descriptable

val limitsValues = listOf(
    Limits(2 shl 2, 2.0),
    Limits(2 shl 3, 2.0),
    Limits(2 shl 4, 2.0),
    Limits(2 shl 5, 2.0),
    Limits(2 shl 6, 2.0),
    Limits(2 shl 7, 1.0),
    Limits(2 shl 8, 1.0),
    Limits(2 shl 9, 0.5)
)

data class Limits(val quantity: Int, val radius: Double) : Descriptable {

    override val description: String
        get() = quantity.toString()

}

