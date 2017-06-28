package com.geiclient.entity;

/**
 * Created by cfzhu on 2017/2/23.
 *
 * 招聘者公司的图片，一个公司可以有多个图片
 *
 */
public class CompanyImg {
    private Long id;
    private String companyImgUrl;
    private Recruiter recruiter;

    public CompanyImg() {
    }

    public CompanyImg(String companyImgUrl, Recruiter recruiter) {
        this.companyImgUrl = companyImgUrl;
        this.recruiter = recruiter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyImgUrl() {
        return companyImgUrl;
    }

    public void setCompanyImgUrl(String companyImgUrl) {
        this.companyImgUrl = companyImgUrl;
    }

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }
}
