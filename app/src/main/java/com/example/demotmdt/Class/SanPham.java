package com.example.demotmdt.Class;

public class SanPham {
    String ten;
    String danhgia;
    String soluongban;
    String gia;
    String mota;
    String img;
    String loai;
    String maSP;
    String UID;
    String daBan;

    public String getDaBan() {
        return daBan;
    }

    public void setDaBan(String daBan) {
        this.daBan = daBan;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(String soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    String soLuongMua;
    int hinhanh;

    public SanPham(String ten) {
        this.ten = ten;
    }

    public SanPham(String ten, String danhgia, String soluongban, String gia, String mota, String img, int hinhanh) {
        this.ten = ten;
        this.danhgia = danhgia;
        this.soluongban = soluongban;
        this.gia = gia;
        this.mota = mota;
        this.img = img;
        this.hinhanh = hinhanh;
    }

    public SanPham(String ten, String soluongban, String gia, String mota, String img, String loai) {
        this.ten = ten;
        this.soluongban = soluongban;
        this.gia = gia;
        this.mota = mota;
        this.img = img;
        this.loai = loai;
    }

    public SanPham(String ten, String soluongban, String gia, String mota, String img) {
        this.ten = ten;
        this.soluongban = soluongban;
        this.gia = gia;
        this.mota = mota;
        this.img = img;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(String danhgia) {
        this.danhgia = danhgia;
    }

    public String getSoluongban() {
        return soluongban;
    }

    public void setSoluongban(String soluongban) {
        this.soluongban = soluongban;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(int hinhanh) {
        this.hinhanh = hinhanh;
    }
}
