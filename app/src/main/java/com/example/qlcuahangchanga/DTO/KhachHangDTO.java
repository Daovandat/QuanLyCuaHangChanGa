package com.example.qlcuahangchanga.DTO;

public class KhachHangDTO {
    int MaKhach;
    String TenKhach;
    String diachi;
    String sdt;
    boolean DuocChon;

    public int getMaKhach() {
        return MaKhach;
    }

    public void setMaKhach(int maKhach) {
        MaKhach = maKhach;
    }

    public String getTenKhach() {
        return TenKhach;
    }

    public void setTenKhach(String tenKhach) {
        TenKhach = tenKhach;
    }

    public boolean isDuocChon() {
        return DuocChon;
    }

    public void setDuocChon(boolean duocChon) {
        DuocChon = duocChon;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
