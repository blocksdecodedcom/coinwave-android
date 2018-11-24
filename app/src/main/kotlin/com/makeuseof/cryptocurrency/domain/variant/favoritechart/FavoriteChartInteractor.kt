package com.makeuseof.cryptocurrency.domain.variant.favoritechart

import com.makeuseof.cryptocurrency.data.model.ChartData
import com.makeuseof.cryptocurrency.domain.usecases.chart.ChartsUseCases
import com.makeuseof.cryptocurrency.domain.usecases.favorite.FavoriteUseCases
import com.makeuseof.utils.coroutine.AppExecutors
import com.makeuseof.utils.coroutine.model.Result

/**
 * Created by askar on 11/24/18
 * with Android Studio
 */
class FavoriteChartInteractor(
        private val appExecutors: AppExecutors,
        private val mChartsUseCases: ChartsUseCases,
        private val mFavoriteUseCases: FavoriteUseCases
): FavoriteChartUseVariant {

    companion object {
        private val CHART_PERIOD = ChartsUseCases.ChartPeriod.TODAY
    }

    override suspend fun getChart(): Result<ChartData>? {
        return mChartsUseCases.getChartData(mFavoriteUseCases.getId(), CHART_PERIOD)
    }
}