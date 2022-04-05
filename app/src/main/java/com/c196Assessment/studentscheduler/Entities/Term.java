package com.c196Assessment.studentscheduler.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
The term class is an entity and generates a
table in the room database. Each attribute is a
column in the database and each time an term
entity is added, it generates a new row. These rows
can all be manipulated and deleted.
 */

@Entity(tableName = "term_table")
public class Term {
    @PrimaryKey (autoGenerate = true)
    private int termId;
    private String termTitle;
    private String termStartDate;
    private String termEndDate;

    //Constructor method.
    public Term(int termId, String termTitle, String termStartDate, String termEndDate) {
        this.termId = termId;
        this.termTitle = termTitle;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    //Allows us to represent an object as a string. Overriding
    //this method allows us to see the apt output.
    @Override
    public String toString() {
        return "Term{" +
                "termId=" + termId +
                ", termTitle='" + termTitle + '\'' +
                ", termStartDate='" + termStartDate + '\'' +
                ", termEndDate='" + termEndDate + '\'' +
                '}';
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public String getTermStartDate() {
        return termStartDate;
    }

    public void setTermStartDate(String termStartDate) {
        this.termStartDate = termStartDate;
    }

    public String getTermEndDate() {
        return termEndDate;
    }

    public void setTermEndDate(String termEndDate) {
        this.termEndDate = termEndDate;
    }
}
