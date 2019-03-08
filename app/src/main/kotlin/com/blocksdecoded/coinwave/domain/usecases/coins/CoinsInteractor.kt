package com.blocksdecoded.coinwave.domain.usecases.coins

import com.blocksdecoded.utils.coroutine.model.Result
import com.blocksdecoded.utils.coroutine.model.mapOnSuccess
import com.blocksdecoded.coinwave.data.crypto.ICoinsStorage
import com.blocksdecoded.coinwave.data.crypto.ICoinsObserver
import com.blocksdecoded.coinwave.data.model.CoinEntity
import com.blocksdecoded.utils.coroutine.AppExecutors
import kotlinx.coroutines.withContext

// Created by askar on 7/19/18.
class CoinsInteractor(
    private val mCoinsSource: ICoinsStorage
) : CoinsUseCases {
    override suspend fun getCoins(skipCache: Boolean): Result<List<CoinEntity>> = withContext(AppExecutors.io) {
        mCoinsSource.getAllCoins(skipCache)
                .mapOnSuccess { it.coins }
    }

    override fun getCoin(id: Int): CoinEntity? {
        return mCoinsSource.getCoin(id)
    }

    override fun saveCoin(id: Int): Boolean {
        return mCoinsSource.saveCoin(id)
    }

    override fun removeCoin(id: Int): Boolean {
        return mCoinsSource.removeCoin(id)
    }

    override fun addObserver(observer: ICoinsObserver) {
        mCoinsSource.addCoinObserver(observer)
    }

    override fun removeObserver(observer: ICoinsObserver) {
        mCoinsSource.removeCoinObserver(observer)
    }
}