package rafael.ktsorter.views

import javafx.util.StringConverter
import rafael.ktsorter.views.plot.Limits
import rafael.ktsorter.views.plot.limitsValues

class LimitsConverter: StringConverter<Limits>() {
    override fun toString(l: Limits?): String = l!!.quantity.toString()

    override fun fromString(s: String?): Limits = limitsValues.find { it.quantity.toString() == s }!!
}
