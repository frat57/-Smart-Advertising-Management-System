package com.example.yazlab3;

public class Firmam {
    private String lokasyon;
    private String name;
    private String type;
    private String sure;
    public Firmam(){

    }
    public Firmam(int id,String lokasyon, String name, String type, String sure) {
        this.id = id;
        this.lokasyon = lokasyon;
        this.name = name;
        this.type = type;
        this.sure = sure;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;


    public String getLokasyon() {
        return lokasyon;
    }

    public void setLokasyon(String lokasyon) {
        this.lokasyon = lokasyon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSure() {
        return sure;
    }

    public void setSure(String sure) {
        this.sure = sure;
    }


}
