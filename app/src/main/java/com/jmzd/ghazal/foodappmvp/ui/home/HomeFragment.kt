package com.jmzd.ghazal.foodappmvp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.jakewharton.rxbinding4.widget.textChanges
import com.jmzd.ghazal.foodappmvp.R
import com.jmzd.ghazal.foodappmvp.data.model.home.ResponseCategoriesList
import com.jmzd.ghazal.foodappmvp.data.model.home.ResponseFoodsList
import com.jmzd.ghazal.foodappmvp.databinding.FragmentHomeBinding
import com.jmzd.ghazal.foodappmvp.ui.home.adapters.CategoriesAdapter
import com.jmzd.ghazal.foodappmvp.ui.home.adapters.FoodsAdapter
import com.jmzd.ghazal.foodappmvp.utils.isNetworkAvailable
import com.jmzd.ghazal.foodappmvp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import greyfox.rxnetwork.RxNetwork
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() , HomeContracts.View{

    //binding
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var presenter: HomePresenter

    @Inject
    lateinit var categoriesAdapter: CategoriesAdapter

    @Inject
    lateinit var foodsAdapter: FoodsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater,container , false)
        return  binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {

            //call api
            presenter.callFoodRandom()
            presenter.callCategoriesList()
            val rand = ('A'..'Z').random()
            presenter.callFoodsList(rand.toString())

            //Search
            searchEdt.textChanges()
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.toString().length > 1) {
                        //Call api
                        presenter.callSearchFood(it.toString())
                    }
                }

            //Filter
            filterFood()

            //Check internet
            RxNetwork.init(requireContext()).observe()
                .subscribeOn(Schedulers.io()) // همیشه از RX3 بر میداشتیم schedulers را ولی برای این لایبرری باید از Rx معمولی import کنیم
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe {
                    // it : RxNetworkInfo
                    /* اگه بزنیم it خیلی گزینه های متنوع باحالی میده بمون */
                    internetError(it.isConnected) // صدا زدن متدهای اینترفیس ویو داخل خود فرگمنت
                }
        }

    }

    private fun filterFood() {
        /*
        * نکته کاتلینی:
        * ما یک لیست نیاز داریم که حروف a تا z داخلش باشه ولی نمیخوایم دستی بنویسیم
        * listOf('A'..'Z')
        * اگر این را بنویسیم خروجی به شکل زیر خواهد بود:
        * a z (یک آیتم)
        * ولی ما میخوایم خروجی به شکل زیر باشه
        * a
        * b
        * c
        * ...
        * z
        * (آیتم های جدا از هم)
        * .flatten()
        * ابن کار را برای ما انجام میده و لیست را به ترتیب میسازه
        * */
        val filters = listOf('A'..'Z').flatten()

        /* این دفعه خودمون لایه کاستومایز شده خودمون رو میخوایم به اسپینر بدیم*/

        /*
        * ما دو تا بخش داریم توی اسپینر
        * 1- آیتمی که سلکت شده
        * 2- آیتم هایی که توی دراپ داون باز میشن
        * ما ه ازای هر کدام از این ها یک لایه طراحی میکنیم
        *
        * اولی توی خود آداپتر ست میشود
        * دومی توی setDropDownViewResource ست میشود
        *
        *
        * */

        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, filters)
        adapter.setDropDownViewResource(R.layout.item_spinner_list)
        binding.filterSpinner.adapter = adapter
        binding.filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                //Call api
                presenter.callFoodsList(filters[position].toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    override fun loadFoodRandom(data: ResponseFoodsList) {
        binding.headerImg.load(data.meals?.get(0)?.strMealThumb)
    }

    override fun loadCategoriesList(data: ResponseCategoriesList) {
        categoriesAdapter.setData(data.categories)
        binding.categoryList.apply {
            layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL , false)
            adapter = categoriesAdapter
        }

        categoriesAdapter.setOnItemClickListener {
            //it : ResponseCategoriesList.Category
            presenter.callFoodsByCategory(it.strCategory.toString())
        }
    }

    override fun loadFoodsList(data: ResponseFoodsList) {

        //visibility mode
        binding.foodsList.visibility = View.VISIBLE
        binding.homeDisLay.visibility = View.GONE

        data.meals.let {
        foodsAdapter.setData(it!!)
        }
        binding.foodsList.apply {
            layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL , false)
            adapter = foodsAdapter
        }

        foodsAdapter.setOnItemClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.idMeal!!.toInt())
            findNavController().navigate(direction)
        }
    }

    override fun foodsLoadingState(isShown: Boolean) {
       binding.apply {
           if(isShown){
               foodsList.visibility = View.INVISIBLE
               homeFoodsLoading.visibility = View.VISIBLE
           }else{
               foodsList.visibility = View.VISIBLE
               homeFoodsLoading.visibility = View.INVISIBLE
           }
       }
    }

    override fun emptyList() {
        binding.apply {
            foodsList.visibility = View.INVISIBLE
            homeDisLay.visibility = View.VISIBLE
        }
    }

    override fun showLoading() {
       binding.apply {
           homeCategoryLoading.visibility = View.VISIBLE
           categoryList.visibility = View.INVISIBLE
           //change view
           disconnectLay.disImg.setImageResource(R.drawable.box)
           disconnectLay.disTxt.text = getString(R.string.emptyList)
       }
    }

    override fun hideLoading() {
        binding.apply {
            homeCategoryLoading.visibility = View.INVISIBLE
            categoryList.visibility = View.VISIBLE
        }
    }

    override fun checkInternet(): Boolean {
        return requireContext().isNetworkAvailable()
    }

    override fun internetError(hasInternet: Boolean) {
        binding.apply {
            if (!hasInternet) {
                homeContent.visibility = View.INVISIBLE
                homeDisLay.visibility = View.VISIBLE
                //Change view
                disconnectLay.disImg.setImageResource(R.drawable.disconnect)
                disconnectLay.disTxt.text = getString(R.string.checkInternet)
            } else {
                homeContent.visibility = View.VISIBLE
                homeDisLay.visibility = View.GONE
                //Call api
//                presenter.callCategoriesList()
//                val rand = ('A'..'Z').random()
//                presenter.callFoodsList(rand.toString())
            }
        }

    }

    override fun serverError(message: String) {
     //   Snackbar.make(binding.root , message , Snackbar.LENGTH_SHORT).show()
        binding.root.showSnackBar(message)
    }

}