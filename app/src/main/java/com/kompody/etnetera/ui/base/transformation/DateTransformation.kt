package com.kompody.etnetera.ui.base.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class DateTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return dateFilter(text)
    }

    private fun dateFilter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 7) text.text.substring(0..6) else text.text
        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i % 2 == 1 && i < 4) out += ":"
        }

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 1) return offset
                if (offset <= 3) return offset + 1
                if (offset <= 7) return offset + 2
                return 9
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 5) return offset - 1
                if (offset <= 9) return offset - 2
                return 7
            }
        }

        return TransformedText(AnnotatedString(out), numberOffsetTranslator)
    }
}