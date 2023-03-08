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
                        view.serverError(it.message.toString())
                    })

        } else {
            view.internetError(false)
        }

    }

    override fun callCategoriesList() {

        if (view.checkInternet()) {

            view.showLoading()

            disposable = repository.loadCategoriesList()
                .applyIoScheduler()
                .subscribe(
                    { response ->
                        //it : Response<ResponseCategoriesList>
                        view.hideLoading()
                        when (response.code()) {
                            in 200..202 -> {
                                response.body()?.let {
                                    //it: ResponseCategoriesList
                                    if(it.categories.isNotEmpty()){
                                    view.loadCategoriesList(it)
                                    }
                                }
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

    override fun callFoodsList(letter: String) {

        if (view.checkInternet()) {

            view.foodsLoadingState(true)

            disposable = repository.loadFoodsList(letter = letter)
                .applyIoScheduler()
                .subscribe(
                    { response ->
                        //it : Response<ResponseFoodsList>
                        view.foodsLoadingState(false)
                        when (response.code()) {
                            in 200..202 -> {
                                response.body()?.let {
                                    //it: ResponseCategoriesList
                                    it.meals?.let { meals ->
                                    if(meals.isNotEmpty()){
                                        view.loadFoodsList(it)
                                    }

                                    }
                                }
                            }
                        }

                    },
                    {
                        // it : Throwable
                        view.foodsLoadingState(false)
                        view.serverError(it.message.toString())
                    })

        } else {
            view.internetError(false)
        }
    }

    override fun callSearchFood(search: String) {

        if (view.checkInternet()) {

            view.foodsLoadingState(true)

            disposable = repository.searchFood(search = search)
                .applyIoScheduler()
                .subscribe(
                    { response ->
                        //it : Response<ResponseFoodsList>
                        view.foodsLoadingState(false)
                        when (response.code()) {
                            in 200..202 -> {
                                response.body()?.let {
                                    //it: ResponseCategoriesList
                                    it.meals?.let { meals ->
                                        if(meals.isNotEmpty()){
                                            view.loadFoodsList(it)
                                        }

                                    }
                                }
                            }
                        }

                    },
                    {
                        // it : Throwable
                        view.foodsLoadingState(false)
                        view.serverError(it.message.toString())
                    })

        } else {
            view.internetError(false)
        }
    }


}