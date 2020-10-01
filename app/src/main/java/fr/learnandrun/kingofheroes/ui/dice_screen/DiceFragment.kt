package fr.learnandrun.kingofheroes.ui.dice_screen

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.business.User
import fr.learnandrun.kingofheroes.tools.android.DefaultFragment
import fr.learnandrun.kingofheroes.tools.android.setPushAndOnClick
import fr.learnandrun.kingofheroes.tools.android.toast
import fr.learnandrun.kingofheroes.view_model.DiceViewModel
import fr.learnandrun.kingofheroes.view_model.PartyViewModel
import kotlinx.android.synthetic.main.fragment_dice.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DiceFragment : DefaultFragment(R.layout.fragment_dice) {

    private val partyViewModel: PartyViewModel by sharedViewModel()
    private val diceViewModel: DiceViewModel by viewModel { parametersOf(partyViewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun drawableOf(id: Int) = ContextCompat.getDrawable(requireContext(), id)
        fun colorOf(id: Int) = ContextCompat.getColor(requireContext(), id)

        val dicesViews = listOf(
            dice_img_btn_dice_1,
            dice_img_btn_dice_2,
            dice_img_btn_dice_3,
            dice_img_btn_dice_4,
            dice_img_btn_dice_5,
            dice_img_btn_dice_6
        )

        val statsViews = listOf(
            dice_fighter_top_left_text_view to dice_fighter_top_left_stats_view,
            dice_fighter_top_right_text_view to dice_fighter_top_right_stats_view,
            dice_fighter_bottom_right_text_view to dice_fighter_bottom_right_stats_view,
            dice_fighter_bottom_left_text_view to dice_fighter_bottom_left_stats_view
        )

        statsViews.forEachIndexed { index, (displayText, statsView) ->
            val player = partyViewModel.board.players[index]
            player.healthLiveData.observe(viewLifecycleOwner) {
                statsView.life = it
            }
            player.victoryPointsLiveData.observe(viewLifecycleOwner) {
                statsView.victory = it
            }
            player.energyLiveData.observe(viewLifecycleOwner) {
                statsView.stamina = it
            }
            displayText.text = getString(R.string.user_display_name_format,
                getString(player.hero.displayNameId),
                if (player is User) getString(R.string.you) else ""
            )
        }

        fun updateThrowOrPass() {
            if (partyViewModel.dicePool.dices.all { dice -> dice.diceFace == null } ||
                partyViewModel.dicePool.dices.any { dice -> dice.isSelected })
                dice_btn_throw.setText(R.string.btn_throw)
            else
                dice_btn_throw.setText(R.string.btn_pass)
        }

        dicesViews.forEachIndexed { index, imageButton ->
            val dice = partyViewModel.dicePool.getDice(index)
            dice.diceFaceLive.observe(viewLifecycleOwner) {
                imageButton.setImageDrawable(it?.imageId?.let(::drawableOf))
            }
            dice.isSelectedLive.observe(viewLifecycleOwner) {
                if (it)
                    imageButton.setColorFilter(colorOf(R.color.reRollDice))
                else
                    imageButton.clearColorFilter()
                updateThrowOrPass()
            }
            partyViewModel.canSelectDicesLive.observe(viewLifecycleOwner) {
                imageButton.isClickable = it
            }
            imageButton.setPushAndOnClick {
                diceViewModel.selectDice(index)
            }
        }

        partyViewModel.diceRollRemainingLive.observe(viewLifecycleOwner) {
            dice_turn_text_view.text = getString(
                if (it > 1) R.string.dice_turns else R.string.dice_turn,
                it
            )
        }

        partyViewModel.currentPlayerLive.observe(viewLifecycleOwner) { player ->
            dice_player_display_name_text_view.text = getString(R.string.user_display_name_format,
                getString(player.hero.displayNameId),
                if (player is User) getString(R.string.you) else ""
            )
            dice_btn_throw.visibility = if (player is User) View.VISIBLE else View.INVISIBLE
            dice_btn_throw.isClickable = player is User
        }

        dice_btn_throw.setPushAndOnClick {
            diceViewModel.throwOrPass()
            updateThrowOrPass()
        }


        partyViewModel.toastEvent.subscribe(viewLifecycleOwner) {
            toast(getString(it))
        }

        partyViewModel.navigateEvent.subscribe(viewLifecycleOwner) {
            findNavController().navigate(it)
        }

        partyViewModel.fragmentLoaded()
    }

}