package com.recruitment.field_buzz_test.models.recruitment;

public class CvFile {
    private Integer id;
    private String tsync_id;

    public CvFile(Integer id, String tsync_id) {
        this.id = id;
        this.tsync_id = tsync_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTsync_id() {
        return tsync_id;
    }

    public void setTsync_id(String tsync_id) {
        this.tsync_id = tsync_id;
    }
}
