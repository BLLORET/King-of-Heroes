package fr.learnandrun.kingofheroes.ui.shop_screen

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.business.User
import fr.learnandrun.kingofheroes.view_model.ShopViewModel
import fr.learnandrun.kingofheroes.tools.android.DefaultFragment
import fr.learnandrun.kingofheroes.tools.android.setPushAndOnClick
import fr.learnandrun.kingofheroes.tools.android.toast
import fr.learnandrun.kingofheroes.view_model.PartyViewModel
import kotlinx.android.synthetic.main.fragment_dice.*
import kotlinx.android.synthetic.main.fragment_shop.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ShopFragment : DefaultFragment(R.layout.fragment_shop) {

    private val partyViewModel: PartyViewModel by sharedViewModel()
    private val shopViewModel: ShopViewModel by viewModel { parametersOf(partyViewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun drawableOf(id: Int) = ContextCompat.getDrawable(requireContext(), id)
        fun colorOf(id: Int) = ContextCompat.getColor(requireContext(), id)

        val attacksViews = listOf(
            shop_first_attack_btn,
            shop_secund_attack_btn,
            shop_third_attack_btn
        )

        fun updateBuyOrPass() {
            if (partyViewModel.shop.cards.any { card -> card.isSelected })
                shop_pass_btn.setText(R.string.btn_buy)
            else
                shop_pass_btn.setText(R.string.btn_pass)
        }

        attacksViews.forEachIndexed { index, imageButton ->
            val card = partyViewModel.shop.getCard(index)

            card.cardLive.observe(viewLifecycleOwner) {
                imageButton.setImageDrawable(it?.imageId?.let(::drawableOf))
            }

            card.isSelectedLive.observe(viewLifecycleOwner) {
                if (it)
                    imageButton.setColorFilter(colorOf(R.color.reRollDice))
                else
                    imageButton.clearColorFilter()
                updateBuyOrPass()
            }

            imageButton.setPushAndOnClick {
                shopViewModel.trySelectCard(index)
            }
        }

        partyViewModel.apply {
            currentPlayerLive.observe(viewLifecycleOwner) { player ->
                shop_pass_btn.visibility = if (player is User) View.VISIBLE else View.INVISIBLE
                shop_pass_btn.isClickable = player is User
            }

            currentPlayer.energyLiveData.observe(viewLifecycleOwner) {
                fragment_shop_stamina_text_value.text = it.toString()
            }

            toastEvent.subscribe(viewLifecycleOwner) {
                toast(getString(it))
            }

            navigateEvent.subscribe(viewLifecycleOwner) {
                findNavController().navigate(it)
            }
        }

        shopViewModel.apply {
            shop_pass_btn.setPushAndOnClick {
                resumeGame()
            }
        }

        partyViewModel.fragmentLoaded()
    }

}