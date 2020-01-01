package rafael.ktsorter.views.plot

import javafx.scene.layout.Region
import rafael.ktsorter.util.Descriptable

enum class Plotters(override val description: String) : Descriptable {

    BARS("Bars") {
        override fun createPlotter(region: Region, initialValues: IntArray, limits: Limits): Plotter =
            BarsPlotter(region, initialValues, limits)
    },
    SCATTER("Scatter") {
        override fun createPlotter(region: Region, initialValues: IntArray, limits: Limits): Plotter =
            ScatterPlotter(region, initialValues, limits)
    },
    RADIAL_SCATTER("Radial Scatter") {
        override fun createPlotter(region: Region, initialValues: IntArray, limits: Limits): Plotter =
            RadialScatterPlotter(region, initialValues, limits)
    }
    ;

    abstract fun createPlotter(region: Region, initialValues: IntArray, limits: Limits): Plotter
}

