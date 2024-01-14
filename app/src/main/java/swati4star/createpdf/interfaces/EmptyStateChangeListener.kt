package swati4star.createpdf.interfaces

interface EmptyStateChangeListener {
    fun setEmptyStateVisible()
    fun setEmptyStateInvisible()
    fun showNoPermissionsView()
    fun hideNoPermissionsView()
    fun filesPopulated()
}
