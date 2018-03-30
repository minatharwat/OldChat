package com.example.user.oldchat.models;

/**
 * Created by Mina on 14/07/2017.
 */

public class GroubModel {
    public String groubName;
    public String groubMembers;
    public String countOfMembers;

    public GroubModel(String groubName, String GroubMembers, String countOfMembers) {
        this.groubMembers = GroubMembers;
        this.groubMembers = GroubMembers;
        this.countOfMembers = countOfMembers;
    }

    public String getGroubName() {
        return groubName;
    }

    public void setGroubName(String groubName) {
        this.groubName = groubName;
    }

    public String getGroubMembers() {
        return groubMembers;
    }

    public void setGroubMembers(String groubMembers) {
        this.groubMembers = groubMembers;
    }

    public String getCountOfMembers() {
        return countOfMembers;
    }

    public void setCountOfMembers(String countOfMembers) {
        this.countOfMembers = countOfMembers;
    }
}
