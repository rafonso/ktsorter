package rafael.ktsorter.views.plot

import javafx.scene.layout.Region
import javafx.scene.shape.Shape
import tornadofx.getChildList

abstract class Plotter(protected val region: Region, initialValues: IntArray, protected val limits: Limits) {

    protected val colors = ColorManager.getColors(limits.quantity)

    protected fun xToRegion(x: Int): Double = x * region.width / limits.quantity

    protected fun yToRegion(y: Int): Double = (limits.quantity - y) * region.height / limits.quantity

    init {
        this.plot(initialValues)
    }

    protected abstract fun plotValues(values: IntArray)

    private fun plot(values: IntArray) {
        region.getChildList()?.removeIf { n -> n is Shape }
        plotValues(values)
    }

}
