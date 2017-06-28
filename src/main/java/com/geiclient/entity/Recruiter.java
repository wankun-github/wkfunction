package com.geiclient.entity;

import java.util.List;

/**
 * Created by cfzhu on 2017/2/23.
 *
 * 招聘者实体类
 *
 */
public class Recruiter {
    private Long id;
    private String businessLicense;
    private String accid;
    private String name;
    private String passWord;
    private String address;
    private String phone;
    private List<Applicant> concernApplicants;
    private String companyType;
    private String introduction;
    private List<CompanyImg> companyImgUrls;
    private String tokenKey;
    private List<Position> positionList;
    private int lifeStatus;

    public Recruiter() {
    }

    public Recruiter(String businessLicense, String accid, String name, String passWord, String address, String phone, List<Applicant> concernApplicants, String companyType, String introduction, List<CompanyImg> companyImgUrls, String tokenKey, List<Position> positionList, int lifeStatus) {
        this.businessLicense = businessLicense;
        this.accid = accid;
        this.name = name;
        this.passWord = passWord;
        this.address = address;
        this.phone = phone;
        this.concernApplicants = concernApplicants;
        this.companyType = companyType;
        this.introduction = introduction;
        this.companyImgUrls = companyImgUrls;
        this.tokenKey = tokenKey;
        this.positionList = positionList;
        this.lifeStatus = lifeStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Applicant> getConcernApplicants() {
        return concernApplicants;
    }

    public void setConcernApplicants(List<Applicant> concernApplicants) {
        this.concernApplicants = concernApplicants;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<CompanyImg> getCompanyImgUrls() {
        return companyImgUrls;
    }

    public void setCompanyImgUrls(List<CompanyImg> companyImgUrls) {
        this.companyImgUrls = companyImgUrls;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public int getLifeStatus() {
        return lifeStatus;
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<Position> positionList) {
        this.positionList = positionList;
    }

    public void setLifeStatus(int lifeStatus) {
        this.lifeStatus = lifeStatus;
    }
}
