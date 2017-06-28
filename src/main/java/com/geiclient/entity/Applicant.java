package com.geiclient.entity;

import java.util.List;

/**
 * Created by cfzhu on 2017/2/23.
 *
 * 求职者实体类
 *
 */
public class Applicant {
    private Long id;
    private String accid;
    private String name;
    private String passWord;
    private String imgUrl;
    private int gender;
    private int age;
    private String tokenKey;
    private List<Recruiter> concernRecruiters;
    private List<Resume> resumes;



    public Applicant() {
    }

    public Applicant(String name, String accid, String passWord, String imgUrl, int gender, int age, String tokenKey, List<Recruiter> concernRecruiters, List<Resume> resumes) {
        this.name = name;
        this.accid = accid;
        this.passWord = passWord;
        this.imgUrl = imgUrl;
        this.gender = gender;
        this.age = age;
        this.tokenKey = tokenKey;
        this.concernRecruiters = concernRecruiters;
        this.resumes = resumes;
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

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public List<Recruiter> getConcernRecruiters() {
        return concernRecruiters;
    }

    public void setConcernRecruiters(List<Recruiter> concernRecruiters) {
        this.concernRecruiters = concernRecruiters;
    }

    public List<Resume> getResumes() {
        return resumes;
    }

    public void setResumes(List<Resume> resumes) {
        this.resumes = resumes;
    }
}
