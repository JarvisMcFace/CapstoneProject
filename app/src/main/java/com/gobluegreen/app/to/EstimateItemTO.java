package com.gobluegreen.app.to;

import java.io.Serializable;

/**
 * Created by David on 7/14/17.
 */

public class EstimateItemTO implements Serializable {

    private static final long serialVersionUID = 1372077230152769207L;

    public enum ItemType {HEADER, ROOM, STAIRWAY, UPHOLSTERY}

    private Object itemObject;
    private ItemType itemType;

    public EstimateItemTO(ItemType itemType) {
        this.itemType = itemType;
    }

    public Object getItemObject() {
        return itemObject;
    }

    public void setItemObject(Object itemObject) {
        this.itemObject = itemObject;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
}
