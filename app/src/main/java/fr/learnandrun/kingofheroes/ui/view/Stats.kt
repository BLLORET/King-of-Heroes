package fr.learnandrun.kingofheroes.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import fr.learnandrun.kingofheroes.R
import kotlinx.android.synthetic.main.view_stats.view.*

class Stats(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    constructor(context: Context): this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet? = null): this(context, attrs, 0)

    val victory: MutableLiveData<Int>
    val life: MutableLiveData<Int>
    val stamina: MutableLiveData<Int>

    init {
        inflate(context, R.layout.view_stats, this)
        val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.Stats)

        victory = MutableLiveData(attributes.getInt(R.styleable.Stats_victory, 0))
        life = MutableLiveData(attributes.getInt(R.styleable.Stats_life, 0))
        stamina = MutableLiveData(attributes.getInt(R.styleable.Stats_stamina, 0))


        stats_victory_value_text_view.text =
            context.getString(R.string.max_victory_points, victory.value)
        stats_life_value_text_view.text =
            context.getString(R.string.max_life, life.value)
        stats_stamina_value_text_view.text =
            stamina.value.toString()

        attributes.recycle()
    }

    fun setupObservers(lifecycleOwner: LifecycleOwner) {
        victory.observe(lifecycleOwner) {
            stats_victory_value_text_view.text = context.getString(R.string.max_victory_points, it)
        }
        life.observe(lifecycleOwner) {
            stats_life_value_text_view.text = context.getString(R.string.max_life, it)
        }
        stamina.observe(lifecycleOwner) {
            stats_stamina_value_text_view.text = it.toString()
        }
    }
}