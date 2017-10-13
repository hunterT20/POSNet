package com.thanhtuan.posnet.model.data;


public class Customer {
    private String Name;
    private String SDT;
    private String DiaChi;

    public Customer() {
    }

    public Customer(String name, String SDT, String diaChi) {
        Name = name;
        this.SDT = SDT;
        DiaChi = diaChi;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }
}
