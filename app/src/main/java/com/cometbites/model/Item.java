package com.cometbites.model;

import com.cometbites.R;

import java.util.ArrayList;

/**
 * Created by Ramakrishnan_sathyav on 11/17/2016.
 */

public class Item {

    private String name;
    private Double price;
    private Integer imageID;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getImageID() {
        return imageID;
    }

    public void setImageID(Integer imageID) {
        this.imageID = imageID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static ArrayList<Item> getData(){
        ArrayList<Item> dataList = new ArrayList<>();

        int[] images = getImages();

        for (int i = 0; i < images.length; i++) {

            Item item = new Item();
            item.setName("Subway "+ i);
            item.setDescription("desc "+ i);
            item.setImageID(images[i]);
            item.setPrice(Double.parseDouble("5.99"));
            dataList.add(item);
        }

        return dataList;
    }
    public static int[] getImages() {

        int[] images = {

                R.drawable.sub1,R.drawable.sub2,R.drawable.sub3,R.drawable.sub4,R.drawable.sub5,
                R.drawable.sub6,R.drawable.sub7,R.drawable.sub8,R.drawable.sub9,R.drawable.sub10,
                R.drawable.sub11
        };

        return images;
    }

}
