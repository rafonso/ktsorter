package rafael.ktsorter.sorter.alghoritm

abstract class QuickSorterBase(pauseTime: Long, type: SortType) : Sorter(pauseTime, type) {

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

    protected open fun sort(values: IntArray, left: Int, right: Int): IntArray {
        if (left < right) {
            val partitionIndex = this.partition(values, right, left, right)

            this.sort(values, left, partitionIndex - 1)
            this.sort(values, partitionIndex + 1, right)
        }

        return values
    }

    override fun process(values: IntArray): IntArray = sort(values, 0, values.lastIndex)

}
