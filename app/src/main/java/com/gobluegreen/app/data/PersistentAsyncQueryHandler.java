package com.gobluegreen.app.data;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.net.Uri;

import java.lang.ref.WeakReference;

/**
 * Created by David on 7/18/17.
 */

public class PersistentAsyncQueryHandler  extends AsyncQueryHandler{

        private WeakReference<PersistentAsyncListener> listener;

    public PersistentAsyncQueryHandler(ContentResolver contentResolver, PersistentAsyncListener listner) {
        super(contentResolver);
        this.listener = new WeakReference<PersistentAsyncListener>(listner);
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {

        PersistentAsyncListener persistentAsyncListener = listener.get();
        persistentAsyncListener.onTransactionComplete(cookie);
    }
}
