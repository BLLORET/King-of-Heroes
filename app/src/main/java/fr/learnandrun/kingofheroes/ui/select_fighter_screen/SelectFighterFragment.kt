package fr.learnandrun.kingofheroes.ui.select_fighter_screen


import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.business.Hero
import fr.learnandrun.kingofheroes.view_model.BoardViewModel
import fr.learnandrun.kingofheroes.view_model.SelectFighterViewModel
import fr.learnandrun.kingofheroes.tools.android.DefaultFragment
import kotlinx.android.synthetic.main.fragment_select_fighter.*

class SelectFighterFragment : DefaultFragment(R.layout.fragment_select_fighter) {

    private lateinit var selectFighterViewModel: SelectFighterViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectFighterViewModel = ViewModelProvider(this)
            .get(SelectFighterViewModel::class.java)

        selectFighterViewModel.currentIndex.observe(viewLifecycleOwner) { currentIndex ->

            select_fighter_left_image_view.setImageDrawable(
                if (currentIndex > 0)
                    Hero.atIndex(currentIndex - 1).getImage(requireContext())
                else
                    null
            )
            select_fighter_center_image_view.setImageDrawable(
                Hero.atIndex(currentIndex).getImage(requireContext())
            )
            select_fighter_right_image_view.setImageDrawable(
                if (currentIndex < Hero.values().size - 1)
                    Hero.atIndex(currentIndex + 1).getImage(requireContext())
                else
                    null
            )

            select_fighter_name_text_view.text =
                Hero.atIndex(currentIndex).getDisplayName(requireContext())


            select_fighter_previous_button.visibility = when {
                currentIndex > 0 -> View.VISIBLE
                else -> View.GONE
            }
            select_fighter_next_button.visibility = when {
                currentIndex < Hero.values().size - 1 -> View.VISIBLE
                else -> View.GONE
            }
        }

        select_fighter_previous_button.setOnClickListener {
            selectFighterViewModel.apply {
                currentIndex.value = currentIndex.value?.minus(1)
            }
        }

        select_fighter_next_button.setOnClickListener {
            selectFighterViewModel.apply {
                currentIndex.value = currentIndex.value?.plus(1)
            }
        }

        select_fighter_choose_button.setOnClickListener {
            val boardViewModel = ViewModelProvider(requireActivity()).get(BoardViewModel::class.java)
            boardViewModel.resetGame()
            findNavController().navigate(
                SelectFighterFragmentDirections.actionSelectFighterFragmentToBoardFragment(
                    Hero.atIndex(selectFighterViewModel.currentIndex.value)
                )
            )
        }
    }
}