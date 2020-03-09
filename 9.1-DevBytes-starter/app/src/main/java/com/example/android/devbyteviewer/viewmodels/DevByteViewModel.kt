/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.devbyteviewer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.devbyteviewer.database.getDatabase
//import com.example.android.devbyteviewer.domain.DevByteVideo
//import com.example.android.devbyteviewer.network.DevByteNetwork
//import com.example.android.devbyteviewer.network.asDomainModel
import com.example.android.devbyteviewer.repository.VideosRepository
import kotlinx.coroutines.*
import java.io.IOException

/**
 * DevByteViewModel designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows data to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available.
 *
 * @param application The application that this viewmodel is attached to, it's safe to hold a
 * reference to applications across rotation since Application is never recreated during actiivty
 * or fragment lifecycle events.
 */
class DevByteViewModel(application: Application) : AndroidViewModel(application) {


     // create a private member variable called videosRepository of the type VideosRepository.
     // Instantiate the variable by passing in the singleton VideosDatabase object.
    private val videosRepository = VideosRepository(getDatabase(application))
     // after instantiating the videosRepository object, add a new val called playlist for holding a LiveData list of videos from the repository
    /**
     * A playlist of videos displayed on the screen.
     */
    val playlist = videosRepository.videos

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     *
     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
     * viewModelJob.cancel()
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * A playlist of videos that can be shown on the screen. This is private to avoid exposing a
     * way to set this value to observers.
     */
    //Below comment out per 9.1 tutorial, part 8
    //private val _playlist = MutableLiveData<List<DevByteVideo>>()

    /**
     * A playlist of videos that can be shown on the screen. Views should use this to get access
     * to the data.
     */
    //Below comment out per 9.1 tutorial, part 8
    //val playlist: LiveData<List<DevByteVideo>>
    //    get() = _playlist
    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _eventNetworkError = MutableLiveData(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _isNetworkErrorShown = MutableLiveData(false)

    /**
     * Flag to display the error message. Views should use this to get access
     * to the data.
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        //refreshDataFromNetwork()  // commend out due to newly created refreshDataFromRepository Method.
        refreshDataFromRepository()
    }
    /**
     * Refresh data from network and pass it via LiveData. Use a coroutine launch to get to
     * background thread.
     */
    //Replace below refreshDataFromNetwork with refreshDataFromRepository
    //private fun refreshDataFromNetwork() = viewModelScope.launch {
    //
    //    try {
    //         val playlist = DevByteNetwork.devbytes.getPlaylist().await()
    //        _playlist.postValue(playlist.asDomainModel())
    //
    //        _eventNetworkError.value = false
    //        _isNetworkErrorShown.value = false
    //
    //    } catch (networkError: IOException) {
            // Show a Toast error message and hide the progress bar.
    //        _eventNetworkError.value = true
    //    }
    //}
    /**
     * Refresh data from the repository. Use a coroutine launch to run in a
     * background thread.
     */
    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                videosRepository.refreshVideos()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(playlist.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }
    /**
     * Resets the network error flag.
     */
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DevByteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DevByteViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
