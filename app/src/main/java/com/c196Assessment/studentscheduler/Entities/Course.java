package com.c196Assessment.studentscheduler.Entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/*
The course class is an entity and generates a
table in the room database. Each attribute is a
column in the database and each time an course
entity is added, it generates a new row. These rows
can all be manipulated and deleted.
 */

@Entity(tableName = "course_table")
public class Course {
    @PrimaryKey (autoGenerate = true)
    private int courseId;
    private int termId;
    private String courseTitle;
    private String courseStartDate;
    private String courseEndDate;
    private CourseStatus courseStatus;
    private String note;

    //Constructor method.
    public Course(int courseId, int termId, String courseTitle, String courseStartDate, String courseEndDate, CourseStatus courseStatus, String note) {
        this.courseId = courseId;
        this.termId = termId;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.note = note;
    }
    //Allows us to represent an object as a string. Overriding
    //this method allows us to see the apt output.
    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", termId=" + termId +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseStartDate='" + courseStartDate + '\'' +
                ", courseEndDate='" + courseEndDate + '\'' +
                ", courseStatus=" + courseStatus +
                ", note='" + note + '\'' +
                '}';
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
