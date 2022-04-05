package com.c196Assessment.studentscheduler.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.c196Assessment.studentscheduler.Entities.Users;

import java.util.List;
@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Users user);

    @Query("SELECT * FROM user_table")
    List<Users> getAllUsers();
}
