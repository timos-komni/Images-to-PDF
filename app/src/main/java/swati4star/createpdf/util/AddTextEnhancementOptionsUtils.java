package swati4star.createpdf.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.itextpdf.text.Font;

import java.util.ArrayList;

import swati4star.createpdf.R;
import swati4star.createpdf.model.EnhancementOptionsEntity;

public class AddTextEnhancementOptionsUtils {

    private AddTextEnhancementOptionsUtils() {

    }

    public static AddTextEnhancementOptionsUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public ArrayList<EnhancementOptionsEntity> getEnhancementOptions(@NonNull Context context,
                                                                     String fontTitle,
                                                                     @NonNull Font.FontFamily fontFamily) {
        ArrayList<EnhancementOptionsEntity> options = new ArrayList<>();

        options.add(new EnhancementOptionsEntity(
                ContextCompat.getDrawable(context, R.drawable.ic_font_black_24dp),
                fontTitle));
        options.add(new EnhancementOptionsEntity(
                context, R.drawable.ic_font_family_24dp,
                String.format(context.getString(R.string.default_font_family_text), fontFamily.name())));
        return options;
    }

    private static class SingletonHolder {
        static final AddTextEnhancementOptionsUtils INSTANCE = new AddTextEnhancementOptionsUtils();
    }

}
