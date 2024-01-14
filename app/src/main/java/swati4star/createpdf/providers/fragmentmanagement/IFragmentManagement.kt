package swati4star.createpdf.providers.fragmentmanagement

import androidx.fragment.app.Fragment

internal interface IFragmentManagement {
    /**
     * Begins a fragment transaction for the favourite fragment.
     */
    fun favouritesFragmentOption()

    /**
     * Sets a fragment based on app shortcut selected, otherwise default
     *
     * @return - instance of current fragment
     */
    fun checkForAppShortcutClicked(): Fragment

    /**
     * Handles all back button actions.
     * It returns a boolean that flags if the app should exit or not.
     * If user clicked twice then it returns true. Otherwise it returns false.
     *
     * @return A should exit flag.
     */
    fun handleBackPressed(): Boolean

    /**
     * Handles the fragment transaction for the selected
     * navigation item.
     *
     * @param itemId The selected item id.
     * @return true when handled
     */
    fun handleNavigationItemSelected(itemId: Int): Boolean
}
