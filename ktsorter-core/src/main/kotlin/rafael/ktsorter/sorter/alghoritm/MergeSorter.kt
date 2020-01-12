package rafael.ktsorter.sorter.alghoritm

/**
 * https://en.wikipedia.org/wiki/Merge_sort
 * http://andreinc.net/2010/12/22/the-merge-sort-algorithm-implementation-in-java/
 */
class MergeSorter(pauseTime: Long) : Sorter(pauseTime) {

    companion object {
        val INFO = SortInfo("Merge", SortType.MERGE, AveragePerformance.N_LOG_N)
    }

    private tailrec fun setValues(values: IntArray, end: Int, larray: IntArray, rarray: IntArray, k: Int, m: Int = 0, n: Int = 0) {
        if (k > end) {
            return
        }

        val (value, nextM, nextN) = if (larray[m] <= rarray[n]) Triple(larray[m], m + 1, n) else Triple(rarray[n], m, n + 1)
        super.set(values, k, value)

        setValues(values, end, larray, rarray, k + 1, nextM, nextN)
    }

    private fun merge(values: IntArray, begin: Int, mid: Int, end: Int) {
        /* Additional Helper Arrays */
        val larraySize = mid - begin + 1
        val rarraySize = end - mid
        val larray = IntArray(larraySize + 1)
        val rarray = IntArray(rarraySize + 1)

        /* Sentinel values, to avoid additional checks
        when we are merging larray and rarray */
        larray[larraySize] = Int.MAX_VALUE
        rarray[rarraySize] = Int.MAX_VALUE

        (0 until larraySize).forEach { larray[it] = values[begin + it] }
        (0 until rarraySize).forEach { rarray[it] = values[mid + it + 1] }

        setValues(values, end, larray, rarray, begin)
    }

    private fun sort(values: IntArray, begin: Int, end: Int) {
        if (begin >= end) {
            return
        }

        val mid = (begin + end) / 2

        this.sort(values, begin, mid)
        this.sort(values, mid + 1, end)
        this.merge(values, begin, mid, end)
    }

    override fun process(values: IntArray): IntArray {
        sort(values, 0, values.lastIndex)

        return values
    }

}
