package com.c196Assessment.studentscheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.c196Assessment.studentscheduler.Entities.Term;

import java.util.List;

/*
This interface is for the Term data access objects.
When one of these access methods is called, it manipulates the
room database that is created. (Room DB created by Database builder class)
 */

@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("SELECT * FROM term_table ORDER BY termId ASC")
    List<Term> getAllTerms();
}
