package com.example.maryam.bakingapp.Utils;


import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

public final class AppUtils {

    public static final String STEP_FRAGMENT_TAG = "STEP_FRAGMENT_TAG";
    //sent_extras
    public static final String TWO_PANE_LAYOUT = "TWO_PANE_LAYOUT";
    public static final String SEND_RECIPE = "recipe";
    public static final String SEND_STEP = "step";

    public static final String PLAY_IF_READY = "PLAY_IF_READY";
    public static final String EXO_POSITION = "EXO_POSITION";


    // widget preferences
    public static final String PREFERENCES_WIDGET_CONTENT = "WIDGET_CONTENT";
    public static final String SHARED_PREFERENCE = "SHARED_PREFERENCE";

    public static final String MIME_VIDEO = "video/";
    public static final String MIME_IMAGE = "image/";

    //get mimeType
    public static String getMimeType(Context context, Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    public static String fmt(double d) {
        if (d == (long) d)
            return String.format("%d", (long) d);
        else
            return String.format("%s", d);
    }
}
