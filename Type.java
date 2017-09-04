package com.tatlicilar.visualeducation;

/**
 * Created by Asus on 24.08.2017.
 */

public class Type {

    public  String userTypeName;
    public  String typeId;

    public  Type() {}

    public String getTypeId() {return typeId;}

    public void setTypeId(String typeId) {this.typeId = typeId;}

    public String getUserTypeName() {return userTypeName;}

    public void setUserTypeName(String userTypeName) {this.userTypeName = userTypeName;
    }

    public Type(String typeId, String userTypeName) {
        this.typeId = typeId;
        this.userTypeName = userTypeName;
    }
}
