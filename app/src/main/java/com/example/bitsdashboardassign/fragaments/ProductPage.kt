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
import com.example.bitsdashboardassign.adapters.ProductAdapter
import com.example.bitsdashboardassign.adapters.SubCategoryAdapter
import com.example.bitsdashboardassign.databinding.FragmentProductPageBinding
import com.example.bitsdashboardassign.models.ProductItem
import com.example.bitsdashboardassign.models.SubCategoryItem
import com.example.bitsdashboardassign.viewModels.SubCategoryViewModel

class ProductPage : Fragment() {

    lateinit var binding: FragmentProductPageBinding
    lateinit var prodAdapter: ProductAdapter
    lateinit var prodList: MutableList<ProductItem>
    lateinit var mContext: Context
    val viewModel: SubCategoryViewModel by activityViewModels()
    val args: ProductPageArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mContext = requireContext()
        binding = FragmentProductPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val catId = args.catId
        val subId = args.subId
        Utils.bottomNavBarVisibility(requireActivity(), View.GONE)
        prodList = viewModel.getProducts(catId, subId)
        prodAdapter = ProductAdapter(mContext) {prodId, item, action ->
            prodModify(prodList, prodId, item, action)
        }
        prodAdapter.submitList(prodList)

        binding.prodRecyView.apply {
            adapter = prodAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        }

        binding.addFloatBtn.setOnClickListener {
            Utils.showAddItemDialog(mContext, true, getString(R.string.add_prod), getString(R.string.empty), getString(R.string.empty), {
                Toast.makeText(mContext, it, Toast.LENGTH_SHORT).show()
            }) { name, desc ->
                viewModel.addProduct(catId = catId, subId = subId,
                    ProductItem(
                        subId = subId,
                        prodId = prodList.size,
                        name = name,
                        enable = true,
                        description = desc
                    )
                )
                prodAdapter.notifyDataSetChanged()
            }
        }

        binding.backArrowImg.setOnClickListener{findNavController().popBackStack()}

    }


    private fun prodModify(list: MutableList<ProductItem>, prodId: Int, item: ProductItem, action: Int) {
        // Edit
        if (action == 1) {
            Utils.showAddItemDialog(mContext, true, getString(R.string.add_prod), item.name, item.description, {
                Toast.makeText(mContext, it, Toast.LENGTH_SHORT).show()
            }) { name, desc ->
                viewModel.editProduct(list, prodId,
                    ProductItem(
                        subId = item.subId,
                        prodId = prodId,
                        name = name,
                        enable = item.enable,
                        description = desc
                    )
                )
                prodAdapter.notifyDataSetChanged()
            }
        } else {  // Delete
            viewModel.deleteProduct(list, prodId)
            prodAdapter.notifyDataSetChanged()
        }
    }
}