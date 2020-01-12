package rafael.ktsorter.sorter.alghoritm

/**
 * https://en.wikipedia.org/wiki/Quicksort
 * https://khan4019.github.io/front-end-Interview-Questions/sort.html#quickSort
 */
class QuickSorter(pauseTime: Long) : QuickSorterBase(pauseTime) {

    companion object {
        val INFO = SortInfo("Quick", SortType.EXCHANGE, AveragePerformance.N_LOG_N)
    }

}
