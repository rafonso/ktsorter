package rafael.ktsorter.sorter.alghoritm

class BubbleSorter(pauseTime: Long) : Sorter(pauseTime, INFO.type) {

    companion object {
        val INFO = SortInfo("Bubble", SortType.EXCHANGE, AveragePerformance.N2)
    }

    private tailrec fun sortUntilI(values: IntArray, i: Int, j: Int) {
        if (j > i) {
            return
        }

        if (isLesser(values, j, j - 1)) {
            super.swap(values, j - 1, j)
        }

        sortUntilI(values, i, j + 1)
    }

    private tailrec fun sort(values: IntArray, i: Int): IntArray {
        if (i == 0) {
            return values
        }

        sortUntilI(values, i, 1)

        return sort(values, i - 1)
    }

    override fun process(values: IntArray): IntArray = sort(values, values.lastIndex)

}
