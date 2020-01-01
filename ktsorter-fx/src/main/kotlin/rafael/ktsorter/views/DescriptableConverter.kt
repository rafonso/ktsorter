package rafael.ktsorter.views

import javafx.util.StringConverter
import rafael.ktsorter.util.Descriptable

class DescriptableConverter<D : Descriptable>(private val values: Iterable<D>) : StringConverter<D>() {

    constructor(values: Array<D>): this(values.toList())

    override fun toString(d: D?): String? = d?.description

    override fun fromString(s: String?): D? = if (s != null) values.find { it.description == s } else null
}
