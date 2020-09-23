package fr.learnandrun.kingofheroes.ui.dice_screen

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.business.Player
import fr.learnandrun.kingofheroes.business.User
import fr.learnandrun.kingofheroes.business.dice.DiceFace
import fr.learnandrun.kingofheroes.view_model.BoardViewModel
import fr.learnandrun.kingofheroes.tools.android.DefaultFragment
import fr.learnandrun.kingofheroes.ui.view.StatsView
import kotlinx.android.synthetic.main.fragment_dice.*

class DiceFragment : DefaultFragment(R.layout.fragment_dice) {

    private lateinit var boardViewModel: BoardViewModel
    private val args : DiceFragmentArgs by navArgs()

    private fun isDiceSelected(dice: ImageView): Boolean = dice.colorFilter != null

    private fun isOneOrMoreDicesSelected(): Boolean =
        isDiceSelected(dice_img_btn_dice_1) ||
                isDiceSelected(dice_img_btn_dice_2) ||
                isDiceSelected(dice_img_btn_dice_3) ||
                isDiceSelected(dice_img_btn_dice_4) ||
                isDiceSelected(dice_img_btn_dice_5) ||
                isDiceSelected(dice_img_btn_dice_6)

    private fun refreshInterface() {
        if (isOneOrMoreDicesSelected())
            dice_btn_throw.text = getString(R.string.btn_throw)
        else
            dice_btn_throw.text = getString(R.string.btn_validate)
    }

    private fun resetInterface() {
        dice_img_btn_dice_1.clearColorFilter()
        dice_img_btn_dice_2.clearColorFilter()
        dice_img_btn_dice_3.clearColorFilter()
        dice_img_btn_dice_4.clearColorFilter()
        dice_img_btn_dice_5.clearColorFilter()
        dice_img_btn_dice_6.clearColorFilter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the board view model
        boardViewModel = ViewModelProvider(requireActivity()).get(BoardViewModel::class.java)

        boardViewModel.showBoardInterfaceLambda = {
            findNavController().navigate(
                DiceFragmentDirections.actionDiceFragmentToBoardFragment(
                    boardViewModel.players[0].hero
                )
            )
        }

        if (!args.isUser)
        {
            dice_btn_throw.visibility = View.INVISIBLE
            dice_btn_throw.isEnabled = false
        }
        else {
            dice_btn_throw.setOnClickListener {
                resetInterface()
                boardViewModel.board.resumeGame()
            }

            fun setDiceClick(dice: ImageView, index: Int) {
                dice.setOnClickListener {
                    if (boardViewModel.canSelectDices()) {
                        if (!isDiceSelected(dice))
                            dice.setColorFilter(
                                ContextCompat.getColor(requireContext(), R.color.reRollDice))
                        else
                            dice.clearColorFilter()
                        boardViewModel.board.selectedDices[index] = isDiceSelected(dice)
                        refreshInterface()
                    }
                }
            }

            setDiceClick(dice_img_btn_dice_1, 0)
            setDiceClick(dice_img_btn_dice_2, 1)
            setDiceClick(dice_img_btn_dice_3, 2)
            setDiceClick(dice_img_btn_dice_4, 3)
            setDiceClick(dice_img_btn_dice_5, 4)
            setDiceClick(dice_img_btn_dice_6, 5)
        }

        val currentPlayer = boardViewModel.board.getCurrentPlayer()
        dice_player_display_name_text_view.text = getString(
            R.string.user_display_name_format,
            currentPlayer.hero.getDisplayName(requireContext()),
            if (currentPlayer is User) getString(R.string.you) else ""
        )


        fun setDiceImage(button: ImageButton, diceFace: DiceFace?) =
            button.setImageDrawable(diceFace?.getImage(requireContext()))

        boardViewModel.showRollDicesAnimationLambda = { dices ->
            setDiceImage(dice_img_btn_dice_1, dices[0])
            setDiceImage(dice_img_btn_dice_2, dices[1])
            setDiceImage(dice_img_btn_dice_3, dices[2])
            setDiceImage(dice_img_btn_dice_4, dices[3])
            setDiceImage(dice_img_btn_dice_5, dices[4])
            setDiceImage(dice_img_btn_dice_6, dices[5])
        }


        fun linkPlayerToView(
            player: Player,
            board_player_display_name_text_view: TextView,
            board_player_stats_view: StatsView
        ) {
            board_player_stats_view.life = player.healthLiveData.value!!
            board_player_stats_view.victory = player.victoryPointsLiveData.value!!
            board_player_stats_view.stamina = player.energyLiveData.value!!
            board_player_display_name_text_view.text = getString(
                R.string.user_display_name_format,
                player.hero.getDisplayName(requireContext()),
                if (player is User) getString(R.string.you) else ""
            )
        }

        linkPlayerToView(
            boardViewModel.players[0],
            dice_fighter_top_left_text_view,
            dice_fighter_top_left_stats_view
        )
        linkPlayerToView(
            boardViewModel.players[1],
            dice_fighter_top_right_text_view,
            dice_fighter_top_right_stats_view
        )
        linkPlayerToView(
            boardViewModel.players[2],
            dice_fighter_bottom_right_text_view,
            dice_fighter_bottom_right_stats_view
        )
        linkPlayerToView(
            boardViewModel.players[3],
            dice_fighter_bottom_left_text_view,
            dice_fighter_bottom_left_stats_view
        )

        boardViewModel.throwDiceClickNameLiveData.observe(viewLifecycleOwner) { value ->
            dice_btn_throw.text = value
        }
    }
}