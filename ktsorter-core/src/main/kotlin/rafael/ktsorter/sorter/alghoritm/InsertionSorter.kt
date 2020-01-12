package rafael.ktsorter.sorter.alghoritm

/**
 * https://en.wikipedia.org/wiki/Insertion_sort
 * https://khan4019.github.io/front-end-Interview-Questions/sort.html#insertionSort
 */
class InsertionSorter(pauseTime: Long) : Sorter(pauseTime) {

    companion object {
        val INFO = SortInfo("Insertion", SortType.INSERTION, AveragePerformance.N2)
    }

    private tailrec fun findInsertionPos(values: IntArray, value: Int, j: Int): Int {
        if(j == 0 || super.isLesserThan(values, j - 1, value)) {
            return j
        }

        super.set(values, j, values[j - 1])

        return findInsertionPos(values, value, j - 1)
    }

    private tailrec fun sort(values: IntArray, begin: Int, end: Int): IntArray {
        if (begin >= end) {
            return values
        }

        val value = values[begin]
        val j = findInsertionPos(values, value, begin)
        super.set(values, j, value)

        return sort(values, begin + 1, end)
    }

    override fun process(values: IntArray): IntArray = sort(values, 1, values.size)

}
