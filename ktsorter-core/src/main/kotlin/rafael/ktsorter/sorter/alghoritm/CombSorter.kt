package rafael.ktsorter.sorter.alghoritm

import kotlin.math.max

private const val SHRINK_FACTOR = 1.3

/**
 * https://en.wikipedia.org/wiki/Comb_sort
 * https://gist.github.com/hiroshi-maybe/4701011
 */
class CombSorter(pauseTime: Long) : Sorter(pauseTime, TYPE) {

    companion object {
        val TYPE = SortType.EXCHANGE
    }

    private fun newInverval(interval: Int) = max((interval / SHRINK_FACTOR).toInt(), 1)

    private tailrec fun passComb(values: IntArray, interval: Int, currentlySorted: Boolean = true, i: Int = 0): Boolean {
        if (i >= values.size - interval) {
            return currentlySorted
        }

        val isSorted = if (super.isLesser(values, i + interval, i)) {
            super.swap(values, i + interval, i)
            false
        } else {
            currentlySorted
        }

        return passComb(values, interval, isSorted, i + 1)
    }

    private tailrec fun sort(values: IntArray, interval: Int, sorted: Boolean): IntArray {
        if (interval <= 1 && sorted) {
            return values
        }

        val currentlySorted = passComb(values, interval)

        return sort(values, newInverval(interval), currentlySorted)
    }

    override fun process(values: IntArray): IntArray = sort(values, newInverval(values.size), false)

}
