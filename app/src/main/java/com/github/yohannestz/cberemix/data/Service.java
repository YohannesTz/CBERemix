package com.github.yohannestz.cberemix.data;

public class Service {
    private String name;
    private int imageRes;

    public Service(String name, int imageRes) {
        this.name = name;
        this.imageRes = imageRes;
    }

    public String getName() {
        return name;
    }

    public int getImageRes() {
        return imageRes;
    }
}
