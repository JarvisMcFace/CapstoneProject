package com.gobluegreen.app.util;

/**
 * Created by David on 9/24/16.
 */

public interface OkHttpHelperCallback {

    void performOnPostExecute(String jsonResults, int requestId, int responseCode);
}
