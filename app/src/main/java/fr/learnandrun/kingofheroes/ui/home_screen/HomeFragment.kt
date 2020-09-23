package fr.learnandrun.kingofheroes.ui.home_screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.tools.android.DefaultFragment
import fr.learnandrun.kingofheroes.ui.view.LeaveCityAlertView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


class HomeFragment : DefaultFragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        home_rules_button.setOnClickListener {
            val url = getString(R.string.rules_url)
            val implicitIntent = Intent(Intent.ACTION_VIEW)
            implicitIntent.data = Uri.parse(url)
            startActivity(implicitIntent)
        }

        home_play_button.setOnClickListener {
            findNavController().navigate(R.id.selectFighterFragment)
        }
    }

}