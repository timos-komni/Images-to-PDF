package swati4star.createpdf.interfaces

interface OnTextToPdfInterface {
    fun onPDFCreationStarted()
    fun onPDFCreated(success: Boolean)
}
