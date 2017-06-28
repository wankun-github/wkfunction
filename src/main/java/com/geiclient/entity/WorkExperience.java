package com.geiclient.entity;

import java.util.Date;

/**
 * Created by cfzhu on 2017/2/23.
 *
 * 工作经历实体类，简历附属类
 *
 */
public class WorkExperience {
    private Long id;
    private Date startTime;
    private Date endTime;
    private String companyName;
    private String position;
    private String remark;
    private Resume resume;

    public WorkExperience() {
    }

    public WorkExperience(Date startTime, Date endTime, String companyName, String position, String remark, Resume resume) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.companyName = companyName;
        this.position = position;
        this.remark = remark;
        this.resume = resume;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }
}
