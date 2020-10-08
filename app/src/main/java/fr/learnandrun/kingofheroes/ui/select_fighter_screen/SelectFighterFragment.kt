package fr.learnandrun.kingofheroes.ui.select_fighter_screen


import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.view_model.PartyViewModel
import fr.learnandrun.kingofheroes.tools.android.DefaultFragment
import fr.learnandrun.kingofheroes.tools.android.setPushAndOnClick
import fr.learnandrun.kingofheroes.tools.android.toast
import fr.learnandrun.kingofheroes.view_model.SelectFighterViewModel
import kotlinx.android.synthetic.main.fragment_select_fighter.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SelectFighterFragment : DefaultFragment(R.layout.fragment_select_fighter) {

    private val partyViewModel: PartyViewModel by sharedViewModel()
    private val selectFighterViewModel: SelectFighterViewModel by viewModel { parametersOf(partyViewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun drawableOf(id: Int) = ContextCompat.getDrawable(requireContext(), id)

        selectFighterViewModel.backArrowIsVisibleLive.observe(viewLifecycleOwner) {
            select_fighter_previous_button.visibility = it
        }

        selectFighterViewModel.nextArrowIsVisibleLive.observe(viewLifecycleOwner) {
            select_fighter_next_button.visibility = it
        }

        selectFighterViewModel.imageBackIdLive.observe(viewLifecycleOwner) {
            select_fighter_left_image_view.setImageDrawable(it?.let(::drawableOf))
        }

        selectFighterViewModel.imageCurrentIdLive.observe(viewLifecycleOwner) {
            select_fighter_center_image_view.setImageDrawable(it?.let(::drawableOf))
        }

        selectFighterViewModel.imageNextIdLive.observe(viewLifecycleOwner) {
            select_fighter_right_image_view.setImageDrawable(it?.let(::drawableOf))
        }

        selectFighterViewModel.heroNameLive.observe(viewLifecycleOwner) {
            select_fighter_name_text_view.text = getString(it)
        }

        select_fighter_next_button.setPushAndOnClick {
            selectFighterViewModel.next()
        }

        select_fighter_previous_button.setPushAndOnClick {
            selectFighterViewModel.back()
        }

        select_fighter_choose_button.setPushAndOnClick {
            selectFighterViewModel.choose()
        }

        partyViewModel.navigateEvent.subscribe(viewLifecycleOwner) {
            findNavController().navigate(it)
        }

    }
}