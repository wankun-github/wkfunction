package com.geiclient.entity;

import java.util.List;

/**
 * Created by cfzhu on 2017/2/23.
 *
 * 简历实体类，求职者附属类
 *
 */
public class Resume {
    private Long id;
    private String title;
    private String name;
    private int age;
    private int gender;
    private String phone;
    private String address;
    private String email;
    private String imgUrl;
    private List<EducationalBg> educationalBgs;// 求职者的教育背景
    private List<WorkExperience> WorkExperiences;// 求职者的工作经历
    private List<SkillCertificate> SkillCertificates;// 技能证书
    private String evaluation;
    private Applicant applicant;

    public Resume() {

    }

    public Resume(String title, String name, int age, int gender, String phone, String address, String email, String imgUrl, List<EducationalBg> educationalBgs, List<WorkExperience> workExperiences, List<SkillCertificate> skillCertificates, String evaluation, Applicant applicant) {
        this.title = title;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.imgUrl = imgUrl;
        this.educationalBgs = educationalBgs;
        WorkExperiences = workExperiences;
        SkillCertificates = skillCertificates;
        this.evaluation = evaluation;
        this.applicant = applicant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<EducationalBg> getEducationalBgs() {
        return educationalBgs;
    }

    public void setEducationalBgs(List<EducationalBg> educationalBgs) {
        this.educationalBgs = educationalBgs;
    }

    public List<WorkExperience> getWorkExperiences() {
        return WorkExperiences;
    }

    public void setWorkExperiences(List<WorkExperience> workExperiences) {
        WorkExperiences = workExperiences;
    }

    public List<SkillCertificate> getSkillCertificates() {
        return SkillCertificates;
    }

    public void setSkillCertificates(List<SkillCertificate> skillCertificates) {
        SkillCertificates = skillCertificates;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
}
