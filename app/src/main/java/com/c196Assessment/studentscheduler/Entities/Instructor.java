package com.c196Assessment.studentscheduler.Entities;

import android.telephony.PhoneNumberFormattingTextWatcher;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
The instructor class is an entity and generates a
table in the room database. Each attribute is a
column in the database and each time an instructor
entity is added, it generates a new row. These rows
can all be manipulated and deleted.
 */

@Entity(tableName = "instructor_table")
public class Instructor {
    @PrimaryKey (autoGenerate = true)
    private int instructorId;
    private int courseId;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;

    //Constructor method.
    public Instructor(int instructorId, int courseId, String instructorName, String instructorPhone, String instructorEmail) {
        this.instructorId = instructorId;
        this.courseId = courseId;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
    }

    //Allows us to represent an object as a string. Overriding
    //this method allows us to see the apt output.
    @Override
    public String toString() {
        return "Instructor{" +
                "instructorId=" + instructorId +
                ", courseId=" + courseId +
                ", instructorName='" + instructorName + '\'' +
                ", instructorPhone='" + instructorPhone + '\'' +
                ", instructorEmail='" + instructorEmail + '\'' +
                '}';
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
