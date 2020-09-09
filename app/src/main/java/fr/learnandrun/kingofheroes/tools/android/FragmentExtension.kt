package fr.learnandrun.kingofheroes.tools.android

import android.widget.Toast
import androidx.fragment.app.Fragment
import fr.learnandrun.kingofheroes.R

/**
 * Extension method to simplify the replacement of the fragment
 * @param fragment the replacement fragment
 * @param addToBackStack if it has to be add to back stack or not
 * @return the replacement fragment
 */
fun <T : Fragment> Fragment.replaceFragment(
    fragment: T,
    addToBackStack: Boolean = true
): T {
    parentFragmentManager
        .beginTransaction()
        .apply { if (addToBackStack) this.addToBackStack(null) }
        .replace(
            R.id.main_container,
            fragment
        )
        .commit()
    return fragment
}

/**
 * Extension method to simplify the addition of the fragment
 * @param fragment the fragment to add
 * @param addToBackStack if it has to be add to back stack or not
 * @return the fragment added
 */
fun <T : Fragment> Fragment.addFragment(
    fragment: T,
    addToBackStack: Boolean = true
): T {
    parentFragmentManager
        .beginTransaction()
        .apply { if (addToBackStack) this.addToBackStack(null) }
        .add(
            R.id.main_container,
            fragment
        )
        .commit()
    return fragment
}

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

/**
 * Extension method that clear the back stack of fragment
 */
fun Fragment.clearBackStack() {
    while (parentFragmentManager.backStackEntryCount > 0)
        parentFragmentManager.popBackStackImmediate()
}

/**
 * Extension method that pop one time the back stack
 */
fun Fragment.back() {
    if (parentFragmentManager.backStackEntryCount > 0)
        parentFragmentManager.popBackStack()
}