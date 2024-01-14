package swati4star.createpdf.model;

public class PreviewImageOptionItem {
    private int mOptionImageId;
    private String mOptionName;

    public PreviewImageOptionItem(int mOptionImageId, String mOptionName) {
        this.mOptionImageId = mOptionImageId;
        this.mOptionName = mOptionName;
    }

    public int getOptionImageId() {
        return mOptionImageId;
    }

    /** @noinspection unused*/
    public void setOptionImageId(int mOptionImageId) {
        this.mOptionImageId = mOptionImageId;
    }

    public String getOptionName() {
        return mOptionName;
    }

    /** @noinspection unused*/
    public void setOptionName(String mOptionName) {
        this.mOptionName = mOptionName;
    }
}
