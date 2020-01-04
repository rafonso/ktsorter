package rafael.ktsorter.views

import javafx.application.Platform
import javafx.concurrent.Task
import rafael.ktsorter.sorter.alghoritm.Sorter
import rafael.ktsorter.sorter.events.BasicSortEvent
import rafael.ktsorter.sorter.events.SortEvent
import rafael.ktsorter.sorter.events.SortListener
import rafael.ktsorter.views.plot.Plotter

class SorterTask(private val sorter: Sorter, private val plotter: Plotter) : Task<Any>(), SortListener {

    override fun call(): Any {
        sorter.subscribe(this)

        sorter.run(plotter.initialValues)
        return Any()
    }

    override fun onEvent(event: SortEvent) {
        Platform.runLater {
            when (event) {
                is BasicSortEvent -> {
                    Platform.runLater {
                        plotter.plot(event.values, event.positions, event.type)
                    }
                }
            }
        }

    }
}
