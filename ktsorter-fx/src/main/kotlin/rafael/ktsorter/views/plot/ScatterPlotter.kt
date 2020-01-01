package rafael.ktsorter.views.plot

import javafx.scene.layout.Region
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import tornadofx.add

class ScatterPlotter(region: Region, initialValues: IntArray, limits: Limits) : Plotter(region, initialValues, limits) {

    private fun valueToRegion(
        values: IntArray,
        i: Int,
        indexToRegion: List<Double>
    ) = try {
        Triple(indexToRegion[i], yToRegion(values[i] - 1), colors[values[i] - 1])
    } catch (e: Exception) {
        throw Exception("$i - ${values.toList()}: ${e.message}", e)
    }

    override fun plotValues(values: IntArray) {
        val indexToRegion = 0.rangeTo(super.limits.quantity).map { xToRegion(it + 1) }
        values.indices.map {
            valueToRegion(
                values,
                it,
                indexToRegion
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

