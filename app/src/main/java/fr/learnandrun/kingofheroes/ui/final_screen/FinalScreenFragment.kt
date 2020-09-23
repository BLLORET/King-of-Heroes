package fr.learnandrun.kingofheroes.ui.final_screen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.tools.android.DefaultFragment
import kotlinx.android.synthetic.main.fragment_final_screen.*

class FinalScreenFragment: DefaultFragment(R.layout.fragment_final_screen) {

    private val args : FinalScreenFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        final_screen_title_text_view.text = if (args.isUser) getString(R.string.win_string) else getString(R.string.lose_string)
        final_screen_hero_image_view.setImageDrawable(args.winner.getImage(requireContext()))
        final_screen_replay_button.setOnClickListener {
            findNavController().navigate(FinalScreenFragmentDirections.actionFinalScreenFragmentToSelectFighterFragment())
        }
    }
}