package com.gobluegreen.app.to;

import java.io.Serializable;

/**
 * Created by David on 7/6/17.
 */

public class UpholsteryTO implements Serializable {

    private static final long serialVersionUID = -2406530213260614209L;

    private UpholsteryType upholsteryType;

    public UpholsteryType getUpholsteryType() {
        return upholsteryType;
    }

    public void setUpholsteryType(UpholsteryType upholsteryType) {
        this.upholsteryType = upholsteryType;
    }
}
