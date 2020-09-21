package fr.learnandrun.kingofheroes.ui.shop_screen

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.business.Attack
import fr.learnandrun.kingofheroes.business.Player
import fr.learnandrun.kingofheroes.model.ShopViewModel
import fr.learnandrun.kingofheroes.tools.android.DefaultFragment
import kotlinx.android.synthetic.main.fragment_shop.*

class ShopFragment(private val player: Player) : DefaultFragment(R.layout.fragment_shop) {
    private lateinit var shopViewModel: ShopViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO : replace this by fight context
        shopViewModel = ViewModelProvider(this).get(ShopViewModel::class.java)

        initImageButton(shop_first_attack_btn, 0)
        initImageButton(shop_secund_attack_btn, 1)
        initImageButton(shop_third_attack_btn, 2)

        shop_pass_btn.setOnClickListener {
            redirectToFightScreen()
        }
    }

    /**
     * Initialise the given image button with the good drawable and set its click listener
     */
    private fun initImageButton(imageButton: ImageButton, attackIndex: Int) {
        imageButton.setImageDrawable(
            shopViewModel.attacks.value!![attackIndex].getImage(requireContext())
        )

        imageButton.setOnClickListener {
            // Return if the player has not enough money
            if (player.energyLiveData.value!! < shopViewModel.attacks.value!![attackIndex].price)
                return@setOnClickListener
            // TODO : Apply the effect before redirect

            // Buy the attack
            player.decreaseEnergy(shopViewModel.attacks.value!![attackIndex].price)
            // Get current attacks
            val newAttacks: MutableList<Attack>? = shopViewModel.attacks.value?.toMutableList()

            // Get available attacks in shop
            val allPossibleAttacks = Attack.values().toMutableSet()
            shopViewModel.attacks.value?.forEach {
                if (shopViewModel.attacks.value!![attackIndex] != it)
                    allPossibleAttacks.remove(it)
            }

            // Set new attack to display
            newAttacks?.set(attackIndex, allPossibleAttacks.random())
            shopViewModel.attacks.postValue(newAttacks!!)

            redirectToFightScreen()
        }
    }

    /**
     * Redirect to the fight screen
     */
    private fun redirectToFightScreen() {
        // TODO : implement the redirection
    }

}