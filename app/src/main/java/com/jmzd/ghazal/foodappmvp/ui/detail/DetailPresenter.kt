package com.jmzd.ghazal.foodappmvp.ui.detail

import com.jmzd.ghazal.foodappmvp.data.repository.DetailRepository
import com.jmzd.ghazal.foodappmvp.utils.applyIoScheduler
import com.jmzd.ghazal.foodappmvp.utils.base.BasePresenterImpl
import javax.inject.Inject


class DetailPresenter @Inject constructor(
    private val repository: DetailRepository,
    val view: DetailContracts.View) :
    BasePresenterImpl(), DetailContracts.Presenter {

    override fun callDetailApi(id: Int) {
        if (view.checkInternet()) {
            view.showLoading()
            disposable = repository.foodDetail(foodId = id)
                .applyIoScheduler()
                .subscribe({ response ->
                    view.hideLoading()
                    when (response.code()) {
                        in 200..202 -> {
                            response.body()?.let { itBody ->
                                if (itBody.meals != null) {
                                    if (itBody.meals.isNotEmpty()) {
                                        view.loadDetail(itBody)
                                    }
                                }
                            }
                        }
                    }

                }, {
                    view.hideLoading()
                    view.serverError(it.message.toString())
                })
        } else {
            view.internetError(false)
        }
    }

}