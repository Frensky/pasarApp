package com.adut.pasar.app.feature.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.adut.pasar.app.R
import com.adut.pasar.app.base.BaseFragment
import com.adut.pasar.app.feature.DashboardViewModel
import com.adut.pasar.app.feature.edit.EditActivity
import com.adut.pasar.domain.model.Item
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.FloatingSearchView.OnQueryChangeListener
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.demo.img.adapter.ItemAdapter
import kotlinx.android.synthetic.main.product_layout.*


open class ProductFragment : BaseFragment() {
    lateinit var dashboardViewModel: DashboardViewModel
    lateinit var viewModel: ProductViewModel
    lateinit var adapter: ItemAdapter
    val productData : ArrayList<Item>

    init {
        productData = ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        component.inject(this)
        dashboardViewModel = ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProductViewModel::class.java)
        return inflater.inflate(getLayout(), container, false)
    }

    override fun getLayout():Int{
        return R.layout.product_layout
    }

    override fun initView(){
        adapter = ItemAdapter(requireContext(),productData)
        adapter.setPriceType(ItemAdapter.PriceState.BELI)

        productListView?.setOverScrollMode(View.OVER_SCROLL_NEVER)
        val mLayoutMgr =  GridLayoutManager(requireContext(), 1)
        productListView?.setLayoutManager(mLayoutMgr)
        productListView?.setAdapter(adapter)

        refreshData()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun setUICallbacks(){
        adapter.onClickItem(object : ItemAdapter.OnItemCLickListener{
            override fun onClickItem(data: Item, position: Int) {
               EditActivity.launchActivity(requireActivity(),data.id)
            }
         }
        )

        productSearchView?.setOnQueryChangeListener(OnQueryChangeListener { oldQuery, newQuery ->
            viewModel.onSearchTitle(newQuery)
        })


        productSearchView?.setDismissFocusOnItemSelection(true)
        productSearchView?.setOnSearchListener(object : FloatingSearchView.OnSearchListener{
            override fun onSearchAction(currentQuery: String?) {
                viewModel.onSearchProduct(currentQuery)
                productSearchView?.clearSearchFocus()
            }

            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {
                //productSearchView?.setSearchBarTitle(searchSuggestion?.body)
                viewModel.onSearchProduct(searchSuggestion?.body)
                productSearchView?.clearSearchFocus()
            }
        })

        product_segment?.onSegmentChecked { segmented ->
            if(segmented.text.equals("Nilai Jual")){
                adapter.setPriceType(ItemAdapter.PriceState.JUAL)
            }
            else{
                adapter.setPriceType(ItemAdapter.PriceState.BELI)
            }
        }

        product_tambah_btn?.setOnClickListener {
            EditActivity.launchAddActivity(requireActivity(),productSearchView?.query.toString())
        }
    }

    open fun observeDashboardViewModel(){
        dashboardViewModel.reloadProductView.observe(this, Observer {
            it?.let {
                refreshData()
                productSearchView?.clearQuery()
                viewModel.onSearchProduct("")
            }
        })

        dashboardViewModel.updateProductView.observe(this, Observer {
            it?.let {
                refreshData()
                viewModel.onSearchProduct(productSearchView?.query.toString())
            }
        })

        dashboardViewModel.searchProductByBarCode.observe(this, Observer {
            it?.let {
                viewModel.onSearchByBarCode(it)
            }
        })
    }


    override fun initObserver(){

        observeDashboardViewModel()

        viewModel.productLiveData.observe(this, Observer {
            if(it != null){
                productData.clear()
                productData.addAll(it)
                adapter.notifyDataSetChanged()

                if(it.isNullOrEmpty()){
                    product_empty_layout?.visibility = View.VISIBLE
                }
                else{
                    product_empty_layout?.visibility = View.GONE
                }
            }
        })

        viewModel.titleLiveData.observe(this, Observer {
            if(it != null){
                val resultList = ArrayList<ProductSuggestion>()
                for(item in it){
                    resultList.add(ProductSuggestion(item))
                }
                productSearchView.swapSuggestions(resultList)
            }
        })

        viewModel.segmentUILiveData.observe(this, Observer {
            if(it != null){
                if(it){
                    product_segment?.visibility = View.VISIBLE
                    product_segment.initialCheckedIndex = 0
                    product_segment.invoke {

                    }
                }
                else{
                    product_segment?.visibility = View.GONE
                }
            }
        })

        viewModel.barCodeData.observe(this, Observer {
            if(it != null){
                productSearchView?.clearQuery()
                productSearchView?.setSearchText(it.title)
                viewModel.onSearchProduct(it.title)
            }
            else{
                Toast.makeText(requireContext(),"Kode Barcode belum terdaftar",Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun updateUI(){
        viewModel.onSearchProduct(productSearchView?.query.toString())
    }

    protected fun refreshData(){
        viewModel.refreshAllData()
        viewModel.checkSegmentUi()
    }

    companion object {
        fun newInstance(): ProductFragment {
            val fragments = ProductFragment()
            return fragments
        }
    }

}