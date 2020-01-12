package rafael.ktsorter.sorter.alghoritm

/**
 * https://en.wikipedia.org/wiki/Pancake_sorting
 * https://rosettacode.org/wiki/Sorting_algorithms/Pancake_sort#JavaScript
 */
class PancakeSorter(pauseTime: Long) : Sorter(pauseTime) {

    companion object {
        val INFO = SortInfo("Pancake", SortType.OTHER, AveragePerformance.N)
    }

    private tailrec fun findMaxIdx(values: IntArray, n: Int, j: Int = 1, maxIdx: Int = 0): Int {
        if (j > n) {
            return maxIdx
        }

        val nextMaxIdx = if (!super.isLesser(values, j, maxIdx)) j else maxIdx

        return findMaxIdx(values, n, j + 1, nextMaxIdx)
    }

    private tailrec fun flip(values: IntArray, i: Int, j: Int) {
        if (i >= j) {
            return
        }

        super.swap(values, i, j)

        flip(values, i + 1, j - 1)
    }

    private tailrec fun sort(values: IntArray, i: Int) {
        if (i == 0) {
            return
        }

        val maxIdx = findMaxIdx(values, i)
        if (maxIdx < i) {
            flip(values, maxIdx, i)
        }

        sort(values, i - 1)
    }

    override fun process(values: IntArray): IntArray {
        sort(values, values.lastIndex)

        return values
    }
}
