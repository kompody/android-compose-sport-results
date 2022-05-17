package com.kompody.etnetera.domain.entity

data class ResultModel(
    val name: String,
    val place: Int,
    val duration: Float,
    val date: Long,
    val type: Type
) {
    enum class Type {
        Remote, Locale
    }
}
