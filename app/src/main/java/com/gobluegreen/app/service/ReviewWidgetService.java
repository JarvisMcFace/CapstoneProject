package com.gobluegreen.app.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by David on 9/21/17.
 */

public class ReviewWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ReviewRemoteViewFactory(this.getApplication(), intent);
    }
}
