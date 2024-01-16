package swati4star.createpdf.customviews

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import swati4star.createpdf.R

class MyCardView : LinearLayout {

    var icon: ImageView? = null
    var text: TextView? = null

    /**
     * Initiates custom card view
     *
     * @param context context
     */
    constructor(context: Context) : super(context) {
        init()
    }

    /**
     * Initiates custom card view with attributes
     *
     * @param context context
     * @param attrs   attributes set
     */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    /**
     * Initiates custom card view with attributes set
     *
     * @param context      context
     * @param attrs        attributes set
     * @param defStyleAttr attribute style
     */
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    /**
     * Initialize card view with no attribute sets
     */
    private fun init() {
        inflate(context, R.layout.item_view_enhancement_option, this)
        text = findViewById(R.id.option_name)
        icon = findViewById(R.id.option_image)
    }

    /**
     * Initialize card view with attributes received
     *
     * @param attrs attribute set
     */
    private fun init(attrs: AttributeSet?) {
        inflate(context, R.layout.item_view_enhancement_option, this)

        val a = context.obtainStyledAttributes(attrs, R.styleable.MyCardView)

        text = findViewById(R.id.option_name)
        icon = findViewById(R.id.option_image)

        text?.text = a.getString(R.styleable.MyCardView_option_text)
        val drawable = a.getDrawable(R.styleable.MyCardView_option_icon)
        icon?.setImageDrawable(drawable)

        a.recycle()
    }
}
