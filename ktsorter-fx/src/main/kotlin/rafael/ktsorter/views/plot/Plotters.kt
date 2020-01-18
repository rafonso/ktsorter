package rafael.ktsorter.views.plot

import javafx.scene.layout.Region
import rafael.ktsorter.util.Descriptable
import rafael.ktsorter.views.Limits

enum class Plotters(override val description: String, val isImage: Boolean = false) : Descriptable {

    BARS("Bars") {
        override fun createPlotter(region: Region, initialValues: IntArray, limits: Limits): Plotter =
            BarsPlotter(region, initialValues, limits)
    },
    HORIZONTAL_IMAGE("Horizontal Image", true) {
        override fun createPlotter(region: Region, initialValues: IntArray, limits: Limits): Plotter =
            HorizontalImagePlotter(region, initialValues, limits)
    },
    VERTICAL_IMAGE("Vertical Image", true) {
        override fun createPlotter(region: Region, initialValues: IntArray, limits: Limits): Plotter =
            VerticalImagePlotter(region, initialValues, limits)
    },
    TILES_IMAGE("Tiles Image", true) {
        override fun createPlotter(region: Region, initialValues: IntArray, limits: Limits): Plotter =
            TilesImagePlotter(region, initialValues, limits)
    },
    VERTICAL_BARS("Vertical Bars") {
        override fun createPlotter(region: Region, initialValues: IntArray, limits: Limits): Plotter =
            VerticalBarsPlotter(region, initialValues, limits)
    },
    HORIZONTAL_BARS("Horizontal Bars") {
        override fun createPlotter(region: Region, initialValues: IntArray, limits: Limits): Plotter =
            HorizontalBarsPlotter(region, initialValues, limits)
    },
    TILES("Tiles") {
        override fun createPlotter(region: Region, initialValues: IntArray, limits: Limits): Plotter =
            TilesPlotter(region, initialValues, limits)
    },
    SCATTER("Scatter") {
        override fun createPlotter(region: Region, initialValues: IntArray, limits: Limits): Plotter =
            ScatterPlotter(region, initialValues, limits)
    },
    RADIAL_SCATTER("Radial Scatter") {
        override fun createPlotter(region: Region, initialValues: IntArray, limits: Limits): Plotter =
            RadialScatterPlotter(region, initialValues, limits)
    },
    RADIAL_BARS_SCATTER("Radial Bars Scatter") {
        override fun createPlotter(region: Region, initialValues: IntArray, limits: Limits): Plotter =
            RadialBarsPlotter(region, initialValues, limits)
    },
    ELLIPSE("Ellipse") {
        override fun createPlotter(region: Region, initialValues: IntArray, limits: Limits): Plotter =
            EllipsePlotter(region, initialValues, limits)
    },
    ;

    abstract fun createPlotter(region: Region, initialValues: IntArray, limits: Limits): Plotter
}

