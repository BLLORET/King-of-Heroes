package fr.learnandrun.kingofheroes.business.shop

import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.business.Player

enum class AttackCard(
    val imageId: Int,
    val price: Int,
    val effect: (currentPlayer: Player, otherPlayers: List<Player>, isInsideCity: Boolean) -> Unit
) {
    AQUAMAN_WAVE(R.drawable.aquaman_attack, 8, { currentPlayer, otherPlayers, _ ->
        currentPlayer.victoryPoints += 3
        otherPlayers.forEach { it.health -=3 }
    }),
    BATMAN_BAT(R.drawable.batman_attack, 6, { currentPlayer, _ , _ ->
        currentPlayer.victoryPoints += 5
    }),
    CAPTAIN_SHIELD(R.drawable.captain_america_attack, 4, { currentPlayer, otherPlayers, _ ->
        currentPlayer.victoryPoints += 1
        otherPlayers.forEach { it.health -= 2 }
    }),
    GROOT_BRANCH(R.drawable.groot_attack, 2, { currentPlayer, _, isInsideCity ->
        if (isInsideCity) currentPlayer.health += 2
    }),
    HAWKEYE_ARROWS(R.drawable.hawkeye_attack, 5, { _, otherPlayers, _ ->
        otherPlayers.forEach { it.health -= 3 }
    }),
    THOR_BOLT(R.drawable.thor_attack, 10, { _, otherPlayers, _ ->
        otherPlayers.forEach { it.health -= 5 }
    })
}