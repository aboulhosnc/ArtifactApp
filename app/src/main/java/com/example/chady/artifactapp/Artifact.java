package com.example.chady.artifactapp;

/*
 * Created by michaelpoblacion1 on 3/24/18.*/


public class Artifact {
    private int id = 0;
    private String artifactName = "";
    private String type[] = {"Weapons", "Tools", "Cooking Utinsels", "Art", "Clothing", "Other"};
    private int price = 0;

    //Firebase User???
    //Firebase Image??

    public Artifact(String artifactName) {
        this.artifactName = artifactName;
    }

    public Artifact(String artifactName, int price) {
        super();

        this.price = price;
    }

    public String getArtifactName() {
        return artifactName;
    }

    public void setArtifactName(String artifactName) {
        this.artifactName = artifactName;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
