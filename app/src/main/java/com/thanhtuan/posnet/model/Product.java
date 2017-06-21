package com.thanhtuan.posnet.model;


import java.util.List;

public class Product {
    private String NamePR;
    private String DonGia;
    private String SL;
    private Boolean chon;
    private List<Product> listKM;

    public Product() {
    }

    public Product(String namePR, String donGia, String SL, Boolean chon) {
        NamePR = namePR;
        DonGia = donGia;
        this.SL = SL;
        this.chon = chon;
    }

    public Product(String namePR, String donGia, String SL, Boolean chon, List<Product> listKM) {
        NamePR = namePR;
        DonGia = donGia;
        this.SL = SL;
        this.chon = chon;
        this.listKM = listKM;
    }

    public String getNamePR() {
        return NamePR;
    }

    public void setNamePR(String namePR) {
        NamePR = namePR;
    }

    public String getDonGia() {
        return DonGia;
    }

    public void setDonGia(String donGia) {
        DonGia = donGia;
    }

    public String getSL() {
        return SL;
    }

    public void setSL(String SL) {
        this.SL = SL;
    }

    public Boolean getChon() {
        return chon;
    }

    public void setChon(Boolean chon) {
        this.chon = chon;
    }

    public List<Product> getListKM() {
        return listKM;
    }

    public void setListKM(List<Product> listKM) {
        this.listKM = listKM;
    }
}
