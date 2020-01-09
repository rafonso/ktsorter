package rafael.ktsorter.sorter.alghoritm

private const val MIN_SIZE = 10

/**
 * https://megocode3.wordpress.com/2008/01/28/8/
 */
class QuickInsertSorter(pauseTime: Long) : QuickSorterBase(pauseTime, TYPE) {

    companion object {
        val TYPE = SortType.HYBRID
    }

    private tailrec fun findInsertionPos(values: IntArray, value: Int, j: Int): Int {
        if (j <= 0 || super.isLesserThan(values, j - 1, value)) {
            return j
        }

        super.set(values, j, values[j - 1])

        return findInsertionPos(values, value, j - 1)
    }

    private tailrec fun executeInsert(values: IntArray, right: Int, i: Int): IntArray {
        if (i >= right) {
            return values
        }

        val value = values[i]
        val j = findInsertionPos(values, value, i)
        super.set(values, j, value)

        return executeInsert(values, right, i + 1)
    }

    override fun sort(values: IntArray, left: Int, right: Int): IntArray =
            if (right - left >= MIN_SIZE) super.sort(values, left, right) else executeInsert(values, right + 1, left + 1)

}
