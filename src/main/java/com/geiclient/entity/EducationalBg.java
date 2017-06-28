package com.geiclient.entity;

import java.util.Date;

/**
 * Created by cfzhu on 2017/2/23.
 *
 * 教育背景实体类，简历附属类
 *
 */
public class EducationalBg {
    private Long id;
    private Date startTime;
    private Date endTime;
    private String schoolName;
    private String major;
    private Resume resume;

    public EducationalBg() {
    }

    public EducationalBg(Date startTime, Date endTime, String schoolName, String major, Resume resume) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.schoolName = schoolName;
        this.major = major;
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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }
}
