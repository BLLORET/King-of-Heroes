package fr.learnandrun.kingofheroes.ui.dice_screen

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.model.BoardViewModel
import fr.learnandrun.kingofheroes.tools.android.DefaultFragment
import kotlinx.android.synthetic.main.fragment_dice.*

class DiceFragment : DefaultFragment(R.layout.fragment_dice) {

    private lateinit var boardViewModel: BoardViewModel
    private val args : DiceFragmentArgs by navArgs()

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

        if (!args.isUser) dice_btn_throw.visibility = View.GONE

        dice_btn_throw.setOnClickListener {
            boardViewModel.board.resumeGame()
        }

        boardViewModel.board.dicesLiveData.observe(viewLifecycleOwner) {
            it?.let { dices ->
                if (dices.size == 6) {
                    dice_img_btn_dice_1.setImageDrawable(dices[0]?.getImage(requireContext()))
                    dice_img_btn_dice_2.setImageDrawable(dices[1]?.getImage(requireContext()))
                    dice_img_btn_dice_3.setImageDrawable(dices[2]?.getImage(requireContext()))
                    dice_img_btn_dice_4.setImageDrawable(dices[3]?.getImage(requireContext()))
                    dice_img_btn_dice_5.setImageDrawable(dices[4]?.getImage(requireContext()))
                    dice_img_btn_dice_6.setImageDrawable(dices[5]?.getImage(requireContext()))
                }
                else {
                    dice_img_btn_dice_1.setImageDrawable(null)
                    dice_img_btn_dice_2.setImageDrawable(null)
                    dice_img_btn_dice_3.setImageDrawable(null)
                    dice_img_btn_dice_4.setImageDrawable(null)
                    dice_img_btn_dice_5.setImageDrawable(null)
                    dice_img_btn_dice_6.setImageDrawable(null)
                }
            }
        }
    }
}