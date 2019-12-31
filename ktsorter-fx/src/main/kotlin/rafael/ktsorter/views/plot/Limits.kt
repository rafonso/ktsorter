package rafael.ktsorter.views.plot

val limitsValues = listOf(
    Limits(5, 2.0),
    Limits(10, 2.0),
    Limits(20, 2.0),
    Limits(50, 2.0),
    Limits(100, 2.0),
    Limits(200, 1.0),
    Limits(500, 1.0),
    Limits(1000, 0.5)
)

data class Limits(val quantity: Int, val radius: Double)
