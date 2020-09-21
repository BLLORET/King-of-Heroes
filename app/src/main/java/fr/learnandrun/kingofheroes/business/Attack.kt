package fr.learnandrun.kingofheroes.business

import android.content.Context
import androidx.core.content.ContextCompat
import fr.learnandrun.kingofheroes.R

enum class Attack(
    private val imageId: Int
) {
    AQUAMAN_WAVE(R.drawable.aquaman_attack),
    BATMAN_BAT(R.drawable.batman_attack),
    CAPTAIN_SHIELD(R.drawable.captain_america_attack),
    GROOT_BRANCH(R.drawable.groot_attack),
    HAWKEYE_ARROWS(R.drawable.hawkeye_attack),
    THOR_BOLT(R.drawable.thor_attack);

    fun getImage(context: Context) = ContextCompat.getDrawable(context, imageId)
}