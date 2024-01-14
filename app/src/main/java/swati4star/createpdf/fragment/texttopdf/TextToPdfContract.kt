package swati4star.createpdf.fragment.texttopdf

/**
 * The [TextToPdfContract] is a contract used by the fragment to communicate with its
 * enhancements.
 */
interface TextToPdfContract {
    /**
     * Represents the view (the fragment in this case).
     */
    interface View {
        /**
         * Update the view when enhancement is changed.
         */
        fun updateView()
    }
}
