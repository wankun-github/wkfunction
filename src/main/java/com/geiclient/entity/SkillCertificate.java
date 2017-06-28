package com.geiclient.entity;

/**
 * Created by cfzhu on 2017/2/23.
 *
 * 技能证书实体类，简历附属类
 *
 */
public class SkillCertificate {
    private Long id;
    private String name;
    private Resume resume;

    public SkillCertificate() {
    }

    public SkillCertificate(String name, Resume resume) {
        this.name = name;
        this.resume = resume;
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

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }
}
