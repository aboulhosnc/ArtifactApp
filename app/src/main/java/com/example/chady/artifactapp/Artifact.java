package com.example.chady.artifactapp;

/*
 * Created by michaelpoblacion1 on 3/24/18.*/


public class Artifact {

    private String artifactName;
    private String description;
    private String  image;
    private String  toolType;
    private int price = 0;





    public Artifact () {}

    public Artifact(String artifactName, String desc, String image, String toolType, int price) {

        this.artifactName = artifactName;
        this.image = image;
        this.description = desc;
        this.toolType = toolType;
        this.price = price;
    }


    // artifact name

    public String getArtifactName() {

        return artifactName;
    }

    public void setArtifactName(String artifactName) {
        this.artifactName = artifactName;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
