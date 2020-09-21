package fr.learnandrun.kingofheroes.ui.final_screen

import android.os.Bundle
import android.view.View
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.business.Hero
import fr.learnandrun.kingofheroes.tools.android.DefaultFragment
import kotlinx.android.synthetic.main.fragment_final_screen.*

class FinalScreenFragment(private val win: Boolean, private val hero: Hero): DefaultFragment(R.layout.fragment_final_screen) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        final_screen_title_text_view.text = if (win) R.string.win_string.toString() else R.string.lose_string.toString()
        final_screen_hero_image_view.setImageDrawable(hero.getImage(requireContext()))
        final_screen_replay_button.setOnClickListener {
            // TODO : redirect to select fighter screen
        }
    }
}