package fr.learnandrun.kingofheroes.ui.board_screen

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.view_model.PartyViewModel
import fr.learnandrun.kingofheroes.business.User
import fr.learnandrun.kingofheroes.view_model.BoardViewModel
import fr.learnandrun.kingofheroes.tools.android.DefaultFragment
import fr.learnandrun.kingofheroes.tools.android.toast
import fr.learnandrun.kingofheroes.ui.view.LeaveCityAlertView
import fr.learnandrun.kingofheroes.ui.view.StatsView
import kotlinx.android.synthetic.main.fragment_board.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BoardFragment : DefaultFragment(R.layout.fragment_board) {

    private val partyViewModel: PartyViewModel by sharedViewModel()
    private val boardViewModel: BoardViewModel by viewModel { parametersOf(partyViewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun drawableOf(id: Int) = ContextCompat.getDrawable(requireContext(), id)
        fun colorOf(id: Int) = ContextCompat.getColor(requireContext(), id)

        data class PlayerInfoView(
            val playerCardImageView: ImageView,
            val playerDisplayNameTextView: TextView,
            val playerStatsView: StatsView
        )

        val listPlayersViews = listOf(
            PlayerInfoView(
                board_player1_card_image_view,
                board_player1_display_name_text_view,
                board_player1_stats_view
            ),
            PlayerInfoView(
                board_player2_card_image_view,
                board_player2_display_name_text_view,
                board_player2_stats_view
            ),
            PlayerInfoView(
                board_player3_card_image_view,
                board_player3_display_name_text_view,
                board_player3_stats_view
            ),
            PlayerInfoView(
                board_player4_card_image_view,
                board_player4_display_name_text_view,
                board_player4_stats_view
            )
        )

        listPlayersViews.forEachIndexed { index, playerInfoView ->
            val player = partyViewModel.board.players[index]
            playerInfoView.apply {
                playerCardImageView.setImageDrawable(drawableOf(player.hero.imageId))
                playerDisplayNameTextView.text = getString(R.string.user_display_name_format,
                    getString(player.hero.displayNameId),
                    if (player is User) getString(R.string.you) else ""
                )
                player.healthLiveData.observe(viewLifecycleOwner) {
                    playerStatsView.life = it
                    if (it == 0) playerCardImageView.setColorFilter(colorOf(R.color.looseGray))
                }
                player.victoryPointsLiveData.observe(viewLifecycleOwner) {
                    playerStatsView.victory = it
                }
                player.energyLiveData.observe(viewLifecycleOwner) {
                    playerStatsView.stamina = it
                }
            }
        }

        partyViewModel.board.playerInsideCityLive.observe(viewLifecycleOwner) {
            board_player_in_city_image_view.setImageDrawable(it?.hero?.imageId?.let(::drawableOf))
        }

        partyViewModel.proposeToLeaveTheCityEvent.subscribe(viewLifecycleOwner) {
            boardViewModel.leaveTheCity(LeaveCityAlertView(requireContext()))
        }

        partyViewModel.toastEvent.subscribe(viewLifecycleOwner) {
            toast(getString(it))
        }

        partyViewModel.navigateEvent.subscribe(viewLifecycleOwner) {
            findNavController().navigate(it)
        }

        partyViewModel.resumeGame()
    }

}