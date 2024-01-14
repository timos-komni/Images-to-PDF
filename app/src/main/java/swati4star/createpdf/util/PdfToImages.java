package swati4star.createpdf.util;

import static android.os.ParcelFileDescriptor.MODE_READ_ONLY;
import static swati4star.createpdf.util.FileUtils.getFileNameWithoutExtension;
import static swati4star.createpdf.util.ImageUtils.saveImage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import swati4star.createpdf.interfaces.ExtractImagesListener;

/** @noinspection deprecation*/
public class PdfToImages extends AsyncTask<Void, Void, Void> {

    private final String mPath;
    private final Uri mUri;
    private final ExtractImagesListener mExtractImagesListener;
    private final String[] mPassword;
    private final WeakReference<Context> mContext;
    private int mImagesCount = 0;
    private ArrayList<String> mOutputFilePaths;
    private PDFEncryptionUtility mPDFEncryptionUtility;
    private String mDecryptedPath;

    public PdfToImages(Context context, String[] password, String mPath, Uri mUri,
                       ExtractImagesListener mExtractImagesListener) {
        this.mPath = mPath;
        this.mUri = mUri;
        this.mExtractImagesListener = mExtractImagesListener;
        mOutputFilePaths = new ArrayList<>();
        this.mPassword = password;
        this.mContext = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mPDFEncryptionUtility = new PDFEncryptionUtility((Activity) getContext());
        mExtractImagesListener.extractionStarted();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (mPassword != null) {
            mDecryptedPath = mPDFEncryptionUtility.removeDefPasswordForImages(mPath, mPassword);
        }
        mOutputFilePaths = new ArrayList<>();
        mImagesCount = 0;

        // Render pdf pages as bitmap
        ParcelFileDescriptor fileDescriptor = null;
        try {
            if (mDecryptedPath != null)
                fileDescriptor = ParcelFileDescriptor.open(new File(mDecryptedPath), MODE_READ_ONLY);
            else {
                final var ctx = getContext();
                if (mUri != null && ctx != null) {
                    // resolve pdf file path based on uri
                    fileDescriptor = ctx.getContentResolver().openFileDescriptor(mUri, "r");
                } else if (mPath != null) {
                    // resolve pdf file path based on relative path
                    fileDescriptor = ParcelFileDescriptor.open(new File(mPath), MODE_READ_ONLY);
                }
            }
            if (fileDescriptor != null) {
                PdfRenderer renderer = new PdfRenderer(fileDescriptor);
                final int pageCount = renderer.getPageCount();
                for (int i = 0; i < pageCount; i++) {
                    PdfRenderer.Page page = renderer.openPage(i);
                    // generate bitmaps for individual pdf pages
                    Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(),
                            Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    canvas.drawColor(Color.WHITE);
                    canvas.drawBitmap(bitmap, 0, 0, null);
                    // say we render for showing on the screen
                    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

                    // close the page
                    page.close();

                    // generate numbered image file names
                    String filename = getFileNameWithoutExtension(mPath) +
                            "_" + (i + 1);
                    String path = saveImage(filename, bitmap);
                    if (path != null) {
                        mOutputFilePaths.add(path);
                        mImagesCount++;
                    }
                }

                // close the renderer
                renderer.close();
            }
        } catch (IOException | SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mExtractImagesListener.updateView(mImagesCount, mOutputFilePaths);
        if (mDecryptedPath != null)
            //noinspection ResultOfMethodCallIgnored
            new File(mDecryptedPath).delete();
    }

    /** @noinspection unused*/
    protected WeakReference<Context> getContextRef() {
        return mContext;
    }

    public Context getContext() {
        return mContext.get();
    }

}
