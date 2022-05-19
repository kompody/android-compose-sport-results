package com.kompody.etnetera.domain.entity

data class ResultModel(
    val id: Long = 0,
    val sportId: Int,
    val sportName: String,
    val place: Int,
    val duration: Int,
    val date: Long,
    val type: Type
) {
    enum class Type {
        Remote, Locale
    }
}
