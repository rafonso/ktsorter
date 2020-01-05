package rafael.ktsorter.sorter.alghoritm

import kotlin.math.max

private const val SHRINK_FACTOR = 1.3

/**
 * https://en.wikipedia.org/wiki/Comb_sort
 * https://gist.github.com/hiroshi-maybe/4701011
 */
class CombSorter(pauseTime: Long) : Sorter(pauseTime) {

    private fun newInverval(interval: Int) = max((interval / SHRINK_FACTOR).toInt(), 1)

    private tailrec fun sort(values: IntArray, interval: Int, sorted: Boolean): IntArray {
        if (interval <= 1 && sorted) {
            return values
        }

        var currentlySorted = true
        for (i in 0 until (values.size - interval)) {
            if (super.isLesser(values, i + interval, i)) {
                super.swap(values, i + interval, i)
                currentlySorted = false
            }
        }

        return sort(values, newInverval(interval), currentlySorted)
    }

    override fun process(values: IntArray): IntArray = sort(values, newInverval(values.size), false)

}
