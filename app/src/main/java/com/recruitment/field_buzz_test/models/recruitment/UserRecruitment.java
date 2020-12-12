package com.recruitment.field_buzz_test.models.recruitment;

public class UserRecruitment {
    private String tsync_id;
    private String name;
    private String email;
    private String phone;
    private String full_address;
    private String name_of_university;
    private int graduation_year;
    private double cgpa;
    private int experience_in_months;
    private String current_work_place_name;
    private String applying_in;
    private int expected_salary;
    private String field_buzz_reference;
    private String github_project_url;
    private CvFile cv_file;
    private long on_spot_update_time;
    private long on_spot_creation_time;
    private Boolean success;
    private String message;

    public String getTsync_id() {
        return tsync_id;
    }

    public void setTsync_id(String tsync_id) {
        this.tsync_id = tsync_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFull_address() {
        return full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }

    public String getName_of_university() {
        return name_of_university;
    }

    public void setName_of_university(String name_of_university) {
        this.name_of_university = name_of_university;
    }

    public int getGraduation_year() {
        return graduation_year;
    }

    public void setGraduation_year(int graduation_year) {
        this.graduation_year = graduation_year;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public int getExperience_in_months() {
        return experience_in_months;
    }

    public void setExperience_in_months(int experience_in_months) {
        this.experience_in_months = experience_in_months;
    }

    public String getCurrent_work_place_name() {
        return current_work_place_name;
    }

    public void setCurrent_work_place_name(String current_work_place_name) {
        this.current_work_place_name = current_work_place_name;
    }

    public String getApplying_in() {
        return applying_in;
    }

    public void setApplying_in(String applying_in) {
        this.applying_in = applying_in;
    }

    public int getExpected_salary() {
        return expected_salary;
    }

    public void setExpected_salary(int expected_salary) {
        this.expected_salary = expected_salary;
    }

    public String getField_buzz_reference() {
        return field_buzz_reference;
    }

    public void setField_buzz_reference(String field_buzz_reference) {
        this.field_buzz_reference = field_buzz_reference;
    }

    public String getGithub_project_url() {
        return github_project_url;
    }

    public void setGithub_project_url(String github_project_url) {
        this.github_project_url = github_project_url;
    }

    public long getOn_spot_update_time() {
        return on_spot_update_time;
    }

    public void setOn_spot_update_time(long on_spot_update_time) {
        this.on_spot_update_time = on_spot_update_time;
    }

    public long getOn_spot_creation_time() {
        return on_spot_creation_time;
    }

    public void setOn_spot_creation_time(long on_spot_creation_time) {
        this.on_spot_creation_time = on_spot_creation_time;
    }

    public CvFile getCv_file() {
        return cv_file;
    }

    public void setCv_file(CvFile cv_file) {
        this.cv_file = cv_file;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
