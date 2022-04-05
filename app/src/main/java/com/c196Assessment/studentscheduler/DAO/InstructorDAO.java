package com.c196Assessment.studentscheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.c196Assessment.studentscheduler.Entities.Instructor;

import java.util.List;

/*
This interface is for the Instructor data access objects.
When one of these access methods is called, it manipulates the
room database that is created. (created by Database builder class)
 */

@Dao
public interface InstructorDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Instructor instructor);

    @Update
    void update(Instructor instructor);

    @Delete
    void delete(Instructor instructor);

    @Query("SELECT * FROM instructor_table ORDER BY instructorId ASC")
    List<Instructor> getAllInstructors();
}
