package rafael.ktsorter.views.plot

import javafx.scene.layout.Region
import javafx.scene.shape.Shape
import tornadofx.add
import tornadofx.getChildList

abstract class Plotter(private val region: Region, initialValues: IntArray, protected val limits: Limits) {

    protected val colors = ColorManager.getColors(limits.quantity)

    protected fun xToRegion(x: Int): Double = x * region.width / limits.quantity

    protected fun yToRegion(y: Int): Double = (limits.quantity - y) * region.height / limits.quantity

    init {
        this.initPlotter()
        this.plot(initialValues)
    }

    protected abstract fun initPlotter()

    protected abstract fun plotValues(values: IntArray): Iterable<Shape>

    private fun plot(values: IntArray) {
        region.getChildList()?.removeIf { n -> n is Shape }

        val shapes = plotValues(values)
        shapes.forEach(region::add)

    }

}
