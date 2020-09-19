package fr.learnandrun.kingofheroes.ui.home_screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.tools.android.DefaultFragment
import fr.learnandrun.kingofheroes.tools.android.replaceFragment
import fr.learnandrun.kingofheroes.ui.select_fighter_screen.SelectFighterFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : DefaultFragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        home_rules_button.setOnClickListener {
            val url = "https://www.regledujeu.fr/king-of-tokyo/"
            val implicitIntent = Intent(Intent.ACTION_VIEW)
            implicitIntent.data = Uri.parse(url)
            startActivity(implicitIntent)
        }

        home_play_button.setOnClickListener {
            this.replaceFragment(SelectFighterFragment())
        }
    }

}