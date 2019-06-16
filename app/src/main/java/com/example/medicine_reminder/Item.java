package com.example.medicine_reminder;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

public class Item implements Serializable {
    // 編號、帳號、密碼、建立、修改
    private long id;
    private int sys;
    private int dia;
    private int pul;
    private int bloodsugar;
    private int weight;
    private long datetime;

    public Item() {
    }

    public Item(long id, int sys, int dia, int pul, int bloodsugar, int weight, long datetime) {
        this.id = id;
        this.sys = sys;
        this.dia = dia;
        this.pul = pul;
        this.bloodsugar = bloodsugar;
        this.weight = weight;
        this.datetime = datetime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSys() { return sys; }

    public void setSys(int sys) { this.sys = sys; }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getPUL() {
        return pul;
    }

    public void setPul(int pul) {
        this.pul = pul;
    }

    public int getBloodsugar() { return bloodsugar; }

    public void setBloodsugar(int bloodsugar) { this.bloodsugar = bloodsugar; }

    public int getWeight() { return weight; }

    public void setWeight(int weight) { this.weight = weight; }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    // 裝置區域的日期時間
    public String getLocaleDatetime() {
        return String.format(Locale.getDefault(), "%tF  %<tR", new Date(datetime));
    }

    // 裝置區域的日期
    public String getLocaleDate() {
        return String.format(Locale.getDefault(), "%tF", new Date(datetime));
    }

    // 裝置區域的時間
    public String getLocaleTime() {
        return String.format(Locale.getDefault(), "%tR", new Date(datetime));
    }
}
