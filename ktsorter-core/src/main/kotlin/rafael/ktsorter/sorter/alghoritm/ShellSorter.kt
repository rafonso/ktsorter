package rafael.ktsorter.sorter.alghoritm

/**
 * https://en.wikipedia.org/wiki/Shellsort
 * https://www.w3resource.com/javascript-exercises/searching-and-sorting-algorithm/searching-and-sorting-algorithm-exercise-6.php
 */
class ShellSorter(pauseTime: Long) : Sorter(pauseTime) {

    companion object {
        val INFO = SortInfo("Shell", SortType.INSERTION, AveragePerformance.N_LOG_N)
    }

    private tailrec fun shellSort1(values: IntArray, increment: Int, temp: Int, j: Int): Int {
        if (j < increment || super.isLesserThan(values, j - increment, temp)) {
            return j
        }

        super.set(values, j, values[j - increment])

        return shellSort1(values, increment, temp, j - increment)
    }

    private tailrec fun shellSort(values: IntArray, increment: Int, i: Int) {
        if (i >= values.size) {
            return
        }

        val temp = values[i]
        val j = shellSort1(values, increment, temp, i)
        super.set(values, j, temp)

        shellSort(values, increment, i + 1)
    }

    private tailrec fun sort(values: IntArray, increment: Int): IntArray {
        if (increment == 0) {
            return values
        }

        shellSort(values, increment, increment)

        val newIncrement = if (increment == 2) 1 else (increment * 5.0 / 11).toInt()
        return sort(values, newIncrement)
    }

    override fun process(values: IntArray): IntArray = sort(values, values.size / 2)

}
