package com.example.appbanhang.model;

import java.io.Serializable;

public class Sanpham implements Serializable {
    private  int Id;
    private  String Tensanpham;
    private  Integer Giasanpham;
    private String Hinhsanpham;
    private String Motasanpham;
    private int IdSanpham;

    public Sanpham(int id, String tensanpham, Integer giasanpham, String hinhsanpham, String motasanpham, int idSanpham) {
        Id = id;
        Tensanpham = tensanpham;
        Giasanpham = giasanpham;
        Hinhsanpham = hinhsanpham;
        Motasanpham = motasanpham;
        IdSanpham = idSanpham;
    }

    public int getId() {
        return Id;
    }

    public String getTensanpham() {
        return Tensanpham;
    }

    public Integer getGiasanpham() {
        return Giasanpham;
    }

    public String getHinhsanpham() {
        return Hinhsanpham;
    }

    public String getMotasanpham() {
        return Motasanpham;
    }

    public int getIdSanpham() {
        return IdSanpham;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setTensanpham(String tensanpham) {
        Tensanpham = tensanpham;
    }

    public void setGiasanpham(Integer giasanpham) {
        Giasanpham = giasanpham;
    }

    public void setHinhsanpham(String hinhsanpham) {
        Hinhsanpham = hinhsanpham;
    }

    public void setMotasanpham(String motasanpham) {
        Motasanpham = motasanpham;
    }

    public void setIdSanpham(int idSanpham) {
        IdSanpham = idSanpham;
    }
}

