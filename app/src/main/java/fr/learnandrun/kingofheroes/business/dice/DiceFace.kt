package fr.learnandrun.kingofheroes.business.dice

import fr.learnandrun.kingofheroes.R

enum class DiceFace(
    private val imageId: Int
) {
    ONE(R.drawable.one),
    TWO(R.drawable.two),
    THREE(R.drawable.three),
    LIGHTNING(R.drawable.bolt),
    SLAP(R.drawable.slap),
    HEART(R.drawable.heart)
}