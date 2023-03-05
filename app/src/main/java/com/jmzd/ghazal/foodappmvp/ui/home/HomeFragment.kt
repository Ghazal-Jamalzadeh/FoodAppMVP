package com.jmzd.ghazal.foodappmvp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import coil.load
import com.jakewharton.rxbinding4.widget.textChanges
import com.jmzd.ghazal.foodappmvp.R
import com.jmzd.ghazal.foodappmvp.data.model.home.ResponseFoodsList
import com.jmzd.ghazal.foodappmvp.databinding.FragmentHomeBinding
import com.jmzd.ghazal.foodappmvp.utils.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() , HomeContracts.View{

    //binding
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var presenter: HomePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater,container , false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //call api
            presenter.callFoodRandom()

            //Search
            searchEdt.textChanges()
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.toString().length > 1) {
                        //Call api
//                        presenter.callSearchFood(it.toString())

                    }
                }

            //Filter
            filterFood()
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
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //Call api
//                presenter.callFoodsList(filters[p2].toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    override fun loadFoodRandom(data: ResponseFoodsList) {
        binding.headerImg.load(data.meals?.get(0)?.strMealThumb)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun checkInternet(): Boolean {
        return requireContext().isNetworkAvailable()
    }

    override fun internetError(hasInternet: Boolean) {
    }

    override fun serverError(message: String) {
    }

}