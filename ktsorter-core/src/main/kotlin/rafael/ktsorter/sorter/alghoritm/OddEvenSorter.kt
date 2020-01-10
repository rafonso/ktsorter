package rafael.ktsorter.sorter.alghoritm

/**
 * https://en.wikipedia.org/wiki/Odd%E2%80%93even_sort
 */
class OddEvenSorter(pauseTime: Long) : Sorter(pauseTime, INFO.type) {

    companion object {
        val INFO = SortInfo("Odd Even", SortType.EXCHANGE, AveragePerformance.N2)
    }

    private tailrec fun sort(values: IntArray, i: Int, sorted: Boolean = true): Boolean {
        if (i >= values.lastIndex) {
            return sorted
        }

        val currentSort = if (super.isLesser(values, i + 1, i)) {
            super.swap(values, i + 1, i)
            false
        } else sorted

        return sort(values, i + 2, currentSort)
    }

    override tailrec fun process(values: IntArray): IntArray {
        val oddsSorted  = sort(values, 0)
        val evensSorted = sort(values, 1)

        return if (oddsSorted && evensSorted) values else process(values)
    }
}
