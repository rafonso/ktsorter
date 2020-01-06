package rafael.ktsorter.sorter.alghoritm

import java.util.stream.Collectors

/**
 * https://en.wikipedia.org/wiki/Quicksort
 * https://khan4019.github.io/front-end-Interview-Questions/sort.html#quickSort
 */
class QuickSorter(pauseTime: Long) : Sorter(pauseTime) {

    private fun partition(values: IntArray, pivot: Int, left: Int, right: Int): Int {
        val pivotValue = values[pivot]
        var partitionIndex = left

        for (i in left until right) {
            if (super.isLesserThan(values, i, pivotValue)) {
                super.swap(values, i, partitionIndex)
                partitionIndex++
            }
        }
        super.swap(values, right, partitionIndex)

        return partitionIndex
    }

    private fun sort(values: IntArray, left: Int, right: Int): IntArray {
        if (left < right) {
            val partitionIndex = this.partition(values, right, left, right)

            this.sort(values, left, partitionIndex - 1)
            this.sort(values, partitionIndex + 1, right)
        }

        return values
    }

    override fun process(values: IntArray): IntArray = sort(values, 0, values.lastIndex)

}
