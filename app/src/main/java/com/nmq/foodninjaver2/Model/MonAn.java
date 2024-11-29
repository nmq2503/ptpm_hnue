package com.nmq.foodninjaver2.Model;

import java.io.Serializable;

public class MonAn implements Serializable {
    int anhMonAn;
    public MonAn() {};
    public MonAn(int anhMonAn) {
        this.anhMonAn = anhMonAn;
    }

    public int getAnhMonAn() {
        return anhMonAn;
    }

    public void setAnhMonAn(int anhMonAn) {
        this.anhMonAn = anhMonAn;
    }
}
