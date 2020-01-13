package rafael.ktsorter.sorter.alghoritm

/**
 * https://en.wikipedia.org/wiki/Radix_sort
 */
class RadixSorter(pauseTime: Long) : Sorter(pauseTime) {

    companion object {
        val INFO = SortInfo("Radix", SortType.DISTRIBUTION, AveragePerformance.N)
    }

    private fun countSort(values: IntArray, exp: Int) {
        val output = IntArray(values.size)
        val count = (0 until 10).map { 0 }.toIntArray()
        val posCount = { i: Int -> values[i] / exp % 10 }

        // Store count of occurrences in count[]
        (values.indices).forEach { count[posCount(it)]++ }

        // Change count[i] so that count[i] now contains actual position of this digit in output[]
        (1 until 10).forEach { count[it] += count[it - 1] }

        // Build the output array
        (values.lastIndex downTo 0).forEach {
            output[count[posCount(it)] - 1] = values[it]
            count[posCount(it)]--
        }

        // Copy the output array to arr[], so that arr[] now
        // contains sorted numbers according to current digit
        (values.indices).forEach { super.set(values, it, output[it]) }
    }

    private tailrec fun sort(values: IntArray, max: Int, exp: Int): IntArray {
        if (exp > max) {
            return values
        }

        countSort(values, exp)

        return sort(values, max, exp * 10)
    }

    override fun process(values: IntArray): IntArray = sort(values, values.max()!!, 1)

}
