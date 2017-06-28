package com.geiclient.entity;

/**
 * Created by cfzhu on 2017/2/23.
 *
 * 面试邀请类: Recruiter --> Applicant
 *
 */
public class InterviewInvitation {
    private Long id;
    private String title;
    private String content;
    private Recruiter recruiter;
    private Applicant applicant;
    private Position position;
    private int lifeStatus;

    public InterviewInvitation() {
    }

    public InterviewInvitation(String title, String content, Recruiter recruiter, Applicant applicant, Position position, int lifeStatus) {
        this.title = title;
        this.content = content;
        this.recruiter = recruiter;
        this.applicant = applicant;
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
