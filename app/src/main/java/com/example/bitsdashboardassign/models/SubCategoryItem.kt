package com.example.bitsdashboardassign.models

data class SubCategoryItem (
    val catId: Int,
    val subId: Int,
    val name: String,
    var enable: Boolean,
    val products: MutableList<ProductItem>
)

data class ProductItem(
    val subId: Int,
    val prodId: Int,
    val name: String,
    var enable: Boolean,
    val description: String
)
