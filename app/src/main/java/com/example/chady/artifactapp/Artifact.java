package com.example.chady.artifactapp;

/*
 * Created by michaelpoblacion1 on 3/24/18.*/


public class Artifact {

    private String title;
    private String description;
    private String  image;
    private String  toolType;
    private long price;
    private String username;





    public Artifact () {}

    public Artifact(String title, String desc, String image, String toolType, int price, String username) {

        this.title = title;
        this.image = image;
        this.description = desc;
        this.toolType = toolType;
        this.price = price;
        this.username = username;
    }


    // artifact name

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    //description

    public String getDescription() {
        return description;
    }

    public void setDescription() {
        this.description = description;
    }

    // image

    public String getImage () {
        return image;
    }

    public void setImage() {
        this.image = image;
    }

    //  tool type

    public String getToolType() {
        return toolType;
}

    public void setToolType () {

        this.toolType = toolType;
    }

    public long getPrice() {

        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
