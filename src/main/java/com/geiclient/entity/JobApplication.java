package com.geiclient.entity;

/**
 * Created by cfzhu on 2017/2/23.
 *
 * 职位申请类: Applicant --> Recruiter
 *
 */
public class JobApplication {
    private Long id;
    private String title;
    private String content;
    private Recruiter recruiter;
    private Applicant applicant;
    private Resume resume;
    private Position position;
    private int lifeStatus;

    public JobApplication() {
    }

    public JobApplication(String title, String content, Recruiter recruiter, Applicant applicant, Resume resume, Position position, int lifeStatus) {
        this.title = title;
        this.content = content;
        this.recruiter = recruiter;
        this.applicant = applicant;
        this.resume = resume;
        this.position = position;
        this.lifeStatus = lifeStatus;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public int getLifeStatus() {
        return lifeStatus;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setLifeStatus(int lifeStatus) {
        this.lifeStatus = lifeStatus;
    }
}
