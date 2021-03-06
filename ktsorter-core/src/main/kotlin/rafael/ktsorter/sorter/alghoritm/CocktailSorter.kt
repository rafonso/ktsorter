package rafael.ktsorter.sorter.alghoritm

/**
 * https://en.wikipedia.org/wiki/Cocktail_shaker_sort
 * https://gist.github.com/zekeair/4498a1847a8742276107
 */
class CocktailSorter(pauseTime: Long) : Sorter(pauseTime) {

    companion object {
        val INFO = SortInfo("Cocktail", SortType.EXCHANGE, AveragePerformance.N2)
    }

    private tailrec fun moveToLeft(i: Int, left: Int, values: IntArray): Int {
        if(i <= left) {
            return i
        }

        if (super.isLesser(values, i, i - 1)) {
            super.swap(values, i - 1, i)
        }

        return moveToLeft(i - 1, left, values)
    }

    private tailrec fun moveToRight(i: Int, right: Int, values: IntArray): Int {
        if(i >= right) {
            return i
        }

        if (super.isLesser(values, i + 1, i)) {
            super.swap(values, i, i + 1)
        }

        return moveToRight(i + 1, right, values)
    }

    private tailrec fun sort(values: IntArray, left: Int, right: Int): IntArray {
        if(left >= right) {
            return values
        }

        moveToRight(left, right, values)
        moveToLeft(right - 1, left, values)

        return sort(values, left + 1, right - 1)
    }

    override fun process(values: IntArray): IntArray = sort(values, 0, values.lastIndex)

}
