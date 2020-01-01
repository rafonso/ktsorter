package rafael.ktsorter.views.plot

import kotlin.math.*

data class CartesianCoordinate(val x: Double, val y: Double) {
    operator fun plus(p: CartesianCoordinate) = CartesianCoordinate(this.x + p.x, this.y + p.y)

    val polarCoordinate: PolarCoordinate by lazy {
        PolarCoordinate(hypot(this.x, this.y), atan2(this.y, this.x))
    }

}

data class PolarCoordinate(val radius: Double, val theta: Double) {

    val cartesianCoordinate: CartesianCoordinate by lazy {
        CartesianCoordinate(radius * cos(theta), radius * sin(theta))
    }

}
