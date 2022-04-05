package com.c196Assessment.studentscheduler.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.c196Assessment.studentscheduler.Entities.Assessment;

import java.util.List;

/*
This interface is for the Assessment data access objects.
When one of these access methods is called, it manipulates the
room database that is created. (created by Database builder class)
 */

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessment_table ORDER BY assessmentId ASC")
    List<Assessment> getAllAssessments();
}
