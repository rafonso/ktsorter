package rafael.ktsorter.sorter.alghoritm

/**
 * https://en.wikipedia.org/wiki/Cycle_sort
 * https://www.geeksforgeeks.org/circle-sort/
 */
class CircleSorter(pauseTime: Long) : Sorter(pauseTime, INFO.type) {

    companion object {
        val INFO = SortInfo("Circle", SortType.EXCHANGE, AveragePerformance.N2)
    }

    private tailrec fun swap(values: IntArray, low: Int, lo: Int, hi: Int, swapped: Boolean): Boolean = when {
        lo > hi  -> swapped
        lo == hi -> {
            // special case arises only for list  of odd size
            if (super.isLesser(values, hi + 1, lo)) {
                super.swap(values, hi + 1, low)
            }
            true
        }
        else     -> {
            val nextSwapped = if (super.isLesser(values, hi, lo)) {
                super.swap(values, hi, lo)
                true
            } else {
                swapped
            }

            swap(values, low, lo + 1, hi - 1, nextSwapped)
        }
    }

    private fun sort(values: IntArray, low: Int, high: Int): Boolean {
        // base case
        if (low == high) {
            return false
        }

        val swapped = swap(values, low, low, high, false)

        // recursive case to check the traverse lists as sub lists
        val mid = (high - low) / 2
        val firstHalf = sort(values, low, low + mid)
        val secondHalf = sort(values, low + mid + 1, high)

        return swapped || firstHalf || secondHalf
    }


    override tailrec fun process(values: IntArray): IntArray =
            if (this.sort(values, 0, values.lastIndex)) process(values) else values

}
