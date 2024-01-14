package swati4star.createpdf.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class EnhancementOptionsEntity {
    private Drawable mImage;
    private String mName;

    public EnhancementOptionsEntity(Drawable image, String name) {
        this.mImage = image;
        this.mName = name;
    }

    public EnhancementOptionsEntity(@NonNull Context context, int imageId, String name) {
        this.mImage = ContextCompat.getDrawable(context, imageId);
        this.mName = name;
    }

    public EnhancementOptionsEntity(@NonNull Context context, int resourceId, int stringId) {
        this.mImage = ContextCompat.getDrawable(context, resourceId);
        this.mName = context.getString(stringId);
    }

    public Drawable getImage() {
        return mImage;
    }

    public void setImage(Drawable image) {
        this.mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }
}
