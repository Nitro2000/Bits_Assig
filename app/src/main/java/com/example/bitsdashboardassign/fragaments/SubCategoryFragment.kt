package com.example.bitsdashboardassign.fragaments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitsdashboardassign.R
import com.example.bitsdashboardassign.Utils
import com.example.bitsdashboardassign.adapters.SubCategoryAdapter
import com.example.bitsdashboardassign.databinding.FragmentSubCategoryBinding
import com.example.bitsdashboardassign.models.SubCategoryItem
import com.example.bitsdashboardassign.viewModels.SubCategoryViewModel

class SubCategoryFragment : Fragment() {

    lateinit var binding: FragmentSubCategoryBinding
    lateinit var subProdList: List<SubCategoryItem>
    lateinit var subAdapter: SubCategoryAdapter
    lateinit var mContext: Context
    val viewModel: SubCategoryViewModel by activityViewModels()
    val args: SubCategoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mContext = requireContext()
        binding = FragmentSubCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val catId = args.catId
        Utils.bottomNavBarVisibility(requireActivity(), View.GONE)
        subProdList = viewModel.findSubList(catId)
        subAdapter = SubCategoryAdapter(mContext, {
            navigateToProductsPage(catId, it)
        }) {subId, item: SubCategoryItem, action ->
            prodModify(catId, subId, item, action)
        }
        subAdapter.submitList(subProdList)

        // Recycler view setting
        binding.subRecyView.apply {
            adapter = subAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        }

        // Adding with floating button
        binding.addFloatBtn.setOnClickListener {
            Utils.showAddItemDialog(mContext, true, getString(R.string.add_sub_cat), getString(R.string.empty), getString(R.string.empty), {
                Toast.makeText(mContext, it, Toast.LENGTH_SHORT).show()
            }) { name, desc ->
                viewModel.addSubCategory(catId = catId,
                    SubCategoryItem(
                        catId = catId,
                        subId = subProdList.size,
                        name = name,
                        enable = true,
                        products = mutableListOf()
                    )
                )
                subAdapter.notifyDataSetChanged()
            }
        }


        binding.backArrowImg.setOnClickListener {findNavController().popBackStack()}
    }

    private fun navigateToProductsPage(catId: Int, it: Int) {
        val direction = SubCategoryFragmentDirections.actionSubCategoryFragmentToProductPage(
            subId = it, catId = catId)
        findNavController().navigate(direction)
    }

    private fun prodModify(catId: Int, subId: Int, item: SubCategoryItem, action: Int) {
        // Edit
        if (action == 1) {
            Utils.showAddItemDialog(mContext, false, getString(R.string.edit_item), item.name, getString(R.string.empty), {
                Toast.makeText(mContext, it, Toast.LENGTH_SHORT).show()
            }) { name, desc ->
                viewModel.editSubCategory(catId = catId, subId = subId,
                    SubCategoryItem(
                        catId = catId,
                        subId = subId,
                        name = name,
                        enable = item.enable,
                        products = item.products
                    )
                )
//                Log.d("Rishabh", "${subProdList.size}")
//                Log.d("Rishabh after", "${subProdList}")
                subAdapter.notifyDataSetChanged()
            }
        } else {  // Delete
            viewModel.deletSubCategory(catId, subId)
            subAdapter.notifyDataSetChanged()
        }
    }

}