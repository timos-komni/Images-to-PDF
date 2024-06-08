package swati4star.createpdf.model

import ja.burhanrashid52.photoeditor.PhotoFilter

/**
 * Filter item model
 *
 * @param imageId id of image to be set
 * @param name    filter name
 */
class FilterItem(
    var imageId: Int, var name: String, var filter: PhotoFilter
)
