package com.nmq.foodninjaver2.features.Home.Model;

public class MenuDomain {
    private String title;
    private String pic;
    private double fee;

    public MenuDomain(String title, String pic, double fee) {
        this.title = title;
        this.pic = pic;
        this.fee = fee;
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

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}