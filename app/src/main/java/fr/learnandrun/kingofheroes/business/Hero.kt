package fr.learnandrun.kingofheroes.business

import android.content.Context
import androidx.core.content.ContextCompat
import fr.learnandrun.kingofheroes.R

enum class Hero(
    private val displayNameId: Int,
    private val imageId: Int
) {
    ANT_MAN(R.string.ant_man, R.drawable.ant_man),
    AQUAMAN(R.string.aquaman, R.drawable.aquaman),
    BATMAN(R.string.batman, R.drawable.batman),
    BLACK_PANTHER(R.string.black_panther, R.drawable.black_panther),
    BLACK_WIDOW(R.string.black_widow, R.drawable.black_widow),
    CAPTAIN_AMERICA(R.string.captain_america, R.drawable.captain_america),
    CAPTAIN_MARVEL(R.string.captain_marvel, R.drawable.captain_marvel),
    DEADPOOL(R.string.deadpool, R.drawable.deadpool),
    DR_STRANGE(R.string.dr_strange, R.drawable.dr_strange),
    GROOT(R.string.groot, R.drawable.groot),
    HAWKEYE(R.string.hawkeye, R.drawable.hawkeye),
    HULK(R.string.hulk, R.drawable.hulk),
    IRON_MAN(R.string.iron_man, R.drawable.iron_man),
    JOKER(R.string.joker, R.drawable.joker),
    ROCKET_RACCOON(R.string.rocket_raccoon, R.drawable.rocket_raccoon),
    SPIDER_MAN(R.string.spider_man, R.drawable.spider_man),
    STAR_LORD(R.string.star_lord, R.drawable.star_lord),
    SUPERMAN(R.string.superman, R.drawable.superman),
    THOR(R.string.thor, R.drawable.thor),
    WONDER_WOMAN(R.string.wonder_woman, R.drawable.wonder_woman);

    fun getDisplayName(context: Context) = context.getString(displayNameId)
    fun getImage(context: Context) = ContextCompat.getDrawable(context, imageId)
}