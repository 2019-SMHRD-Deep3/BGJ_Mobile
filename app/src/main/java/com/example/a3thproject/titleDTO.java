package com.example.a3thproject;

public class titleDTO {

    String title;
    String path;
    int num;


    public titleDTO(String title, String path) {
        this.title = title;
        this.path = path;
    }

    public titleDTO(String title, int num) {
        this.title = title;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }


    public String getPath() {
        return path;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setPath(String path) {
        this.path = path;
    }
}