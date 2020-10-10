package fr.learnandrun.kingofheroes.tools.android

import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Extension method that simplify the use of toast (short toast)
 * @param msg the message to display
 */
fun Fragment.toast(msg: String) =
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()

/**
 * Extension method that simplify the use of toast (long toast)
 * @param msg the message to display
 */
fun Fragment.toastLong(msg: String) =
    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
