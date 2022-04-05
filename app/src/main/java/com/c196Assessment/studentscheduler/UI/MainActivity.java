package com.c196Assessment.studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.c196Assessment.studentscheduler.Database.Repository;
import com.c196Assessment.studentscheduler.Entities.Assessment;
import com.c196Assessment.studentscheduler.Entities.AssessmentType;
import com.c196Assessment.studentscheduler.Entities.Course;
import com.c196Assessment.studentscheduler.Entities.CourseStatus;
import com.c196Assessment.studentscheduler.Entities.Instructor;
import com.c196Assessment.studentscheduler.Entities.Term;
import com.c196Assessment.studentscheduler.Entities.Users;
import com.c196Assessment.studentscheduler.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static int numAlert; //static in that will keep track of the number of alerts made
    //this page makes sure the user has credentials to get into the application providing a
    //level of security to the application.

    EditText userName;
    EditText userPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Repository repository = new Repository(getApplication());
        Instructor instructor = new Instructor(1, 1, "Ken", "517-517-5577", "ks@gmail.com");
        repository.insert(instructor);
        Instructor instructor1 = new Instructor(2, 2, "Meg", "517-517-5577", "ms@gmail.com");
        repository.insert(instructor1);
        Term term = new Term(1, "Spring", "01/07/2022", "05/31/2022");
        repository.insert(term);
        Term term1 = new Term(2, "Fall", "09/01/2022", "12/16/2022");
        repository.insert(term1);
        Course course = new Course(1, 1,"Bio", "01/07/2022", "05/31/2022", CourseStatus.IN_PROGRESS, "Hello");
        repository.insert(course);
        Course course1 = new Course(2, 2,"Physics", "09/01/2022", "12/16/2022", CourseStatus.IN_PROGRESS, "Hello");
        repository.insert(course1);
        Assessment assessment = new Assessment(1, "Bio", "05/28/2022", "05/28/2022", AssessmentType.OBJECTIVE, 1);
        repository.insert(assessment);
        Assessment assessment1 = new Assessment(2, "Physics", "10/28/2022", "10/28/2022", AssessmentType.OBJECTIVE, 2);
        repository.insert(assessment1);
        Users user = new Users(1, "test", "test");
        repository.insert(user);
    }
    //Takes the user to the terms view
    public void loginButton(View view){
        userName = findViewById(R.id.userName);
        userPassword = findViewById(R.id.password);
        Repository repository = new Repository(getApplication());
        List<Users> userList = repository.getUsersList();
        for(Users user : userList){
            if(user.getUserName().equals(userName.getText().toString()) && user.getUserPassword().equals(userPassword.getText().toString())){
                Intent intent = new Intent (MainActivity.this, TermList.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
            }
        }

    }

}