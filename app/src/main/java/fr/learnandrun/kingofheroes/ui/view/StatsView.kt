package fr.learnandrun.kingofheroes.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import fr.learnandrun.kingofheroes.R
import kotlinx.android.synthetic.main.view_stats.view.*

class StatsView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    constructor(context: Context): this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet? = null): this(context, attrs, 0)

    private data class Stats(
        var victory: Int,
        var life: Int,
        var stamina: Int
    )

    private val stats: Stats

    init {
        inflate(context, R.layout.view_stats, this)
        val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.StatsView)

        stats = Stats(
            attributes.getInt(R.styleable.StatsView_victory, 0),
            attributes.getInt(R.styleable.StatsView_life, 10),
            attributes.getInt(R.styleable.StatsView_stamina, 0)
        )

        stats_victory_value_text_view.text =
            context.getString(R.string.max_victory_points, stats.victory)
        stats_life_value_text_view.text =
            context.getString(R.string.max_life, stats.life)
        stats_stamina_value_text_view.text =
            stats.stamina.toString()

        attributes.recycle()
    }

    var victory: Int
        get() = stats.victory
        set(value) {
            stats.victory = value
            stats_victory_value_text_view.text =
                context.getString(R.string.max_victory_points, value)
        }

    var life: Int
        get() = stats.life
        set(value) {
            stats.life = value
            stats_life_value_text_view.text = context.getString(R.string.max_life, value)
        }

    var stamina: Int
        get() = stats.stamina
        set(value) {
            stats.stamina = value
            stats_stamina_value_text_view.text = value.toString()
        }

}