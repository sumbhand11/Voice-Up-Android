package com.voiceup.sumantbhandari.social;

/**
 * Created by sumantbhandari on 12/31/16.
 */

public class UserTable {

    private String name;
    private String image;

    public  UserTable(){}

    public UserTable(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
