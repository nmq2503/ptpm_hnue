package com.nmq.foodninjaver2.features.Home.Model;

import java.io.Serializable;

public class RestaurantDomain implements Serializable {
    private String title;
    private String pic;

    public RestaurantDomain(String title, String pic) {
        this.title = title;
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
