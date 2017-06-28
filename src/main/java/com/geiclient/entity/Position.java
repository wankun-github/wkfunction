package com.geiclient.entity;

/**
 * Created by cfzhu on 2017/3/3.
 *
 * 职位实体类
 *
 */
public class Position {
    private Long id;
    private String name;//职位名称
    private String salary;//薪资
    private int type;//种类 0：全职   1：兼职
    private String education;//要求的学历
    private String workYears;//工作年限
    private String address;//工作地址
    private String imgUrl;//图片
    private Recruiter recruiter;//发布职位的招聘者

    public Position() {

    }

    public Position(String name, String salary, int type, String education, String workYears, String address, String imgUrl, Recruiter recruiter) {
        this.name = name;
        this.salary = salary;
        this.type = type;
        this.education = education;
        this.workYears = workYears;
        this.address = address;
        this.imgUrl = imgUrl;
        this.recruiter = recruiter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getWorkYears() {
        return workYears;
    }

    public void setWorkYears(String workYears) {
        this.workYears = workYears;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }
}
