package com.example.ritesh.profilesneva;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by Ritesh on 07-02-2018.
 */

public class ListData {
    private String personName;
    private String skills;
    private Bitmap imgAddress;

    public Bitmap getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(Bitmap imgAddress) {

        this.imgAddress = imgAddress;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }


}
