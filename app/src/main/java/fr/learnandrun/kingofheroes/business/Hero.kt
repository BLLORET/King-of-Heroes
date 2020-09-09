package fr.learnandrun.kingofheroes.business

import android.content.Context
import fr.learnandrun.kingofheroes.R

enum class Hero(
    private val displayNameId: Int,
    val imageId: Int
) {
    BLACK_PANTHER(R.string.black_panther, R.drawable.black_panther),
    BLACK_WIDOW(R.string.black_widow, R.drawable.black_widow),
    DR_STRANGE(R.string.dr_strange, R.drawable.dr_strange),
    STAR_LORD(R.string.star_lord, R.drawable.star_lord),
    THOR(R.string.thor, R.drawable.thor);

    fun getDisplayName(context: Context) = context.getString(displayNameId)
}