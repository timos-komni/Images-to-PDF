package swati4star.createpdf.model

class HomePageItem
/**
 * Constructor for home page item
 *
 * @param iconId      icon drawable id
 * @param drawableId  drawable id of the Home Page Item
 * @param titleString title string resource id of home page item
 */(private val iconId: Int, val drawableId: Int, val titleString: Int) {

     fun getNavigationItemId(): Int = iconId
}
