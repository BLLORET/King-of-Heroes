package fr.learnandrun.kingofheroes.tools.android

import android.view.View
import com.thekhaeng.pushdownanim.PushDown
import com.thekhaeng.pushdownanim.PushDownAnim

fun View.setPushAndOnClick(listener: (View) -> Unit): PushDown =
    PushDownAnim.setPushDownAnimTo(this).setOnClickListener(listener)