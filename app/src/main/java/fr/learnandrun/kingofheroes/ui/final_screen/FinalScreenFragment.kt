package fr.learnandrun.kingofheroes.ui.final_screen

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.business.User
import fr.learnandrun.kingofheroes.tools.android.DefaultFragment
import fr.learnandrun.kingofheroes.tools.android.toast
import fr.learnandrun.kingofheroes.view_model.PartyViewModel
import kotlinx.android.synthetic.main.fragment_final_screen.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FinalScreenFragment: DefaultFragment(R.layout.fragment_final_screen) {

    private val partyViewModel: PartyViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun drawableOf(id: Int) = ContextCompat.getDrawable(requireContext(), id)

        final_screen_title_text_view.text =
            if (partyViewModel.currentPlayer is User)
                getString(R.string.win_string)
            else
                getString(R.string.lose_string)

        final_screen_hero_image_view.setImageDrawable(
            drawableOf(partyViewModel.currentPlayer.hero.imageId)
        )

        final_screen_replay_button.setOnClickListener {
            findNavController().navigate(
                FinalScreenFragmentDirections.actionFinalScreenFragmentToSelectFighterFragment())
        }

        partyViewModel.navigateEvent.subscribe(viewLifecycleOwner) {
            findNavController().navigate(it)
        }

        partyViewModel.fragmentLoaded()
    }

}