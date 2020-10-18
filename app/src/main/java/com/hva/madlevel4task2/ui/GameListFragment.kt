package com.hva.madlevel4task2.ui

import android.content.res.Resources
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hva.madlevel4task2.R
import com.hva.madlevel4task2.model.Game
import com.hva.madlevel4task2.repository.GameRepository
import com.hva.madlevel4task2.ui.GameListFragment.Companion.gameRepository
import com.hva.madlevel4task2.ui.GameListFragment.Companion.mainScope
import kotlinx.android.synthetic.main.fragment_list_game.*
import kotlinx.android.synthetic.main.fragment_overview.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class GameListFragment : Fragment() {

    companion object {
        lateinit var gameRepository: GameRepository
        val mainScope = CoroutineScope(Dispatchers.Main)
        var results = ""
        var gamesWon = 0
        var gamesLost = 0
        var gamesDrawn = 0
    }

    private var games = arrayListOf<Game>()
    private val gameListAdapter = GameListAdapter(games)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // makes it possible to use triggers on menu items
        setHasOptionsMenu(true)

        (activity as MainActivity).changeActionBar(
            resources.getString(R.string.page_home_title),
            R.id.history, R.menu.menu_second_fragment,
            true
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragments
        return inflater.inflate(R.layout.fragment_list_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameRepository = GameRepository(requireContext())
        getGamesFromDatabase()
        initView()
    }


    private fun initView() {
        rvGameList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvGameList.adapter = gameListAdapter
        rvGameList.setHasFixedSize(true)
        rvGameList.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        createItemTouchHelper().attachToRecyclerView(rvGameList)
    }

    // removes all the games with the background thread
    private fun removeAllGames() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            getGamesFromDatabase()
        }
    }

    // gets the list of games and refreshes it
    fun getGamesFromDatabase() {
        mainScope.launch {
            val gameList = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            countResult()
            this@GameListFragment.games.clear()
            this@GameListFragment.games.addAll(gameList)
            this@GameListFragment.gameListAdapter.notifyDataSetChanged()
        }
    }

    // counts the results of games
    private fun countResult() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gamesWon =
                    gameRepository.getAmountOfGamesWon(resources.getString(R.string.you_win))
                gamesLost =
                    gameRepository.getAmountOfGamesLost(resources.getString(R.string.computer_wins))
                gamesDrawn =
                    gameRepository.getAmountOfGamesDrawn(resources.getString(R.string.draw))
            }
        }
    }

    // inflate the corresponding menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.menu_second_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // selects the item in the menu and fires a trigger when the icon is clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.deleteAll ->  {
                Toast.makeText(context, "Deleted all items", Toast.LENGTH_SHORT).show()
                removeAllGames()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // hides the item, get the item and hide it from the user
    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.history)

        if (item != null) {
            item.isVisible = false
        }
    }

    // deleting of the object with a swipe
    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val gameToDelete = games[position]
                mainScope.launch {
                    withContext(Dispatchers.IO) {
                        gameRepository.deleteGame(gameToDelete)
                    }
                    getGamesFromDatabase()
                }
            }
        }
        return ItemTouchHelper(callback)
    }
}