package com.example.bitsdashboardassign.viewModels

import androidx.lifecycle.ViewModel
import com.example.bitsdashboardassign.models.GeneralDataItem
import com.example.bitsdashboardassign.models.ProductItem
import com.example.bitsdashboardassign.models.SubCategoryItem

class SubCategoryViewModel : ViewModel() {

    private val _subCatList = mutableListOf(
        GeneralDataItem<SubCategoryItem>(
            0, mutableListOf(
                SubCategoryItem(0, 0, "Veg", true, mutableListOf(
                    ProductItem(0, 0, "Corn Pizza", true, "Try popcorn seeds"),
                    ProductItem(0, 1, "Cheesy Pizza", true, "Melted with extra cheese")
                )),
                SubCategoryItem(0, 1, "Non-Veg", true, mutableListOf(
                    ProductItem(1, 0, "Chicken Pizza", true, "Taste it u love it"),
                    ProductItem(1, 1, "Beef Pizza", true, "Be strong and have some beef")
                ))
            )
        ),
        GeneralDataItem<SubCategoryItem>(
            1, mutableListOf(
                SubCategoryItem(1, 0, "Cakes", true, mutableListOf(
                    ProductItem(0, 0, "Pound Cake", true, "Pounded with chocolate"),
                    ProductItem(0, 1, "Strawberry Cake", true, "All time favourite")
                )),
                SubCategoryItem(1, 1, "Cookies", true, mutableListOf(
                    ProductItem(1, 0, "Sweet cookie", true, "Try this with your guest"),
                    ProductItem(1, 1, "Jam cookie", true, "No one hates the jam")
                )),
                SubCategoryItem(1, 2, "Candies", true, mutableListOf(
                    ProductItem(2, 0, "Melody", true, "Melody itni chocolaty ku hai"),
                    ProductItem(2, 1, "Lollipop", true, "Mangooooouuu")
                )),
                SubCategoryItem(1, 3, "Sweets", true, mutableListOf(
                    ProductItem(3, 0, "Jalebi", true, "More delicious with rabdi"),
                    ProductItem(3, 1, "Rasmalai", true, "How much 1 piece or 2 piece")
                ))

            )
        )
    )

    fun findSubList(catId: Int): MutableList<SubCategoryItem> {
        for (prod in _subCatList) {
            if (prod.id == catId)
                return prod.data
        }   // We can raise error as well
        return mutableListOf()
    }

    private fun findSubCategIndex(list: List<SubCategoryItem>, subId: Int): Int {
        var count = -1
        for (prod in list) {
            count++;
            if (prod.subId == subId)
                return count
        }
        return -1
    }

    // CategoryLinking
    fun addSubFromCategory(catId: Int) {
        _subCatList.add(GeneralDataItem(catId, mutableListOf<SubCategoryItem>()))
    }

    fun deleteSubFromCategory(catId: Int) {
        _subCatList.removeAt(catId)
    }

    // SubCategory
    fun addSubCategory(catId: Int, item: SubCategoryItem): Boolean {
        val list = findSubList(catId)
        list.add(item)
        return true
    }

    fun editSubCategory(catId: Int, subId: Int, item: SubCategoryItem): Boolean {
        val list = findSubList(catId)
        val index = findSubCategIndex(list, subId)
        if (index == -1) return false
        list[index] = item
        return true
    }

    fun deletSubCategory(catId: Int, subId: Int): Boolean {
        val list = findSubList(catId)
        val index = findSubCategIndex(list, subId)
        if (index == -1) return false
        list.removeAt(index)
        return true
    }

    // Product
    private fun findProdIndex(list: List<ProductItem>, prodId: Int): Int {
        var count = -1
        for (prod in list) {
            count++;
            if (prod.prodId == prodId)
                return count
        }
        return -1
    }

    fun getProducts(catId: Int, subId: Int): MutableList<ProductItem> {
        val subList = findSubList(catId = catId)
        for (prod in subList) {
            if (prod.subId == subId) {
                return prod.products
            }
        }
        return mutableListOf<ProductItem>()
    }

    fun addProduct(catId: Int, subId: Int, item: ProductItem) {
        val list = getProducts(catId, subId)
        list.add(item)
    }

    fun editProduct(list: MutableList<ProductItem>, prodId: Int, item: ProductItem): Boolean {
        val index = findProdIndex(list, prodId)
        if (index == -1) return false
        list[index] = item
        return true
    }

    fun deleteProduct(list: MutableList<ProductItem>, prodId: Int): Boolean {
        val index = findProdIndex(list, prodId)
        if (index == -1) return false
        list.removeAt(index)
        return true
    }

    // Profile section
    fun subCatCount(): Int {
        var count = 0;
        for (prod in _subCatList) {
            count += prod.data.size
        }
        return count;
    }

    fun prodCount(): Int {
        var count = 0;
        for (sub in _subCatList) {
            for (prod in sub.data) {
                count += prod.products.size
            }
        }
        return count;
    }
}