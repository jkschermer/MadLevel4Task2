package com.hva.madlevel4task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hva.madlevel4task2.R
import com.hva.madlevel4task2.model.Game
import kotlinx.android.synthetic.main.item_game.view.*

class GameListAdapter(private var gameList: List<Game>) :
    RecyclerView.Adapter<GameListAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun databind(game: Game) {
            itemView.tvDate.text = game.date.toString()
            itemView.tvResult.text = game.result

            if (game.move_computer == "Paper") {
                itemView.ivComputer.setImageResource(R.drawable.paper)
            }

            if (game.move_computer == "Rock") {
                itemView.ivComputer.setImageResource(R.drawable.rock)
            }

            if (game.move_computer == "Scissor") {
                itemView.ivComputer.setImageResource(R.drawable.scissors)
            }

            if (game.move_user == "Paper") {
                itemView.ivUser.setImageResource(R.drawable.paper)
            }

            if (game.move_user == "Rock") {
                itemView.ivUser.setImageResource(R.drawable.rock)
            }

            if (game.move_user == "Scissor") {
                itemView.ivUser.setImageResource(R.drawable.scissors)
            }

            changeSize(itemView.ivComputer)
            changeSize(itemView.ivUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_game,
                parent, false)
        )
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(gameList[position])
    }

    // used for changing the size of the imageview
    private fun changeSize(iv: ImageView) {
        val size = 200
        iv.layoutParams.height = size
        iv.layoutParams.width = size
    }
}

