package com.example.demotmdt.Class;

public class User {
    String idUser,tenUser,email,matKhau,diaChi,SDT,thongTinThanhToan,imgUS;

    public String getImgUS() {
        return imgUS;
    }

    public void setImgUS(String imgUS) {
        this.imgUS = imgUS;
    }

    byte[] hinhAnh;

    public User(String idUser, String tenUser, String taiKhoan, String matKhau, String diaChi, String SDT, String thongTinThanhToan, byte[] hinhAnh) {
        this.idUser = idUser;
        this.tenUser = tenUser;
        this.email = taiKhoan;
        this.matKhau = matKhau;
        this.diaChi = diaChi;
        this.SDT = SDT;
        this.thongTinThanhToan = thongTinThanhToan;
        this.hinhAnh = hinhAnh;
    }

    public User() {
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTenUser() {
        return tenUser;
    }

    public void setTenUser(String tenUser) {
        this.tenUser = tenUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getThongTinThanhToan() {
        return thongTinThanhToan;
    }

    public void setThongTinThanhToan(String thongTinThanhToan) {
        this.thongTinThanhToan = thongTinThanhToan;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
