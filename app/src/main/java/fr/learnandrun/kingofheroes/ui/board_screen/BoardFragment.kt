package fr.learnandrun.kingofheroes.ui.board_screen

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.model.BoardViewModel
import fr.learnandrun.kingofheroes.model.PlayerViewModel
import fr.learnandrun.kingofheroes.tools.android.DefaultFragment
import kotlinx.android.synthetic.main.fragment_board.*

class BoardFragment : DefaultFragment(R.layout.fragment_board) {

    private lateinit var boardViewModel: BoardViewModel
    private val args : BoardFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the board view model
        boardViewModel = ViewModelProvider(this).get(BoardViewModel::class.java)

        // TODO: ASK DAVID MENAGER
        val player1ViewModel = PlayerViewModel(
            board_player1_card_image_view,
            board_player1_display_name_text_view,
            board_player1_stats_view
        )
        val player2ViewModel = PlayerViewModel(
            board_player2_card_image_view,
            board_player2_display_name_text_view,
            board_player2_stats_view
        )
        val player3ViewModel = PlayerViewModel(
            board_player3_card_image_view,
            board_player3_display_name_text_view,
            board_player3_stats_view
        )
        val player4ViewModel = PlayerViewModel(
            board_player4_card_image_view,
            board_player4_display_name_text_view,
            board_player4_stats_view
        )
        val playerViewModels = listOf(
            player1ViewModel,
            player2ViewModel,
            player3ViewModel,
            player4ViewModel
        )





        boardViewModel.startGame(args.selectedHero)
    }

}