package rafael.ktsorter.views.plot

import javafx.scene.Node
import javafx.scene.layout.Region
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape
import rafael.ktsorter.sorter.events.EventType
import rafael.ktsorter.views.Limits

class VerticalBarsPlotter(region: Region, initialValues: IntArray, limits: Limits) : Plotter(region, initialValues, limits) {

    private lateinit var indexToXRegion: List<Double>
    private var width: Double = 0.0

    private fun valueToBar(index: Int, value: Int): Rectangle =
            Rectangle(indexToXRegion[index], 0.0, width, region.height).also { r ->
                r.fill = colors[value - 1]
            }

    override fun initPlotter() {
        width = region.width / limits.quantity
        indexToXRegion = (0 until limits.quantity).map { it * width }
    }

    override fun plotValues(values: List<Int>): List<Shape> = values.mapIndexed(this::valueToBar)

    override fun plotPositions(shapes: List<Node>, positions: List<Int>, eventType: EventType) {
        basicPlotPositions(shapes, positions, eventType, limits)
    }

}
