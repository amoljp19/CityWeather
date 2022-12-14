package com.softaai.cityweather.weather.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.jakewharton.rxbinding2.widget.RxTextView
import com.softaai.cityweather.CityWeatherApplication
import com.softaai.cityweather.R
import com.softaai.cityweather.data.dto.WeatherDetailsDTO
import com.softaai.cityweather.data.room.CityEntity
import com.softaai.cityweather.utils.InputValidator.isValidCityInput
import com.softaai.cityweather.weather.viewmodel.WeatherViewModel
import com.softaai.cityweather.weather.viewmodel.WeatherViewModelFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_city_weather_search.*
import org.parceler.Parcels
import javax.inject.Inject


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

class CityWeatherSearchActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: WeatherViewModelFactory
    private lateinit var viewModel: WeatherViewModel
    private var isConnectedToInternet: Boolean = false
    private var searchedCityNames = ArrayList<String>()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_weather_search)

        CityWeatherApplication.appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel::class.java)

        val itemInputNameObservable = RxTextView.textChanges(autocomplete_textView)
            .map { inputText: CharSequence -> inputText.isEmpty() || !isValidCityInput(inputText.toString()) }
            .distinctUntilChanged()

        compositeDisposable.add(setupTextInputObserver(itemInputNameObservable))

        setupSearchedCityClickedListener()
    }

private fun setupSearchedCityClickedListener() {
    cityButton.setOnClickListener {
        if (!isConnectedToInternet) {
            Toast.makeText(this, getString(R.string.user_has_not_internet_connection_message), Toast.LENGTH_SHORT).show()
        }
        else{
            processRequestStartUI()
            val searchedCityName = autocomplete_textView.text.toString()
            setupWeatherDetailsObserver(searchedCityName)?.let { it1 -> compositeDisposable.add(it1) }
        }
    }
}

private fun setupTextInputObserver(itemInputNameObservable: Observable<Boolean>): Disposable {
    return itemInputNameObservable.subscribe { inputIsEmpty: Boolean ->
        cityTextInputLayout.error = getString(R.string.invalid_input_message)
        cityTextInputLayout.isErrorEnabled = inputIsEmpty
        cityButton?.isEnabled = !inputIsEmpty
    }
}

private fun setupWeatherDetailsObserver(searchedCityName: String): Disposable? {
    return viewModel.getWeather(searchedCityName)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { weatherResponse: WeatherDetailsDTO? ->
                resolveRequestEndUI()
                navigateToDetailsActivity(weatherResponse)

                if (!(searchedCityNames.contains(searchedCityName)))
                    viewModel.addCity(searchedCityName)
            },
            { throwable: Throwable? ->
                resolveRequestEndUI()
                if (!isConnectedToInternet) {
                    Toast.makeText(this, getString(R.string.user_has_not_internet_connection_message), Toast.LENGTH_SHORT).show()
                } else {
                    throwable?.printStackTrace()
                    Toast.makeText(this, getString(R.string.error_with_fetching_weather_details_message), Toast.LENGTH_SHORT).show()
                }
            }
        )
}

private fun processRequestStartUI() {
    inputLinearLayout.isEnabled = false
    inputLinearLayout.alpha = 0.5f
    progressBar.visibility = View.VISIBLE
}

private fun resolveRequestEndUI() {
    inputLinearLayout.isEnabled = true
    inputLinearLayout.alpha = 1f
    progressBar.visibility = View.INVISIBLE
}

private fun navigateToDetailsActivity(weatherResponse: WeatherDetailsDTO?) {
    val intent = Intent(this, CityWeatherDetailsActivity::class.java)
    intent.putExtra(getString(R.string.intentWeatherDetailsParcelerBundleName), Parcels.wrap(weatherResponse))
    startActivity(intent)
}

override fun onStart() {
    super.onStart()
    compositeDisposable.add(setupSearchedCitiesObserver())
    compositeDisposable.add(setupInternetConnectionObserver())

}

private fun setupSearchedCitiesObserver(): Disposable {
    return viewModel.getCities()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { citiesList: List<CityEntity> ->
            searchedCityNames.clear()
            citiesList.forEach { searchedCityNames.add(it.cityName) }

            val adapter = ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, searchedCityNames)

            autocomplete_textView.setAdapter(adapter)
            autocomplete_textView.threshold = 0
        }
}

private fun setupInternetConnectionObserver(): Disposable {
    return ReactiveNetwork.observeInternetConnectivity()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { isConnected: Boolean? ->
                isConnected?.let {
                    if (!isConnected)
                        Toast.makeText(this, getString(R.string.user_has_lost_internet_connection_message), Toast.LENGTH_SHORT).show()
                    isConnectedToInternet = isConnected
                }
            },
            { t: Throwable? ->
                Log.v("ReactiveNetwork", t?.message)
            }
        )
}

override fun onStop() {
    super.onStop()
    compositeDisposable.clear()
}

}