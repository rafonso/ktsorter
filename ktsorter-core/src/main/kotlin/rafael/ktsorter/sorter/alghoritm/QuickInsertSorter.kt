package rafael.ktsorter.sorter.alghoritm

private const val MIN_SIZE = 10

/**
 * https://megocode3.wordpress.com/2008/01/28/8/
 */
class QuickInsertSorter(pauseTime: Long) : QuickSorterBase(pauseTime) {

    companion object {
        val INFO = SortInfo("Quick Insert", SortType.HYBRID, AveragePerformance.N_LOG_N)
    }

    private tailrec fun findInsertionPos(values: IntArray, value: Int, j: Int): Int {
        if (j <= 0 || super.isLesserThan(values, j - 1, value)) {
            return j
        }

        super.set(values, j, values[j - 1])

        return findInsertionPos(values, value, j - 1)
    }

    private tailrec fun executeInsert(values: IntArray, end: Int, i: Int): IntArray {
        if (i >= end) {
            return values
        }

        val value = values[i]
        val j = findInsertionPos(values, value, i)
        super.set(values, j, value)

        return executeInsert(values, end, i + 1)
    }

    override fun sort(values: IntArray, begin: Int, end: Int): IntArray =
            if (end - begin >= MIN_SIZE) super.sort(values, begin, end) else executeInsert(values, end + 1, begin + 1)

}
