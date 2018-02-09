package com.example.ritesh.profilesneva;

import android.graphics.Bitmap;

/**
 * Created by Ritesh on 07-02-2018.
 */

class ListData {
    private String personName;
    private String skills;
    private Bitmap imgAddress;

    Bitmap getImgAddress() {
        return imgAddress;
    }

    void setImgAddress(Bitmap imgAddress) {

        this.imgAddress = imgAddress;
    }

    String getPersonName() {
        return personName;
    }

    void setPersonName(String personName) {
        this.personName = personName;
    }

    String getSkills() {
        return skills;
    }

    void setSkills(String skills) {
        this.skills = skills;
    }


}
