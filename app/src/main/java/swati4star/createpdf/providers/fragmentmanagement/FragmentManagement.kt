package swati4star.createpdf.providers.fragmentmanagement

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.navigation.NavigationView
import swati4star.createpdf.R
import swati4star.createpdf.activity.WelcomeActivity
import swati4star.createpdf.fragment.AboutUsFragment
import swati4star.createpdf.fragment.AddImagesFragment
import swati4star.createpdf.fragment.AddTextFragment
import swati4star.createpdf.fragment.ExceltoPdfFragment
import swati4star.createpdf.fragment.ExtractTextFragment
import swati4star.createpdf.fragment.FAQFragment
import swati4star.createpdf.fragment.FavouritesFragment
import swati4star.createpdf.fragment.HistoryFragment
import swati4star.createpdf.fragment.HomeFragment
import swati4star.createpdf.fragment.ImageToPdfFragment
import swati4star.createpdf.fragment.InvertPdfFragment
import swati4star.createpdf.fragment.MergeFilesFragment
import swati4star.createpdf.fragment.PdfToImageFragment
import swati4star.createpdf.fragment.QrBarcodeScanFragment
import swati4star.createpdf.fragment.RemoveDuplicatePagesFragment
import swati4star.createpdf.fragment.RemovePagesFragment
import swati4star.createpdf.fragment.SettingsFragment
import swati4star.createpdf.fragment.SplitFilesFragment
import swati4star.createpdf.fragment.ViewFilesFragment
import swati4star.createpdf.fragment.ZipToPdfFragment
import swati4star.createpdf.fragment.texttopdf.TextToPdfFragment
import swati4star.createpdf.util.Constants
import swati4star.createpdf.util.FeedbackUtils
import swati4star.createpdf.util.FragmentUtils
import java.util.Objects

/**
 * This is a fragment service that manages the fragments
 * mainly for the MainActivity.
 */
class FragmentManagement(private val context: FragmentActivity, private val navigationView: NavigationView) : IFragmentManagement {
    private val mFeedbackUtils: FeedbackUtils = FeedbackUtils(context)
    private val mFragmentUtils: FragmentUtils = FragmentUtils(context)
    private var mDoubleBackToExitPressedOnce = false

    override fun favouritesFragmentOption() {
        val fragmentManager = context.supportFragmentManager
        val currFragment = fragmentManager.findFragmentById(R.id.content)
        val fragment: Fragment = FavouritesFragment()
        val transaction = fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
        if (currFragment !is HomeFragment) {
            transaction.addToBackStack(mFragmentUtils.getFragmentName(currFragment))
        }
        transaction.commit()
    }

    override fun checkForAppShortcutClicked(): Fragment {
        var fragment: Fragment = HomeFragment()
        context.intent.action?.let {
            when (it) {
                Constants.ACTION_SELECT_IMAGES -> {
                    fragment = ImageToPdfFragment()
                    val bundle = Bundle()
                    bundle.putBoolean(Constants.OPEN_SELECT_IMAGES, true)
                    fragment.arguments = bundle
                }
                Constants.ACTION_VIEW_FILES -> {
                    fragment = ViewFilesFragment()
                    setNavigationViewSelection(R.id.nav_gallery)
                }
                Constants.ACTION_TEXT_TO_PDF -> {
                    fragment = TextToPdfFragment()
                    setNavigationViewSelection(R.id.nav_text_to_pdf)
                }
                Constants.ACTION_MERGE_PDF -> {
                    fragment = MergeFilesFragment()
                    setNavigationViewSelection(R.id.nav_merge)
                }
                else -> fragment = HomeFragment() // Set default fragment
            }
        }
        if (areImagesReceived()) fragment = ImageToPdfFragment()

        context.supportFragmentManager.beginTransaction().replace(R.id.content, fragment).commit()

        return fragment
    }

    override fun handleBackPressed(): Boolean {
        val currentFragment = context.supportFragmentManager.findFragmentById(R.id.content)
        if (currentFragment is HomeFragment) {
            return checkDoubleBackPress()
        } else {
            if (mFragmentUtils.handleFragmentBottomSheetBehavior(currentFragment)) return false
        }
        handleBackStackEntry()
        return false
    }

