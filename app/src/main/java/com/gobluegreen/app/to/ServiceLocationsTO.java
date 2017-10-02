package com.gobluegreen.app.to;

import java.io.Serializable;
import java.util.List;

/**
 * Created by David on 10/1/17.
 */

public class ServiceLocationsTO implements Serializable {

    private static final long serialVersionUID = 5299486819821178472L;

    private List<LocationTO> locationTOs;

    public List<LocationTO> getLocationTOs() {
        return locationTOs;
    }

    public void setLocationTOs(List<LocationTO> locationTOs) {
        this.locationTOs = locationTOs;
    }
}
