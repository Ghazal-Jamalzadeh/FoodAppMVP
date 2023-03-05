package com.jmzd.ghazal.foodappmvp.ui.home

import com.jmzd.ghazal.foodappmvp.data.repository.HomeRepository
import com.jmzd.ghazal.foodappmvp.utils.applyIoScheduler
import com.jmzd.ghazal.foodappmvp.utils.base.BasePresenterImpl
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val repository: HomeRepository,
    val view: HomeContracts.View,
) : BasePresenterImpl(), HomeContracts.Presenter {

    override fun callFoodRandom() {

        if (view.checkInternet()) {

            disposable = repository.loadFoodRandom()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .applyIoScheduler()
                .subscribe(
                    { response ->
                        //it : Response<ResponseFoodList>

                        view.hideLoading()
                        when (response.code()) {
                            in 200..202 -> {
                                response.body()?.let {
                                    //it: ResponseFoodList
                                    view.loadFoodRandom(it)
                                }
                            }
                            422 -> {

                            }
                            in 400..499 -> {

                            }
                            in 500..599 -> {

                            }
                        }

                    },
                    {
                        // it : Throwable
                        view.hideLoading()
                        view.serverError(it.message.toString())
                    })

        } else {
            view.internetError(false)
        }

    }


}