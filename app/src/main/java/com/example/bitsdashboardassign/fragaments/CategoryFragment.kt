package com.example.bitsdashboardassign.fragaments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitsdashboardassign.R
import com.example.bitsdashboardassign.Utils
import com.example.bitsdashboardassign.adapters.CategoryAdapter
import com.example.bitsdashboardassign.databinding.FragmentCategoryBinding
import com.example.bitsdashboardassign.models.CategoryItem
import com.example.bitsdashboardassign.viewModels.CategoryViewModel
import com.example.bitsdashboardassign.viewModels.SubCategoryViewModel


class CategoryFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var prodList: List<CategoryItem>
    private val viewModel: CategoryViewModel by activityViewModels()
    private val subViewModel: SubCategoryViewModel by activityViewModels()

    lateinit var mContext: Context
    lateinit var mActivity: FragmentActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mContext = requireContext()
        mActivity = requireActivity()
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Utils.bottomNavBarVisibility(mActivity, View.VISIBLE)
        prodList = viewModel.prodList
        categoryAdapter = CategoryAdapter(mContext, {
            navigateToSubCate(it)
        }) { catId, listItem, action ->
            prodModify(catId, listItem, action)
        }
        categoryAdapter.submitList(prodList)
        Log.d("Rishabh before", "${prodList}")

        binding.catRecyView.apply {
            adapter = categoryAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        binding.addFloatBtn.setOnClickListener {
            Utils.showAddItemDialog(mContext, true, getString(R.string.add_cat), getString(R.string.empty), getString(R.string.empty), {
                Toast.makeText(mContext, it, Toast.LENGTH_SHORT).show()
            }) { name, desc ->
                subViewModel.addSubFromCategory(catId = prodList.size)
                viewModel.addProduct(
                    CategoryItem(
                        name = name,
                        id = prodList.size,
                        enable = true,
                        description = desc
                    )
                )

                Log.d("Rishabh", "${viewModel.prodList.size}")
                Log.d("Rishabh after", "${viewModel.prodList}")
                categoryAdapter.notifyDataSetChanged()
            }
        }

    }

    private fun navigateToSubCate(catId: Int) {
        val direction =
            CategoryFragmentDirections.actionCategoryFragmentToSubCategoryFragment(catId)
        findNavController().navigate(direction)
    }

    private fun prodModify(catId: Int, listItem: CategoryItem ,action: Int) {
        // Edit
        if (action == 1) {
            Utils.showAddItemDialog(mContext, true, getString(R.string.edit_item), listItem.name, listItem.description, {
                Toast.makeText(mContext, it, Toast.LENGTH_SHORT).show()
            }) { name, desc ->
                viewModel.editProduct(catId,
                    CategoryItem(
                        name = name,
                        id = catId,
                        enable = listItem.enable,
                        description = desc
                    )
                )
                Log.d("Rishabh", "${viewModel.prodList.size}")
                Log.d("Rishabh after", "${viewModel.prodList}")
                categoryAdapter.notifyDataSetChanged()
            }
        } else {  // Delete
            viewModel.deleteProduct(catId)
            subViewModel.deleteSubFromCategory(catId)
            categoryAdapter.notifyDataSetChanged()
        }
    }

}