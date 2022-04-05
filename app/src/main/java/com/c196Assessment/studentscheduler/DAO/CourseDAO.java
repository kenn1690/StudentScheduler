package com.c196Assessment.studentscheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.c196Assessment.studentscheduler.Entities.Course;

import java.util.List;

/*
This interface is for the Course data access objects.
When one of these access methods is called, it manipulates the
room database that is created. (created by Database builder class)
 */

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM course_table ORDER BY courseId ASC")
    List<Course> getAllCourses();
}
