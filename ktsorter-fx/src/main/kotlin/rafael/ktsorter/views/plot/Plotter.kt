package rafael.ktsorter.views.plot

import javafx.scene.Node
import javafx.scene.layout.Region
import javafx.scene.shape.Shape
import rafael.ktsorter.sorter.events.EventType
import rafael.ktsorter.views.Limits
import tornadofx.add
import tornadofx.getChildList

abstract class Plotter(protected val region: Region, val initialValues: IntArray, protected val limits: Limits) {

    protected val colors = ColorManager.getColors(limits.quantity)

    protected fun xToRegion(x: Int): Double = x * region.width / limits.quantity

    protected fun yToRegion(y: Int): Double = (limits.quantity - y) * region.height / limits.quantity

    init {
        this.initPlotter()
        this.plot(initialValues.toList(), emptyList(), EventType.IDLE)
    }

    protected abstract fun initPlotter()

    protected abstract fun plotValues(values: List<Int>): List<Node>

    protected abstract fun plotPositions(shapes: List<Node>, positions: List<Int>, eventType: EventType)

    fun plot(values: List<Int>, positions: List<Int>, eventType: EventType) {
        region.getChildList()?.clear()

        val shapes = plotValues(values)
        plotPositions(shapes, positions, eventType)
        shapes.forEach(region::add)
    }

}
