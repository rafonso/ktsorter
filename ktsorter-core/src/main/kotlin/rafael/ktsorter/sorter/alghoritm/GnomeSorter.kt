package rafael.ktsorter.sorter.alghoritm

class GnomeSorter(pauseTime: Long) : Sorter(pauseTime) {

    private fun sort(values: IntArray, limit: Int) {
        for(i in limit downTo 1) {
            if(!super.isLesser(values, i, i - 1)) {
                break
            }
            super.swap(values, i, i - 1)
        }
    }

    override fun process(values: IntArray): IntArray {
        for(pos in 1 until values.size) {
            this.sort(values, pos)
        }

        return values
    }

}
