package rafael.ktsorter.numbergenerator

import org.junit.jupiter.api.RepeatedTest
import kotlin.test.assertEquals

private const val TOTAL_REPETITIONS = 10

class NumberGeneratorTest {

    private val size = 100

    @RepeatedTest(value = TOTAL_REPETITIONS, name = RepeatedTest.SHORT_DISPLAY_NAME)
    fun `Random Generator of size n must generate a random sequence of n integer from 1 to n`() {
        val values = NumberGenerator.RANDOM.generate(size)
        val setValues = values.toSortedSet()

        assertEquals(
            size, setValues.size, "Random Generator should have generated $size different values from 1 to $size, " +
                    "but there are ${setValues.size} values: ${values.toList()}"
        )
        values.forEachIndexed { index, value ->
            assert(value in 1..size) {
                "Random Generator should at position $index should be between 1 and $size, " +
                        "but it was $value: ${values.toList()}"
            }
        }
    }

    @RepeatedTest(value = TOTAL_REPETITIONS, name = RepeatedTest.SHORT_DISPLAY_NAME)
    fun `Crescent Generator of size n must generate a crescent sequence of n integer from 1 to n`() {
        val values = NumberGenerator.CRESCENT.generate(size)
        val setValues = values.toSortedSet()

        assertEquals(
            size, setValues.size, "Random Generator should have generated $size different values from 1 to $size, " +
                    "but there are ${setValues.size} values: ${values.toList()}"
        )
        assertEquals(
            1,
            values[0],
            "Crescent sequence at position 0 should be 1, but it was ${values[0]}: ${values.toList()}"
        )
        assertEquals(
            size,
            values.last(),
            "Crescent sequence at position ${values.lastIndex} should be $size, " +
                    "but it was ${values.last()}: ${values.toList()}"
        )
        (1 until values.size).forEach { pos ->
            assert(values[pos] - values[pos - 1] == 1) {
                "In Crescent sequence at positions ${pos - 1} and $pos should have a difference of 1, " +
                        "but it was ${values[pos] - values[pos - 1]}: ${values.toList()}"
            }
        }

    }

    @RepeatedTest(value = TOTAL_REPETITIONS, name = RepeatedTest.SHORT_DISPLAY_NAME)
    fun `Decrescent Generator of size n must generate a decrescent sequence of n integers from n to 1`() {
        val values = NumberGenerator.DECRESCENT.generate(size)
        val setValues = values.toSortedSet()

        assertEquals(
            size, setValues.size, "Random Generator should have generated $size different values from 1 to $size, " +
                    "but there are ${setValues.size} values: ${values.toList()}"
        )
        assertEquals(
            size,
            values.first(),
            "Decrescent sequence at position 0 should be $size, but it was ${values.first()}: ${values.toList()}"
        )
        assertEquals(
            1,
            values.last(),
            "Decrescent sequence at position ${values.lastIndex} should be 1, " +
                    "but it was ${values.last()}: ${values.toList()}"
        )
        (1 until values.size).forEach { pos ->
            assert(values[pos] - values[pos - 1] == - 1) {
                "In Crescent sequence at positions $pos and ${pos - 1} should have a difference of 1, " +
                        "but it was ${values[pos] - values[pos - 1]}: ${values.toList()}"
            }
        }

    }

    @RepeatedTest(value = TOTAL_REPETITIONS, name = RepeatedTest.SHORT_DISPLAY_NAME)
    fun `Single Generator of size n must generate a sequence of a unique ramdom integer from 1 to n`() {
        val values = NumberGenerator.SINGLE.generate(size)
        val setValues = values.toSortedSet()

        assertEquals(
            1,
            setValues.size,
            "Single Generator should have generated a sequence of a unique random integer from 1 to $size, " +
                    "but there are ${setValues.size} values: ${values.toList()}"
        )
        assert(values.first() in 1..size) {
            "Single Generator should have generated a sequence of a unique random integer from 1 to $size, " +
                    "but this value was ${values.first()}"
        }
    }

    @RepeatedTest(value = TOTAL_REPETITIONS, name = RepeatedTest.SHORT_DISPLAY_NAME)
    fun `Sequence of 4 Generator of size n must generate a sequence of 4 random integers from 1 to n`() {
        val values = NumberGenerator.SEQUENCE_OF_4.generate(size)
        val setValues = values.toSortedSet()

        assertEquals(
            size,
            values.size,
            "Sequence of 4 Generator of size $size should have this size, but it was ${values.size}: ${values.toList()}"
        )
        assertEquals(
            4,
            setValues.size,
            "Sequence of 4 Generator of size $size should have only 4 different values, " +
                    "but they were $setValues: ${values.toList()}"
        )
        setValues.forEachIndexed { index, value ->
            assert(value in 1..size) {
                "Sequence of 4 Generator should at position $index a value be between 1 and $size, " +
                        "but it was $value: ${values.toList()}"
            }
        }
    }

    @RepeatedTest(value = TOTAL_REPETITIONS, name = RepeatedTest.SHORT_DISPLAY_NAME)
    fun `Semi-sorted Sequence of size n must generate a semi crescent sequence random integers from 1 to n`() {
        val values = NumberGenerator.SEMI_SORTED.generate(size)
        val delta = (2 * randomFactor * size).toInt()

        assertEquals(
            size,
            values.size,
            "Semi-sorted Sequence of size $size should have this size, but it was ${values.size}: ${values.toList()}"
        )
        (delta until values.size).forEach { pos ->
            assert(values[pos - delta] <= values[pos]) {
                "At Semi-sorted Sequence of size $size value at position ${pos - delta} should be lesser than value at position $pos, " +
                        "but they were ${values[pos - delta]} and ${values[pos]}: ${values.toList()}"
            }
        }
    }
}
