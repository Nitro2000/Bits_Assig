package com.example.bitsdashboardassign.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitsdashboardassign.models.CategoryItem

class CategoryViewModel : ViewModel() {

    private val _prodList = mutableListOf(
        CategoryItem("Snacks", 0, true, "Have Some Pizza"),
        CategoryItem("Deserts", 1, true, "Have Some Pizza")
    )

    val prodList: List<CategoryItem>
        get() = _prodList

    fun getProducts(): List<CategoryItem> {
        return prodList
    }

    private fun findProduct(id: Int): Int {
        var count = -1;
        for (prod in _prodList) {
            count++;
            if (prod.id == id)
                return count
        }
        return -1    // We can raise error as well
    }

    fun addProduct(categoryItem: CategoryItem) {
        _prodList.add(categoryItem)
    }

    fun editProduct(id: Int, categoryItem: CategoryItem) {
        _prodList[id] = categoryItem
    }

    fun deleteProduct(index: Int) {
        _prodList.removeAt(index)
    }
}