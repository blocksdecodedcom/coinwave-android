package com.blocksdecoded.coinwave.presentation.pickfavorite

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import butterknife.BindView
import butterknife.OnClick
import com.blocksdecoded.core.mvp.BaseMVPFragment
import com.blocksdecoded.coinwave.R
import com.blocksdecoded.coinwave.data.model.CoinEntity
import com.blocksdecoded.coinwave.presentation.coinslist.recycler.CoinsListAdapter
import com.blocksdecoded.coinwave.presentation.coinslist.recycler.CoinsListVH
import com.blocksdecoded.utils.extensions.hide
import com.blocksdecoded.utils.extensions.visible

class PickFavoriteFragment :
        BaseMVPFragment<PickFavoriteContract.Presenter>(),
        PickFavoriteContract.View,
        CoinsListVH.CoinVHListener {
    companion object {
        fun newInstance(): PickFavoriteFragment = PickFavoriteFragment()
    }

    override var mPresenter: PickFavoriteContract.Presenter? = null
    override val layoutId: Int = R.layout.fragment_pick_favorite

    @BindView(R.id.fragment_pick_favorite_recycler)
    @JvmField var mRecycler: RecyclerView? = null
    private var mAdapter: CoinsListAdapter? = null

    @BindView(R.id.fragment_pick_favorite_header)
    lateinit var mHeader: View
    @BindView(R.id.fragment_pick_favorite_error_container)
    lateinit var mErrorContainer: View
    @BindView(R.id.fragment_pick_favorite_progress)
    lateinit var mProgress: View

    @OnClick(
            R.id.back,
            R.id.connection_error_retry
    )
    fun onClick(view: View) {
        when (view.id) {
            R.id.back -> finishView()

            R.id.connection_error_retry -> mPresenter?.onRetryClick()
        }
    }

    //region Lifecycle

    override fun initView(rootView: View) {
        mAdapter = CoinsListAdapter(arrayListOf(), this)

        mRecycler?.setHasFixedSize(true)
        val lm = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mRecycler?.layoutManager = lm
        mRecycler?.adapter = mAdapter
    }

    //endregion

    //region Click

    override fun onClick(position: Int) {
        mPresenter?.onCoinClick(position)
    }

    override fun onPick(position: Int) {
    }

    override fun showError() {
        mHeader.hide()
        mRecycler.hide()
        mErrorContainer.visible()
    }

    override fun hideError() {
        mErrorContainer.hide()
    }

    override fun showLoading() {
        mRecycler.hide()
        mHeader.hide()
        mProgress.visible()
    }

    override fun hideLoading() {
        mProgress.hide()
    }

    //endregion

    //region Contract

    override fun showCoins(coins: List<CoinEntity>) {
        mHeader.visible()
        mRecycler.visible()
        mAdapter?.setItems(coins)
    }

    //endregion
}