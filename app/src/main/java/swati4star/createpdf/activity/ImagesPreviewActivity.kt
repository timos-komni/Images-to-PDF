package swati4star.createpdf.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import butterknife.ButterKnife
import com.eftimoff.viewpagertransformers.DepthPageTransformer
import swati4star.createpdf.R
import swati4star.createpdf.adapter.PreviewAdapter
import swati4star.createpdf.util.Constants
import swati4star.createpdf.util.ThemeUtils

class ImagesPreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeUtils.getInstance().setThemeApp(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_images)

        ButterKnife.bind(this)
        // Extract imagesArrayList uri array from the intent
        val imagesArrayList = intent.getStringArrayListExtra(Constants.PREVIEW_IMAGES)

        val viewPager = findViewById<ViewPager>(R.id.viewpager)
        val previewAdapter = PreviewAdapter(this, imagesArrayList)
        viewPager.adapter = previewAdapter
        viewPager.setPageTransformer(true, DepthPageTransformer())
        supportActionBar?.hide()
    }

    companion object {
        /**
         * get start intent for this activity
         *
         * @param context context to start activity from
         * @param uris    extra images uri
         * @return start intent
         */
        @JvmStatic
        fun getStartIntent(context: Context, uris: ArrayList<String?>?): Intent {
            val intent = Intent(context, ImagesPreviewActivity::class.java)
            intent.putExtra(Constants.PREVIEW_IMAGES, uris)
            return intent
        }
    }
}