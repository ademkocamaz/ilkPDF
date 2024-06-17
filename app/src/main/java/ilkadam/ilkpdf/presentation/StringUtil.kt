package ilkadam.ilkpdf.presentation

import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

object StringUtil {

    fun readableFileSize(size: Int): String {
        if (size <= 0) {
            return "0"
        }

        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()

        return DecimalFormat("#,##0.#").format(
            size / 1024.0.pow(digitGroups.toDouble())
        ) + " " + units[digitGroups]
    }
}