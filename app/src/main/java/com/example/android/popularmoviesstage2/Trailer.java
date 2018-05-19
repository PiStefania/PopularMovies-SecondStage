package com.example.android.popularmoviesstage2;

public class Trailer {
    private int id;
    private String key;

    public Trailer(int id, String url){
        this.id=id;
        this.key=url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
