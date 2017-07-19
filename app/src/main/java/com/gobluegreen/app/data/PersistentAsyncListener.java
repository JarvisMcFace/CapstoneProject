package com.gobluegreen.app.data;

/**
 * Created by David on 7/18/17.
 */

public interface PersistentAsyncListener {

    void onTransactionComplete(Object returnValue);
}
