package com.nmq.foodninjaver2.features.Home.Domain;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantDomain implements Serializable {
    private String title;
    private String pic;
    private ArrayList<MenuDomain> menuItems; //DS món ăn

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
