package com.example.android.navigation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding= DataBindingUtil.inflate<FragmentTitleBinding>(inflater,
                R.layout.fragment_title,container,false)

        binding.playButton.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
        }
        setHasOptionsMenu(true)

        //Below is from the section 4 logging stuff comment out if you don't want it, but it does require imoport android.util.Log
        //Log.i("TitleFragment", "onCreateView called")

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,view!!.findNavController())
                ||super.onOptionsItemSelected(item)
    }

    // The Remaining overrides are for the Logging portion in part 4, comment out so it doesn't run if you are done testing.

    /* override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i("TitleFragment", "onAttach called")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TitleFragment", "onCreate called")
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("TitleFragment", "onActivityCreated called")
    }
    override fun onStart() {
        super.onStart()
        Log.i("TitleFragment", "onStart called")
    }
    override fun onResume() {
        super.onResume()
        Log.i("TitleFragment", "onResume called")
    }
    override fun onPause() {
        super.onPause()
        Log.i("TitleFragment", "onPause called")
    }
    override fun onStop() {
        super.onStop()
        Log.i("TitleFragment", "onStop called")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("TitleFragment", "onDestroyView called")
    }
    override fun onDetach() {
        super.onDetach()
        Log.i("TitleFragment", "onDetach called")
    } */
}