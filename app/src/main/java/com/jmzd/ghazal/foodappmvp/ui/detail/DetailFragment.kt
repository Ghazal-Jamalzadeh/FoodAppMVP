package com.jmzd.ghazal.foodappmvp.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.gson.Gson
import com.jmzd.ghazal.foodappmvp.R
import com.jmzd.ghazal.foodappmvp.data.database.FoodEntity
import com.jmzd.ghazal.foodappmvp.data.model.home.ResponseFoodsList
import com.jmzd.ghazal.foodappmvp.databinding.FragmentDetailBinding
import com.jmzd.ghazal.foodappmvp.databinding.FragmentHomeBinding
import com.jmzd.ghazal.foodappmvp.ui.detail.player.PlayerActivity
import com.jmzd.ghazal.foodappmvp.utils.VIDEO_ID
import com.jmzd.ghazal.foodappmvp.utils.isNetworkAvailable
import com.jmzd.ghazal.foodappmvp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import greyfox.rxnetwork.RxNetwork
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject


@AndroidEntryPoint
class DetailFragment : Fragment(), DetailContracts.View {
    //Binding
    private lateinit var binding: FragmentDetailBinding

    @Inject
    lateinit var presenter: DetailPresenter

    @Inject
    lateinit var entity : FoodEntity

    //Other
    private val args: DetailFragmentArgs by navArgs()
    private var foodId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailBinding.inflate(layoutInflater , container , false)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Get food id
        foodId = args.foodId
        if (foodId > 0) {
            //Call api
            presenter.callDetailApi(foodId)
        }
        //Check internet
        RxNetwork.init(requireContext()).observe()
            .subscribeOn(Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe {
                internetError(it.isConnected)
            }
        //Back
        binding.detailBack.setOnClickListener { findNavController().popBackStack() }
    }

    override fun loadDetail(data: ResponseFoodsList) {
        //InitViews
        binding.apply {

            data.meals?.get(0)?.let { itMeal ->
                //Favorite
                entity.id = itMeal.idMeal.toString().toInt()
                entity.title = itMeal.strMeal.toString()
                entity.img = itMeal.strMealThumb.toString()
                presenter.checkFavorite(itMeal.idMeal.toString().toInt())

                //set data
                foodCoverImg.load(itMeal.strMealThumb) {
                    crossfade(true)
                    crossfade(500)
                }
                foodCategoryTxt.text = itMeal.strCategory
                foodAreaTxt.text = itMeal.strArea
                foodTitleTxt.text = itMeal.strMeal
                foodDescTxt.text = itMeal.strInstructions
                //Play
                if (itMeal.strYoutube != null) {
                    foodPlayImg.visibility = View.VISIBLE
                    foodPlayImg.setOnClickListener {
                        /*روش اول*/
//                        startActivity(Intent(Intent.ACTION_VIEW , Uri.parse(itMeal.strSource)))

                        /*روش دوم*/
                        val videoId = itMeal.strYoutube.split("=")[1]
                        Intent(requireContext(), PlayerActivity::class.java).also {
                            it.putExtra(VIDEO_ID, videoId)
                            startActivity(it)
                        }
                    }
                } else {
                    foodPlayImg.visibility = View.GONE
                }
                //Source
                if (itMeal.strSource != null) {
                    foodSourceImg.visibility = View.VISIBLE
                    foodSourceImg.setOnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(itMeal.strSource)))
                    }
                } else {
                    foodSourceImg.visibility = View.GONE
                }
            }
            //Json converter
            val jsonData = JSONObject(Gson().toJson(data))
            val meals = jsonData.getJSONArray("meals")
            val meal = meals.getJSONObject(0)
            //Ingredient
            for (i in 1..15) {
                val ingredient = meal.getString("strIngredient$i")
                if (ingredient.isNullOrEmpty().not()) {
                    ingredientsTxt.append("$ingredient\n")
                }
            }
            //Measure
            for (i in 1..15) {
                val measure = meal.getString("strMeasure$i")
                if (measure.isNullOrEmpty().not()) {
                    measureTxt.append("$measure\n")
                }
            }
        }
    }

    override fun updateFavorite(isAdded: Boolean) {
        binding.apply {
            //Click
            detailFav.setOnClickListener {
                if (isAdded) {
                    presenter.deleteFood(entity)
                } else {
                    presenter.saveFood(entity)
                }
            }
            //Change color
            if (isAdded) {
                detailFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.tartOrange))
            } else {
                detailFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
    }


    override fun showLoading() {
        binding.detailLoading.visibility = View.VISIBLE
        binding.detailContentLay.visibility = View.GONE
    }

    override fun hideLoading() {
        binding.detailLoading.visibility = View.GONE
        binding.detailContentLay.visibility = View.VISIBLE
    }

    override fun checkInternet(): Boolean {
        return requireContext().isNetworkAvailable()
    }

    override fun internetError(hasInternet: Boolean) {
        binding.apply {
            if (!hasInternet) {
                detailContentLay.visibility = View.GONE
                homeDisLay.visibility = View.VISIBLE
                //Change view
                disconnectLay.disImg.setImageResource(R.drawable.disconnect)
                disconnectLay.disTxt.text = getString(R.string.checkInternet)
            } else {
                detailContentLay.visibility = View.VISIBLE
                homeDisLay.visibility = View.GONE
            }
        }
    }

    override fun serverError(message: String) {
        binding.root.showSnackBar(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onStop()
    }
}