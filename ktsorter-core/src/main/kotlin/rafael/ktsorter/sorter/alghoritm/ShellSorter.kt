package rafael.ktsorter.sorter.alghoritm

/**
 * https://en.wikipedia.org/wiki/Shellsort
 * https://www.w3resource.com/javascript-exercises/searching-and-sorting-algorithm/searching-and-sorting-algorithm-exercise-6.php
 */
class ShellSorter(pauseTime: Long) : Sorter(pauseTime) {

    private tailrec fun sort(values: IntArray, increment: Int): IntArray {
        if (increment == 0) {
            return values
        }

        for (i in increment until values.size) {
            var j = i
            val temp = values[i]

            while (j >= increment && !super.isLesserThan(values, j - increment, temp)) {
                super.set(values, j, values[j - increment])
                j -= increment
            }

            super.set(values, j, temp)
        }

        val newIncrement = if (increment == 2) 1 else (increment * 5.0 / 11).toInt()
        return sort(values, newIncrement)
    }

    override fun process(values: IntArray): IntArray = sort(values, values.size / 2)

}
