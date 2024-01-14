package swati4star.createpdf.model

import java.util.Objects

class BrushItem(val color: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val brushItem = other as BrushItem
        return color == brushItem.color
    }

    override fun hashCode(): Int = Objects.hash(color)
}
