package fr.learnandrun.kingofheroes.ui.board_screen

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.business.Player
import fr.learnandrun.kingofheroes.business.User
import fr.learnandrun.kingofheroes.model.BoardViewModel
import fr.learnandrun.kingofheroes.tools.android.DefaultFragment
import fr.learnandrun.kingofheroes.ui.view.StatsView
import kotlinx.android.synthetic.main.fragment_board.*

class BoardFragment : DefaultFragment(R.layout.fragment_board) {

    private lateinit var boardViewModel: BoardViewModel
    private val args : BoardFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the board view model
        boardViewModel = ViewModelProvider(requireActivity()).get(BoardViewModel::class.java)

        boardViewModel.showRollDicesInterfaceLambda = { isUser ->
            findNavController().navigate(
                BoardFragmentDirections.actionBoardFragmentToDiceFragment(isUser)
            )
        }

        val isInit = boardViewModel.isInit
        if (!isInit)
            boardViewModel.initGame(args.selectedHero)

        // TODO: ASK DAVID MENAGER
        fun linkPlayerToView(
            player: Player,
            board_player_card_image_view: ImageView,
            board_player_display_name_text_view: TextView,
            board_player_stats_view: StatsView
        ) {
            player.healthLiveData.removeObservers(viewLifecycleOwner)
            player.healthLiveData.observe(viewLifecycleOwner) {
                board_player_stats_view.life = it
                if (it == 0)
                    board_player_card_image_view.setColorFilter(
                        ContextCompat.getColor(requireContext(), R.color.looseGray))
            }
            player.victoryPointsLiveData.removeObservers(viewLifecycleOwner)
            player.victoryPointsLiveData.observe(viewLifecycleOwner) {
                board_player_stats_view.victory = it
            }
            player.energyLiveData.removeObservers(viewLifecycleOwner)
            player.energyLiveData.observe(viewLifecycleOwner) {
                board_player_stats_view.stamina = it
            }
            //TODO: USE observable...
            board_player_card_image_view.setImageDrawable(player.hero.getImage(requireContext()))
            board_player_display_name_text_view.text = getString(
                R.string.user_display_name_format,
                player.hero.getDisplayName(requireContext()),
                if (player is User) getString(R.string.you) else ""
            )
        }
        linkPlayerToView(
            boardViewModel.players[0],
            board_player1_card_image_view,
            board_player1_display_name_text_view,
            board_player1_stats_view
        )
        linkPlayerToView(
            boardViewModel.players[1],
            board_player2_card_image_view,
            board_player2_display_name_text_view,
            board_player2_stats_view
        )
        linkPlayerToView(
            boardViewModel.players[2],
            board_player3_card_image_view,
            board_player3_display_name_text_view,
            board_player3_stats_view
        )
        linkPlayerToView(
            boardViewModel.players[3],
            board_player4_card_image_view,
            board_player4_display_name_text_view,
            board_player4_stats_view
        )

        boardViewModel.board.playerInsideCityLiveData.removeObservers(viewLifecycleOwner)
        boardViewModel.board.playerInsideCityLiveData.observe(viewLifecycleOwner) {
            it?.let { player ->
                board_player_in_city_image_view.setImageDrawable(
                    player.hero.getImage(requireContext())
                )
            } ?: board_player_in_city_image_view.setImageDrawable(null)
        }

        if (!isInit)
            boardViewModel.startGame()
    }
}