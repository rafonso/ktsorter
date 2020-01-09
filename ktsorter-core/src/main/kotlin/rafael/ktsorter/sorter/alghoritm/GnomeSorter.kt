package rafael.ktsorter.sorter.alghoritm

class GnomeSorter(pauseTime: Long) : Sorter(pauseTime, TYPE) {

    companion object {
        val TYPE = SortType.EXCHANGE
    }

    private tailrec fun sort(values: IntArray, limit: Int, i: Int) {
        if (i == 0 || !super.isLesser(values, i, i - 1)) {
            return
        }

        super.swap(values, i, i - 1)

        sort(values, limit, i - 1)
    }

    override fun process(values: IntArray): IntArray {
        values.indices.forEach { this.sort(values, it, it) }

        return values
    }

}
