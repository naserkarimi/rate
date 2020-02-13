package com.revolut.rates.ui.rate

import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import android.view.View
import com.revolut.rates.R
import com.revolut.rates.base.BaseViewModel
import com.revolut.rates.model.Model
import com.revolut.rates.network.RateApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RateListViewModel : BaseViewModel() {
    @Inject
    lateinit var rateApi: RateApi

    private lateinit var subscription: Disposable
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadRates() }
    val rateListAdapter: RateListAdapter = RateListAdapter()

    init {
        loadRates()
    }

    private fun loadRates() {
        subscription = rateApi.getRates(Model.currency)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveRateListStart() }
            .doOnTerminate { onRetrieveRateeListFinish() }
            .subscribe(
                { result -> onRetrieveRateListSuccess(result.rates) },
                { onRetrieveRateListError() }
            )
    }

    private fun onRetrieveRateListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveRateeListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveRateListSuccess(rateList: Map<String, Double>) {
        rateListAdapter.updateRateList(rateList)
        Handler().postDelayed({
            loadRates()
        }, 1000)
    }

    private fun onRetrieveRateListError() {
        errorMessage.value = R.string.error
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}