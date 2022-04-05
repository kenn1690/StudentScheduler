package com.c196Assessment.studentscheduler.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
The assessment class is an entity and generates a
table in the room database. Each attribute is a
column in the database and each time an assessment
entity is added, it generates a new row. These rows
can all be manipulated and deleted.
 */

@Entity(tableName = "assessment_table")
public class Assessment {
    @PrimaryKey (autoGenerate = true)
    private int assessmentId;
    private String assessmentTitle;
    private String assessmentStartDate;
    private String assessmentEndDate;
    private AssessmentType assessmentType;
    private int courseId;


    //Constructor method.
    public Assessment(int assessmentId, String assessmentTitle, String assessmentStartDate, String assessmentEndDate, AssessmentType assessmentType, int courseId) {
        this.assessmentId = assessmentId;
        this.assessmentTitle = assessmentTitle;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.assessmentType = assessmentType;
        this.courseId = courseId;
    }

    //Allows us to represent an object as a string. Overriding
    //this method allows us to see the apt output.
    @Override
    public String toString() {
        return "Assessment{" +
                "assessmentId=" + assessmentId +
                ", assessmentTitle='" + assessmentTitle + '\'' +
                ", assessmentStartDate='" + assessmentStartDate + '\'' +
                ", assessmentEndDate='" + assessmentEndDate + '\'' +
                ", assessmentType=" + assessmentType +
                ", courseId=" + courseId +
                '}';
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public String getAssessmentStartDate() {
        return assessmentStartDate;
    }

    public void setAssessmentStartDate(String assessmentStartDate) {
        this.assessmentStartDate = assessmentStartDate;
    }

    public String getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public void setAssessmentEndDate(String assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public AssessmentType getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(AssessmentType assessmentType) {
        this.assessmentType = assessmentType;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
