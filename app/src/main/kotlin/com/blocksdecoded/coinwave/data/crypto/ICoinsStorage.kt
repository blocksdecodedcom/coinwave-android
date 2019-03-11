package com.blocksdecoded.coinwave.data.crypto

import com.blocksdecoded.coinwave.data.model.CoinsDataResponse
import com.blocksdecoded.coinwave.data.model.CoinEntity
import io.reactivex.Flowable

// Created by askar on 7/19/18.
interface ICoinsStorage {
    fun getAllCoins(skipCache: Boolean): Flowable<CoinsDataResponse>

    fun getWatchlist(skipCache: Boolean): Flowable<CoinsDataResponse>

    fun setCoinsData(coinsData: CoinsDataResponse)

    fun getCoin(id: Int): CoinEntity?

    fun saveCoin(id: Int): Boolean

    fun removeCoin(id: Int): Boolean

    fun addCoinObserver(observer: ICoinsObserver)

    fun removeCoinObserver(observer: ICoinsObserver)
}