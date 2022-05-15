package rafael.ktsorter.sorter.alghoritm

import rafael.ktsorter.util.Descriptable
import java.util.*

enum class AveragePerformance(override val description: String) : Descriptable {
    N("n"),
    N_LOG_N("n log n"),
    N2("n" + 0xB2.toChar())
}

/**
 * Type sorting algorithm according Wikipedia site: https://en.wikipedia.org/wiki/Sorting_algorithm
 */
enum class SortType : Descriptable {
    EXCHANGE, SELECTION, INSERTION, MERGE, DISTRIBUTION, HYBRID, OTHER;

    override val description = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

}

data class SortInfo(val name: String, val type: SortType, val performance: AveragePerformance)
