/*
 *  Copyright 2019, The Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.android.marsrealestate.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.android.marsrealestate.databinding.FragmentDetailBinding

/**
 * This [Fragment] will show the detailed information about a selected piece of Mars real estate.
 */
class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.setLifecycleOwner(this)  // Clean up code alters all of the following changed from setLifecycleOwner to lifecycleOwner
        val marsProperty = DetailFragmentArgs.fromBundle(arguments!!).selectedProperty //This line gets the selected MarsProperty object from the Safe Args.
        // Notice the use of Kotlin's not-null assertion operator (!!) in the above line. If the selectedProperty isn't there, something terrible has
        // happened and you actually want the code to throw a null pointer. (In production code, you should handle that error in some way.)
        val viewModelFactory = DetailViewModelFactory(marsProperty, application) // Initialize DetailViewModelFactory here.
        binding.viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java) // this line gets a DetailViewModel from the factory and connects all the parts
        return binding.root
    }
}