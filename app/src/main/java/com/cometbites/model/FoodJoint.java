package com.cometbites.model;

import com.cometbites.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ramakrishnan_sathyav on 11/17/2016.
 */

public class FoodJoint {
    private String name;
    private Integer fjID;

    private Integer logoID;

    private String wait_time;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFjID() {
        return fjID;
    }

    public void setFjID(Integer fjID) {
        this.fjID = fjID;
    }

    public Integer getLogoID() {
        return logoID;
    }

    public void setLogoID(Integer logoID) {
        this.logoID = logoID;
    }


    public String getWait_time() {
        return wait_time;
    }

    public void setWait_time(String wait_time) {
        this.wait_time = wait_time;
    }



    public static ArrayList<FoodJoint> getData(){
        ArrayList<FoodJoint> dataList = new ArrayList<>();

        int[] images = getImages();
        String[] title = {"chick-fil-a","subway","create"};
        for (int i = 0; i < images.length; i++) {

            FoodJoint foodJoint = new FoodJoint();
            foodJoint.setName(title[i]);
            foodJoint.setFjID(i);
            foodJoint.setLogoID(images[i]);
            foodJoint.setWait_time("10");
            dataList.add(foodJoint);
        }

        return dataList;
    }
    public static int[] getImages() {

        int[] images = {

                R.drawable.chickfilea,R.drawable.subway,R.drawable.create
        };

        return images;
    }


}
