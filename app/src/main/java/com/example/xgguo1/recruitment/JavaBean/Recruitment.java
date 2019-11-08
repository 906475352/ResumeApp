package com.example.xgguo1.recruitment.JavaBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Recruitment extends BmobObject{
    private String jbPosition;
    private String jbType;
    private String jbExperience;
    private String jbEducation;
    private String jbStar;
    private String jbSalary;
    private String jbCity;
    private String jbPositionDesc;
    private String jbPositionRequest;
    private String jbAdress;
    private BmobFile jbCompanyImg;

    public String getJbSalary() {
        return jbSalary;
    }

    public void setJbSalary(String jbSalary) {
        this.jbSalary = jbSalary;
    }


    public String getJbType() {
        return jbType;
    }

    public void setJbType(String jbType) {
        this.jbType = jbType;
    }

    public String getJbPosition() {
        return jbPosition;
    }

    public void setJbPosition(String jbPosition) {
        this.jbPosition = jbPosition;
    }

    public String getJbExperience() {
        return jbExperience;
    }

    public void setJbExperience(String jbExperience) {
        this.jbExperience = jbExperience;
    }

    public String getJbEducation() {
        return jbEducation;
    }

    public void setJbEducation(String jbEducation) {
        this.jbEducation = jbEducation;
    }

    public String getJbStar() {
        return jbStar;
    }

    public void setJbStar(String jbStar) {
        this.jbStar = jbStar;
    }

    public String getJbCity() {
        return jbCity;
    }

    public void setJbCity(String jbCity) {
        this.jbCity = jbCity;
    }

    public String getJbPositionDesc() {
        return jbPositionDesc;
    }

    public void setJbPositionDesc(String jbPositionDesc) {
        this.jbPositionDesc = jbPositionDesc;
    }

    public String getJbPositionRequest() {
        return jbPositionRequest;
    }

    public void setJbPositionRequest(String jbPositionRequest) {
        this.jbPositionRequest = jbPositionRequest;
    }

    public String getJbAdress() {
        return jbAdress;
    }

    public void setJbAdress(String jbAdress) {
        this.jbAdress = jbAdress;
    }

    public BmobFile getJbCompanyImg() {
        return jbCompanyImg;
    }

    public void setJbCompanyImg(BmobFile jbCompanyImg) {
        this.jbCompanyImg = jbCompanyImg;
    }

}