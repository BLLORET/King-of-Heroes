package fr.learnandrun.kingofheroes.ui.select_fighter_screen


import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.business.Hero
import fr.learnandrun.kingofheroes.model.SelectFighterViewModel
import fr.learnandrun.kingofheroes.tools.android.DefaultFragment
import fr.learnandrun.kingofheroes.tools.android.toast
import kotlinx.android.synthetic.main.fragment_select_fighter.*
import kotlin.math.max
import kotlin.math.min

class SelectFighterFragment : DefaultFragment(R.layout.fragment_select_fighter) {

    private lateinit var selectFighterViewModel: SelectFighterViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectFighterViewModel = ViewModelProvider(this).get(SelectFighterViewModel::class.java)

        selectFighterViewModel.currentIndex.observe(viewLifecycleOwner, Observer { currentIndex ->

            select_fighter_left_image_view.setImageDrawable(
                if (currentIndex > 0)
                    Hero.values()[currentIndex - 1].getImage(context!!)
                else null
            )
            select_fighter_center_image_view.setImageDrawable(Hero.values()[currentIndex].getImage(context!!))
            select_fighter_right_image_view.setImageDrawable(
                if (currentIndex < Hero.values().size - 1) Hero.values()[currentIndex + 1].getImage(context!!)
                else null
            )

            select_fighter_name_text_view.text = Hero.values()[currentIndex].getDisplayName(context!!)

            select_fighter_previous_button.background =
                if (currentIndex > 0) ContextCompat.getDrawable(context!!, R.drawable.arrow_left)
                else null

            select_fighter_next_button.background =
                if (currentIndex < Hero.values().size - 1) ContextCompat.getDrawable(context!!, R.drawable.arrow_right)
                else null
        })

        select_fighter_previous_button.setOnClickListener {
            selectFighterViewModel.currentIndex.postValue(
                max(0, selectFighterViewModel.currentIndex.value!!.minus(1))
            )
        }

        select_fighter_next_button.setOnClickListener {
            selectFighterViewModel.currentIndex.postValue(
                min(Hero.values().size - 1, selectFighterViewModel.currentIndex.value!!.plus(1))
            )
        }

        select_fighter_choose_button.setOnClickListener {
            toast("You have chosen " +
                    Hero.values()[selectFighterViewModel.currentIndex.value!!]
                        .getDisplayName(context!!)
            )
        }
    }
}