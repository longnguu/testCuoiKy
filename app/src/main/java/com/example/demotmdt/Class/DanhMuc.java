package com.example.demotmdt.Class;

public class DanhMuc {
    String name,mota;
    int hinh;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public DanhMuc(String name, String mota, int hinh) {
        this.name = name;
        this.mota = mota;
        this.hinh = hinh;
    }
}
