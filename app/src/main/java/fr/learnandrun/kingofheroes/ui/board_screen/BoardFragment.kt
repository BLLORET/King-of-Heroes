package fr.learnandrun.kingofheroes.ui.board_screen

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.model.BoardViewModel
import fr.learnandrun.kingofheroes.tools.android.DefaultFragment

class BoardFragment : DefaultFragment(R.layout.fragment_board) {

    private lateinit var boardViewModel: BoardViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the board view model
        boardViewModel = ViewModelProvider(this).get(BoardViewModel::class.java)
        boardViewModel.board.startGame()
    }

}