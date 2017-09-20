package com.gobluegreen.app.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.gobluegreen.app.data.estimate.EstimateContentProvider;

/**
 * Created by David on 9/17/17.
 */

public class EstimateLoader extends CursorLoader {

    public EstimateLoader(Context context) {
        super(context);
    }

    public EstimateLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }

    public static Loader<Cursor> loadAllEstimates(Context context) {
        return new EstimateLoader(context, EstimateContentProvider.CONTENT_URI);
    }

    private EstimateLoader(Context context, Uri uri) {
        super(context, uri, null, null, null, null);
    }


}
