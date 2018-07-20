package com.makeuseof.cryptocurrency.domain

import android.content.Context
import com.makeuseof.cryptocurrency.domain.usecases.list.CurrencyListInteractor
import com.makeuseof.cryptocurrency.domain.usecases.list.CurrencyListUseCases
import com.makeuseof.utils.coroutine.AppExecutors

// Created by askar on 7/19/18.
object UseCaseProvider {
    fun getCurrencyListUseCases(context: Context): CurrencyListUseCases{
        return CurrencyListInteractor(
                AppExecutors.getInstance(),
                ServiceProvider.getCurrencyService(context)
        )
    }
}