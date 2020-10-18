package com.hva.madlevel4task2.ui

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.hva.madlevel4task2.R
import com.hva.madlevel4task2.model.Game
import com.hva.madlevel4task2.repository.GameRepository
import com.hva.madlevel4task2.ui.GameListFragment.Companion.gameRepository
import com.hva.madlevel4task2.ui.GameListFragment.Companion.gamesDrawn
import com.hva.madlevel4task2.ui.GameListFragment.Companion.gamesLost
import com.hva.madlevel4task2.ui.GameListFragment.Companion.gamesWon
import com.hva.madlevel4task2.ui.GameListFragment.Companion.mainScope
import com.hva.madlevel4task2.ui.GameListFragment.Companion.results
import kotlinx.android.synthetic.main.fragment_overview.*
import kotlinx.android.synthetic.main.fragment_overview.view.*
import kotlinx.coroutines.*
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class OverviewFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).changeActionBar(resources.getString(
            R.string.app_name
        ),
            R.id.deleteAll, R.menu.menu_main, false)
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameRepository = GameRepository(requireContext())
        // sets the statistics

        results = getString(
            R.string.result,
            gamesWon,
            gamesDrawn,
            gamesLost
        )

        tvOverviewResults.text = results

        view.ivRock.setOnClickListener {
            ivUserOverview.setImageResource(R.drawable.rock)
            val imageName = ivRock.tag
            checkResult(imageName as String)
        }

        view.ivScissor.setOnClickListener {
            ivUserOverview.setImageResource(R.drawable.scissors)
            val imageName = ivScissor.tag
            checkResult(imageName as String)
        }

        view.ivPaper.setOnClickListener {
            ivUserOverview.setImageResource(R.drawable.paper)
            val imageName = ivPaper.tag
            checkResult(imageName as String)
        }
    }

    // used for changing the size of the imageview
    private fun changeSize(iv: ImageView) {
        val size = 200
        iv.layoutParams.height = size
        iv.layoutParams.width = size
    }

    // checks if the images are the same, if they are it will return true
    private fun compareMoves(moveUser: String, move: String) : Boolean {
        return (moveUser == move)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkResult(moveUser: String) {
        // set the string for the winner of the game
        var result = ""
        val randomMove = getRandomMoveComputer()


        // If computer is rock
        if (randomMove == resources.getString(R.string.Rock)) {
            ivComputerOverview.setImageResource(R.drawable.rock)
                // user is paper
               if (compareMoves(moveUser, resources.getString(R.string.Paper))) {
                   result = resources.getString(R.string.you_win)
                   tvWinner.text = result
                   gamesWon++
               }
                // user is scissor
               if (compareMoves(moveUser, resources.getString(R.string.Scissor))) {
                   result = resources.getString(R.string.computer_wins)
                   tvWinner.text = result
                   gamesLost++
               }
                // user is rock
               if (compareMoves(moveUser, resources.getString(R.string.Rock))) {
                   result = resources.getString(R.string.draw)
                   tvWinner.text  = result
                   gamesDrawn++
               }

            val date = Calendar.getInstance().time
            addGame(date, result, moveUser, randomMove)
            }

        // if computer is paper
        if (randomMove == resources.getString(R.string.Paper))  {
            ivComputerOverview.setImageResource(R.drawable.paper)
            // user is paper
            if (compareMoves(moveUser, resources.getString(R.string.Paper))) {
                result = resources.getString(R.string.draw)
                tvWinner.text = result
                gamesDrawn++
            }
            // user is scissor
            if (compareMoves(moveUser, resources.getString(R.string.Scissor))) {
                result = resources.getString(R.string.you_win)
                tvWinner.text = result
                gamesWon++
            }
            // user is rock
            if (compareMoves(moveUser, resources.getString(R.string.Rock))) {
                result = resources.getString(R.string.computer_wins)
                tvWinner.text = result
                gamesLost++
            }
            val date = Calendar.getInstance().time
            addGame(date, result, moveUser, randomMove)
        }

        // if computer is scissor
        if (randomMove == resources.getString(R.string.Scissor)) {
            ivComputerOverview.setImageResource(R.drawable.scissors)
            // user is paper
            if (compareMoves(moveUser, resources.getString(R.string.Paper))) {
                result = resources.getString(R.string.computer_wins)
                tvWinner.text = result
                gamesLost++
            }
            // user is scissor
            if (compareMoves(moveUser, resources.getString(R.string.Scissor))) {
                result = resources.getString(R.string.draw)
                tvWinner.text = result
                gamesDrawn++
            }
            // user is rock
            if (compareMoves(moveUser, resources.getString(R.string.Rock))) {
                result = resources.getString(R.string.you_win)
                tvWinner.text = result
                gamesWon++
            }
            val date = Calendar.getInstance().time

            addGame(date, result, moveUser, randomMove)
        }
        // changing the sizes of the image displayed
        changeSize(ivComputerOverview)
        changeSize(ivUserOverview)
    }

    fun addGame(date: Date, result: String, moveUser: String, moveComputer: String) {
        val game = Game()
        game.date = date
        game.result = result
        game.move_user = moveUser
        game.move_computer = moveComputer
        game.amountOfGamesWon = gamesWon
        game.amountOfGamesDrawn = gamesDrawn
        game.amountOfGamesLost = gamesLost

        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }
        }

        // updating the results in the overview
        results = getString(
            R.string.result,
            gamesWon,
            gamesDrawn,
            gamesLost
        )

        tvOverviewResults.text = results

//        refreshList()
    }

//    private fun refreshList() {
//        val fragmentManager= activity?.supportFragmentManager
//        val gameFragment = fragmentManager?.findFragmentById(R.id.SecondFragment)
//        (gameFragment as? GameListFragment)?.getGamesFromDatabase()
//    }


//    fun countResult() {
//        val userResult = UserResult()
//        userResult.amountOfGamesWon = gamesWon
//        userResult.amountOfGamesDrawn = gamesDrawn
//        userResult.amountOfGamesLost = gamesLost
//
//        mainScope.launch {
//            withContext(Dispatchers.IO) {
//                userResultRepository.insertResult(userResult)
//            }
//        }
//    }

    // gives a random string from the array of moves
    private fun getRandomMoveComputer() : String {
        val values = resources.getStringArray(R.array.movements)
        return values.random()
    }

}

