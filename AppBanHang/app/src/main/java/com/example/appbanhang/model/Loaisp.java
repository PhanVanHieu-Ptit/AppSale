package com.example.appbanhang.model;

public class Loaisp {
    public int Id;
    public String Tenloaisp;
    public String Hinhanhloaisp;

    public Loaisp(int id, String tenloaisp, String hinhanhloaisp) {
        Id = id;
        Tenloaisp = tenloaisp;
        Hinhanhloaisp = hinhanhloaisp;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setTenloaisp(String tenloaisp) {
        Tenloaisp = tenloaisp;
    }

    public void setHinhanhloaisp(String hinhanhloaisp) {
        Hinhanhloaisp = hinhanhloaisp;
    }

    public int getId() {
        return Id;
    }

    public String getTenloaisp() {
        return Tenloaisp;
    }

    public String getHinhanhloaisp() {
        return Hinhanhloaisp;
    }
}
