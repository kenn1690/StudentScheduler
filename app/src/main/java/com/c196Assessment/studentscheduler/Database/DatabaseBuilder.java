package com.c196Assessment.studentscheduler.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.c196Assessment.studentscheduler.DAO.UserDAO;
import com.c196Assessment.studentscheduler.Entities.Assessment;
import com.c196Assessment.studentscheduler.Entities.Course;
import com.c196Assessment.studentscheduler.Entities.Instructor;
import com.c196Assessment.studentscheduler.Entities.Term;
import com.c196Assessment.studentscheduler.Entities.Users;

import com.c196Assessment.studentscheduler.DAO.AssessmentDAO;
import com.c196Assessment.studentscheduler.DAO.CourseDAO;
import com.c196Assessment.studentscheduler.DAO.InstructorDAO;
import com.c196Assessment.studentscheduler.DAO.TermDAO;


/*
This uses the @Database to create a room DB with classes that are marked
as entities. The class is abstract so it may work with the room database.
The room database then provides the implementations to access/manipulate
content for each DAO added.
 */

@Database(entities = {Assessment.class, Course.class, Instructor.class, Term.class, Users.class}, version = 28, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract AssessmentDAO assessmentDAO();
    public abstract CourseDAO courseDAO();
    public abstract InstructorDAO instructorDAO();
    public abstract TermDAO termDAO();
    public abstract UserDAO userDAO();
    private static volatile DatabaseBuilder INSTANCE; //This is made volatile to run on multiple threads.
    static DatabaseBuilder getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (DatabaseBuilder.class){
                if(INSTANCE == null){
                    //The fallbackToDestructiveMigration() will deleted the database
                    //and start again if the DB schema changes.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "StudentScheduler.db").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
