package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )
        Log.i("GameFragment", "Called ViewModelProviders.of")

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // Set the viewmodel for databinding - this allows the bound layout access
        // to all the data in the ViewModel
        binding.gameViewModel = viewModel
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner

        /** Setting up LiveData observation relationship **/
        // Below Observer Lambda removed because we are adding LiveData to Data Binding
        //viewModel.word.observe(viewLifecycleOwner, Observer { newWord -> binding.wordText.text = newWord })

        // Add Observer for Score
        // Commented out because of desire to add string formatting with data binding.
        //viewModel.score.observe(viewLifecycleOwner, Observer { newScore -> binding.scoreText.text = newScore.toString() })

        // Observer for the Game finished event
        // Observer is removed due to added Listeners in game_fragment.xml
        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer<Boolean> { hasFinished ->
            if (hasFinished) gameFinished()
        })

        // Bindings are removed due to Listeners in game_fragment.xml
        //binding.correctButton.setOnClickListener { onCorrect() }
        //binding.skipButton.setOnClickListener { onSkip() }
        //binding.endGameButton.setOnClickListener { onEndGame() }
        return binding.root
    }


    /** Methods for buttons presses **/
    /** Method declarations for these are commented out because of Listeners in the game_fragment.xml**/

    //private fun onSkip() {
    //    viewModel.onSkip()
    //}
    //private fun onCorrect() {
    //    viewModel.onCorrect()
    //}
    //private fun onEndGame() {
    //    gameFinished()
    //}

    private fun gameFinished() {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections.actionGameToScore()
        action.score = viewModel.score.value?:0
        findNavController(this).navigate(action)
        viewModel.onGameFinishComplete()
    }
}
