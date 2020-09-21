package fr.learnandrun.kingofheroes.ui.dice_screen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.model.DiceViewModel
import fr.learnandrun.kingofheroes.tools.android.DefaultFragment

class DiceFragment : DefaultFragment(R.layout.fragment_dice) {

    private lateinit var diceViewModel: DiceViewModel
    private val args : DiceFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}