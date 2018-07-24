package com.makeuseof.cryptocurrency.view.list

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.makeuseof.core.mvp.BaseMVPFragment
import com.makeuseof.cryptocurrency.R
import com.makeuseof.cryptocurrency.data.model.CurrencyEntity
import com.makeuseof.cryptocurrency.view.list.recycler.CurrencyListAdapter
import com.makeuseof.cryptocurrency.view.list.recycler.CurrencyListViewHolder
import com.makeuseof.utils.hide
import com.makeuseof.utils.inflate
import com.makeuseof.utils.showShortToast
import com.makeuseof.utils.visible

class CurrencyListFragment :
        BaseMVPFragment<CurrencyListContract.Presenter>(),
        CurrencyListContract.View,
        CurrencyListViewHolder.CurrencyVHClickListener {
    override var mPresenter: CurrencyListContract.Presenter? = null

    private var mRecycler: RecyclerView? = null
    private var mAdapter: CurrencyListAdapter? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    private var mErrorContainer: View? = null
    private var mRetry: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = container.inflate(R.layout.fragment_currency_list)

        initView(rootView)

        return rootView
    }

    private fun initView(rootView: View?) {
        mAdapter = CurrencyListAdapter(arrayListOf(), this)
        mRecycler = rootView?.findViewById(R.id.fragment_currency_list_recycler)
        mSwipeRefreshLayout = rootView?.findViewById(R.id.fragment_currency_list_refresh)

        mErrorContainer = rootView?.findViewById(R.id.fragment_currency_list_error)
        mRetry = rootView?.findViewById(R.id.connection_error_retry)

        mRetry?.setOnClickListener {
            mPresenter?.getCurrencyList()
        }

        mSwipeRefreshLayout?.setOnRefreshListener {
            mPresenter?.getCurrencyList()
        }

        val lm = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mRecycler?.layoutManager = lm
        mRecycler?.adapter = mAdapter
    }

    //region ViewHolder

    override fun onPick(position: Int) {
        mPresenter?.onCurrencyPick(position)
    }

    override fun onClick(position: Int) {
        mPresenter?.onCurrencyClick(position)
    }

    //endregion

    //region Contract

    override fun updateCurrency(position: Int, currencyEntity: CurrencyEntity) {
        mAdapter?.updateItem(currencyEntity)
    }

    override fun deleteCurrency(position: Int) {
        mAdapter?.deleteItem(position)
    }

    override fun showCurrencies(currencies: List<CurrencyEntity>) {
        mSwipeRefreshLayout?.isRefreshing = false
        mRecycler.visible()
        mErrorContainer.hide()
        mRecycler?.post {
            mAdapter?.setItems(currencies)
        }
    }

    override fun showNetworkError(hideList: Boolean) {
        mSwipeRefreshLayout?.isRefreshing = false
        if(hideList){
            mRecycler.hide()
            mErrorContainer.visible()
        } else {
            showShortToast(context, "Couldn't refresh currencies.\nPlease check internet connection and try again.")
            mRecycler.visible()
            mErrorContainer.hide()
        }
    }

    override fun showLoading() {
        mSwipeRefreshLayout?.isRefreshing = true
        mErrorContainer.hide()
    }

    //endregion
}