package com.example.bitsdashboardassign.models

data class GeneralDataItem<T>(
    val id: Int,
    val data: MutableList<T>
)
