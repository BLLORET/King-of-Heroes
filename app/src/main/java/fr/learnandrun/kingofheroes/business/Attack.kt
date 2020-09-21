package fr.learnandrun.kingofheroes.business

import android.content.Context
import androidx.core.content.ContextCompat
import fr.learnandrun.kingofheroes.R

enum class Attack(
    private val imageId: Int,
    val price: Int
) {
    AQUAMAN_WAVE(R.drawable.aquaman_attack, 8),
    BATMAN_BAT(R.drawable.batman_attack, 6),
    CAPTAIN_SHIELD(R.drawable.captain_america_attack, 4),
    GROOT_BRANCH(R.drawable.groot_attack, 2),
    HAWKEYE_ARROWS(R.drawable.hawkeye_attack, 5),
    THOR_BOLT(R.drawable.thor_attack, 3);

    fun getImage(context: Context) = ContextCompat.getDrawable(context, imageId)
}