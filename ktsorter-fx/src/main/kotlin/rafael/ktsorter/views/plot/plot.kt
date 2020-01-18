package rafael.ktsorter.views.plot

import javafx.scene.Node
import javafx.scene.shape.Shape
import rafael.ktsorter.sorter.events.EventType
import rafael.ktsorter.views.Limits
import kotlin.math.cos
import kotlin.math.sin

const val ellipseFactor = 0.95

data class CartesianCoordinate(val x: Double, val y: Double) {
    operator fun plus(p: CartesianCoordinate) = CartesianCoordinate(this.x + p.x, this.y + p.y)

//    val polarCoordinate: PolarCoordinate by lazy {
//        PolarCoordinate(hypot(this.x, this.y), atan2(this.y, this.x))
//    }
//
}

data class PolarCoordinate(val radius: Double, val theta: Double) {

    val cartesianCoordinate: CartesianCoordinate by lazy {
        CartesianCoordinate(radius * cos(theta), radius * sin(theta))
    }

}

internal fun basicPlotPositions(shapes: List<Node>, positions: List<Int>, eventType: EventType, limits: Limits) {
    when (eventType) {
        EventType.COMPARSION          -> positions.map { shapes[it] }.map { it as Shape }.forEach { shape ->
            shape.strokeWidth = limits.radius / 2
            shape.strokeDashArray.addAll(0.6, 0.2)
        }
        EventType.SWAP, EventType.SET -> positions.map { shapes[it] }.map { it as Shape }.forEach { shape ->
            shape.strokeWidth = limits.radius / 2
            shape.strokeDashArray.addAll(0.2, 0.2)
        }
    }
}
