package fr.learnandrun.kingofheroes.ui.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import fr.learnandrun.kingofheroes.R
import kotlinx.android.synthetic.main.leave_city_alert.view.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LeaveCityAlertView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    constructor(context: Context): this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    private var continuation: Continuation<Boolean>? = null

    init {
        inflate(context, R.layout.leave_city_alert, this)
    }

    suspend fun suspendShow(): Boolean {
        val alert = AlertDialog.Builder(context).setView(this).create()
        leave_city_alert_stay_button.setOnClickListener {
            Toast.makeText(context, "Stay", Toast.LENGTH_SHORT).show()
            alert.dismiss()
        }
        leave_city_alert_leave_button.setOnClickListener {
            Toast.makeText(context, "Leave", Toast.LENGTH_SHORT).show()
            continuation?.resume(true)
            continuation = null
            alert.dismiss()
        }
        alert.setOnDismissListener {
            continuation?.resume(false)
            continuation = null
        }
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.show()
        return suspendCoroutine {
            continuation = it
        }
    }

}