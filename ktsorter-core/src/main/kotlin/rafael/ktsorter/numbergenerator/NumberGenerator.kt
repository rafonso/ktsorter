package rafael.ktsorter.numbergenerator

import kotlin.random.Random

private const val randomFactor = 0.05

/**
 * Creates the initial values to be sorted.
 */
enum class NumberGenerator(val description: String) {

    /**
     * Creates a sequence of random numbers from 1 to n.
     */
    RANDOM("Random") {
        override fun generate(n: Int): IntArray = (1 until n + 1).shuffled().toIntArray()
    },
    /**
     * Creates a sequence of crescent numbers from 1 to n.
     */
    CRESCENT("Crescent") {
        override fun generate(n: Int): IntArray = (1 until n + 1).toList().toIntArray()
    },
    /**
     * Creates a sequence of decrescent numbers from n to 1.
     */
    DECRESCENT("Decrescent") {
        override fun generate(n: Int): IntArray = (1 until n + 1).toList().reversed().toIntArray()
    },
    /**
     * Creates a sequence of a single random number between 1 and n.
     */
    SINGLE("Single") {
        override fun generate(n: Int): IntArray {
            val value = Random.nextInt(n) + 1
            return List(n) { _ -> value }.toIntArray()
        }
    },
    /**
     * Creates a sequence of 4 random different numbers between 1 and n.
     */
    SEQUENCE_OF_4("Sequence of 4") {

        private fun getValue(delta: Int, pos: Int): List<Int> {
            val value = (delta * (pos + Random.nextDouble())).toInt()
            return List(delta) { _ -> value }
        }

        private tailrec fun normalize(values: MutableList<List<Int>>, quantity: Int, i: Int): List<Int> {
            val totalSize = values.map { l -> l.size }.sum()
            if (totalSize == quantity || i == 4) {
                return values.flatten()
            }

            values[i] = values[i] + values[i][0]
            return normalize(values, quantity, i + 1)
        }

        override fun generate(n: Int): IntArray {
            val delta = n / 4

            val values0 = getValue(delta, 0)
            val values1 = getValue(delta, 1)
            val values2 = getValue(delta, 2)
            val values3 = getValue(delta, 3)

            return normalize(mutableListOf(values0, values1, values2, values3), n, 0).shuffled().toIntArray()
        }
    },
    SEMI_SORTED("Semi Sorted") {

        private fun generateValue(pos: Int, max: Int): Int {
            val signal = if (Random.nextBoolean()) +1 else -1
            val factor = signal * randomFactor * max * Random.nextDouble()
            val value = (pos + factor).toInt()
            return when {
                value < 1 -> 1
                value > max -> max
                else -> value
            }
        }

        override fun generate(n: Int): IntArray =
            (0 until n).map { generateValue(it + 1, n) }.toIntArray()

    }
    ;

    /**
     * Creates an [IntArray] of size n to be used as initial values.
     *
     * @param n Size of Array
     * @return A sequence of n integers.
     */
    abstract fun generate(n: Int): IntArray

}

fun main() {
    print("Enter Generator Type and quantity: ")
    val (strType, strQuantity) = readLine()!!.split(' ')
    val generator = NumberGenerator.values()[strType.toInt()]
    val quantity = strQuantity.toInt()

    val values = generator.generate(quantity)
    println(values.joinToString(separator = " ") { i -> "%3d".format(i) })
}
