package com.c196Assessment.studentscheduler.Database;

import android.app.Application;

import com.c196Assessment.studentscheduler.DAO.AssessmentDAO;
import com.c196Assessment.studentscheduler.DAO.CourseDAO;
import com.c196Assessment.studentscheduler.DAO.InstructorDAO;
import com.c196Assessment.studentscheduler.DAO.TermDAO;
import com.c196Assessment.studentscheduler.DAO.UserDAO;
import com.c196Assessment.studentscheduler.Entities.Assessment;
import com.c196Assessment.studentscheduler.Entities.Course;
import com.c196Assessment.studentscheduler.Entities.Instructor;
import com.c196Assessment.studentscheduler.Entities.Term;
import com.c196Assessment.studentscheduler.Entities.Users;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
This repository works with the DAO to generate queries.
It is the logic deciding whether to get, update, delete
or add information. It uses the ExecutorService and a pool
of threads to perform these tasks. When calling repository,
it will always require one of the entities and then use the
DAO which will use it's own implementations to complete the
query.
 */

public class Repository {
    private AssessmentDAO mAssessmentDAO;
    private CourseDAO mCourseDAO;
    private InstructorDAO mInstructorDAO;
    private TermDAO mTermDAO;
    private UserDAO mUsersDAO;
    private List<Users> mUserList;
    private List<Assessment> mAssessmentList;
    private List<Course> mCourseList;
    private List<Instructor> mInstructorList;
    private List<Term> mTermList;
    private static int NUMBER_OF_THREADS = 4; //Number of threads to be used.
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application); //used to connect to database
        mAssessmentDAO = db.assessmentDAO();
        mCourseDAO = db.courseDAO();
        mInstructorDAO = db.instructorDAO();
        mTermDAO = db.termDAO();
        mUsersDAO = db.userDAO();
    }
    public void insert(Users user){
        databaseExecutor.execute(()-> {
            mUsersDAO.insert(user);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public List<Users> getUsersList(){
        databaseExecutor.execute(()->{
            mUserList = mUsersDAO.getAllUsers();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mUserList;
    }
    public List<Assessment> getAssessmentList(){
        databaseExecutor.execute(()-> {
            mAssessmentList = mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAssessmentList;
    }
    public void insert(Assessment assessment){
        databaseExecutor.execute(()-> {
            mAssessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Assessment assessment){
        databaseExecutor.execute(()-> {
            mAssessmentDAO.update(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Assessment assessment){
        databaseExecutor.execute(()-> {
            mAssessmentDAO.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public List<Course> getCourseList(){
        databaseExecutor.execute(()-> {
            mCourseList = mCourseDAO.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mCourseList;
    }
    public void insert(Course course){
        databaseExecutor.execute(()-> {
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.delete(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Instructor> getInstructorList(){
        databaseExecutor.execute(()-> {
            mInstructorList = mInstructorDAO.getAllInstructors();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mInstructorList;
    }
    public void insert(Instructor instructor){
        databaseExecutor.execute(()-> {
            mInstructorDAO.insert(instructor);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Instructor instructor) {
        databaseExecutor.execute(()->{
            mInstructorDAO.update(instructor);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Instructor instructor){
        databaseExecutor.execute(()->{
            mInstructorDAO.delete(instructor);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public List<Term> getTermList(){
        databaseExecutor.execute(()->{
            mTermList = mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mTermList;
    }
    public void insert(Term term){
        databaseExecutor.execute(()->{
          mTermDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Term term){
        databaseExecutor.execute(()->{
           mTermDAO.update(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Term term){
        databaseExecutor.execute(()->{
          mTermDAO.delete(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
