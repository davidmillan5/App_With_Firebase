package com.example.firebaseinit;

public class ClsStudent {

    //Atributes

    private String id;
    private String fullName;
    private String major;
    private String semester;
    private String checkBox;


    // Getters And Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(String checkBox) {
        this.checkBox = checkBox;
    }


    // Constructor


    public ClsStudent() {
    }

    public ClsStudent(String id, String fullName, String major, String semester, String checkBox) {
        this.id = id;
        this.fullName = fullName;
        this.major = major;
        this.semester = semester;
        this.checkBox = checkBox;
    }
}
