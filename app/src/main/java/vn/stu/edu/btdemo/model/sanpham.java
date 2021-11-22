package vn.stu.edu.btdemo.model;

import java.io.Serializable;

public class sanpham implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getPhanloai() {
        return phanloai;
    }

    public void setPhanloai(String phanloai) {
        this.phanloai = phanloai;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }

    private int id;
    private String ten;
    private int gia;

    public sanpham(int id, String ten, int gia, String phanloai, String mota, byte[] anh) {
        this.id = id;
        this.ten = ten;
        this.gia = gia;
        this.phanloai = phanloai;
        this.mota = mota;
        this.anh = anh;
    }

    private String phanloai;
    private String mota;
    private byte[] anh;


}
