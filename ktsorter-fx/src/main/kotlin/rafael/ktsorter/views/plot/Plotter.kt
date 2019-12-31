package rafael.ktsorter.views.plot

import javafx.scene.layout.Region
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Shape
import tornadofx.add
import tornadofx.getChildList
import java.lang.Exception

class Plotter(private val region: Region, private val initialValues: IntArray, private val limits: Limits) {

    private val colors = ColorManager.getColors(limits.quantity)

    private fun xToRegion(x: Int): Double = x * region.width / initialValues.size

    private fun yToRegion(y: Int): Double = (limits.quantity - y) * region.height / limits.quantity

    private fun valueToRegion(values: IntArray, i: Int) = try {
        Triple(xToRegion(i + 1), yToRegion(values[i] - 1), colors[values[i] - 1])
    } catch (e: Exception) {
        throw Exception("$i - $values: ${e.message}", e)
    }

    init {
        this.plot(initialValues)
    }

    private fun plot(values: IntArray) {
        region.getChildList()?.removeIf { n -> n is Shape }

        values.indices.map {
            valueToRegion(
                values,
                it
            )
        }.map {
            Circle(it.first, it.second, limits.radius, it.third).also { c ->
                c.stroke = Color.BLACK
                c.strokeWidth = limits.radius / 4
            }
        }.forEach {
            region.add(it)
        }
    }

}