    override fun handleNavigationItemSelected(itemId: Int): Boolean {
        var fragment: Fragment? = null
        val fragmentManager = context.supportFragmentManager
        val bundle = Bundle()

        when (itemId) {
            R.id.nav_home -> fragment = HomeFragment()
            R.id.nav_camera -> fragment = ImageToPdfFragment()
            R.id.nav_qrcode -> fragment = QrBarcodeScanFragment()
            R.id.nav_gallery -> fragment = ViewFilesFragment()
            R.id.nav_merge -> fragment = MergeFilesFragment()
            R.id.nav_split -> fragment = SplitFilesFragment()
            R.id.nav_text_to_pdf -> fragment = TextToPdfFragment()
            R.id.nav_history -> fragment = HistoryFragment()
            R.id.nav_add_text -> fragment = AddTextFragment()
            R.id.nav_add_password -> {
                fragment = RemovePagesFragment()
                bundle.putString(Constants.BUNDLE_DATA, Constants.ADD_PWD)
                fragment.setArguments(bundle)
            }
            R.id.nav_remove_password -> {
                fragment = RemovePagesFragment()
                bundle.putString(Constants.BUNDLE_DATA, Constants.REMOVE_PWd)
                fragment.setArguments(bundle)
            }
            R.id.nav_share -> mFeedbackUtils.shareApplication()
            R.id.nav_about -> fragment = AboutUsFragment()
            R.id.nav_settings -> fragment = SettingsFragment()
            R.id.nav_extract_images -> {
                fragment = PdfToImageFragment()
                bundle.putString(Constants.BUNDLE_DATA, Constants.EXTRACT_IMAGES)
                fragment.setArguments(bundle)
            }
            R.id.nav_pdf_to_images -> {
                fragment = PdfToImageFragment()
                bundle.putString(Constants.BUNDLE_DATA, Constants.PDF_TO_IMAGES)
                fragment.setArguments(bundle)
            }
            R.id.nav_excel_to_pdf -> fragment = ExceltoPdfFragment()
            R.id.nav_remove_pages -> {
                fragment = RemovePagesFragment()
                bundle.putString(Constants.BUNDLE_DATA, Constants.REMOVE_PAGES)
                fragment.setArguments(bundle)
            }
            R.id.nav_rearrange_pages -> {
                fragment = RemovePagesFragment()
                bundle.putString(Constants.BUNDLE_DATA, Constants.REORDER_PAGES)
                fragment.setArguments(bundle)
            }
            R.id.nav_compress_pdf -> {
                fragment = RemovePagesFragment()
                bundle.putString(Constants.BUNDLE_DATA, Constants.COMPRESS_PDF)
                fragment.setArguments(bundle)
            }
            R.id.nav_add_images -> {
                fragment = AddImagesFragment()
                bundle.putString(Constants.BUNDLE_DATA, Constants.ADD_IMAGES)
                fragment.setArguments(bundle)
            }
            R.id.nav_help -> {
                val intent = Intent(context, WelcomeActivity::class.java)
                intent.putExtra(Constants.SHOW_WELCOME_ACT, true)
                context.startActivity(intent)
            }
            R.id.nav_remove_duplicate_pages -> fragment = RemoveDuplicatePagesFragment()
            R.id.nav_invert_pdf -> fragment = InvertPdfFragment()
            R.id.nav_add_watermark -> {
                fragment = ViewFilesFragment()
                bundle.putInt(Constants.BUNDLE_DATA, Constants.ADD_WATERMARK)
                fragment.setArguments(bundle)
            }
            R.id.nav_zip_to_pdf -> fragment = ZipToPdfFragment()
            R.id.nav_rateus -> mFeedbackUtils.openWebPage("https://play.google.com/store/apps/details?id=swati4star.createpdf")
            R.id.nav_rotate_pages -> {
                fragment = ViewFilesFragment()
                bundle.putInt(Constants.BUNDLE_DATA, Constants.ROTATE_PAGES)
                fragment.setArguments(bundle)
            }
            R.id.nav_text_extract -> fragment = ExtractTextFragment()
            R.id.nav_faq -> fragment = FAQFragment()
        }

        try {
            fragment?.let { fragmentManager.beginTransaction().replace(R.id.content, it).commit() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // if help or share is clicked then return false, as we don't want
        // them to be selected
        return itemId != R.id.nav_share && itemId != R.id.nav_help
    }

    /**
     * Closes the app only when double clicked
     */
    private fun checkDoubleBackPress(): Boolean {
        if (mDoubleBackToExitPressedOnce) {
            return true
        }
        mDoubleBackToExitPressedOnce = true
        Toast.makeText(context, R.string.confirm_exit_message, Toast.LENGTH_SHORT).show()
        return false
    }

    /**
     * Back stack count will be 1 when we open a item from favourite menu
     * on clicking back, return back to fav menu and change title
     */
    private fun handleBackStackEntry() {
        val fragmentManager = context.supportFragmentManager
        val count = fragmentManager.backStackEntryCount
        if (count > 0) {
            val s = fragmentManager.getBackStackEntryAt(count - 1).name
            context.title = s
            fragmentManager.popBackStack()
        } else {
            val fragment: Fragment = HomeFragment()
            fragmentManager.beginTransaction().replace(R.id.content, fragment).commit()
            context.setTitle(R.string.app_name)
            setNavigationViewSelection(R.id.nav_home)
        }
    }

    private fun areImagesReceived(): Boolean {
        val intent = context.intent
        val type = intent.type
        return type != null && type.startsWith("image/")
    }

    private fun setNavigationViewSelection(id: Int) {
        navigationView.setCheckedItem(id)
    }
}
