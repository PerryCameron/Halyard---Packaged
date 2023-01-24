package com.ecsail.structures;

public class LabelDTO {
    String city;
    String nameAndMemId;
    String expires;
    String member;

    public LabelDTO() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNameAndMemId() {
        return nameAndMemId;
    }

    public void setNameAndMemId(String nameAndMemId) {
        this.nameAndMemId = nameAndMemId;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }
}
