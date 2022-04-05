package com.c196Assessment.studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.c196Assessment.studentscheduler.Database.Repository;
import com.c196Assessment.studentscheduler.Entities.Assessment;
import com.c196Assessment.studentscheduler.Entities.Course;
import com.c196Assessment.studentscheduler.Entities.CourseStatus;
import com.c196Assessment.studentscheduler.Entities.Instructor;
import com.c196Assessment.studentscheduler.R;
import com.c196Assessment.studentscheduler.Utilities.DateInputTextWatcher;
import com.c196Assessment.studentscheduler.Utilities.MyReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//The assessment list and course details class controls the assessment list and course details view.
//This class writes to both the course and instructor tables.

public class AssessmentListAndCourseDetails extends AppCompatActivity {
    final Calendar myCalendarStart = Calendar.getInstance(); //returns a calendar
    final Calendar myCalendarEnd = Calendar.getInstance(); //returns a calendar
    DatePickerDialog.OnDateSetListener myCourseStartDate; //Confirms user has selected date
    DatePickerDialog.OnDateSetListener myCourseEndDate; //Confirms user has selected date
    SimpleDateFormat sdf;
    private CourseStatus courseStatus;
    private Repository repository;
    public static int numCourses;
    int id;
    int termId;
    int instructorId;
    Long date;
    Instructor currentInstructor;
    String myFormat = "MM/dd/yyyy";
    List<Instructor> allInstructors;
    List<Course> allCourses;
    EditText editCourseTitle;
    EditText editCourseStartDate;
    EditText editCourseEndDate;
    EditText editCourseNotes;
    EditText editInstructorName;
    EditText editInstructorPhone;
    EditText editInstructorEmail;
    RadioButton inProgressRB;
    RadioButton completedRB;
    RadioButton droppedRB;
    RadioButton toTakeRB ;
    Course currentCourse;
    RecyclerView recyclerView;
    Intent intent;
    DateInputTextWatcher ditw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list_and_course_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Gets back arrow to return a page
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Gets back arrow to return a page
        id = getIntent().getIntExtra("courseId", -1); //Gets the selected course id or assigns a value of -1
        Bundle bundle = getIntent().getExtras(); //Gets the Term id from the previous activities screen so it assiged to the course
        termId= bundle.getInt("termIdBundle"); //sets the term id
        inProgressRB = findViewById(R.id.inProgressRB);
        completedRB = findViewById(R.id.completedRB);
        droppedRB = findViewById(R.id.droppedRB);
        toTakeRB = findViewById(R.id.toTakeRB);
        repository = new Repository(getApplication());
        allCourses = repository.getCourseList();
        allInstructors = repository.getInstructorList();

        //This method gets and sets the current
        //course if there is one.
        for(Course course : allCourses){
            if(course.getCourseId() == id){
                currentCourse = course;
            }
        }
        //This method gets and sets the current
        //instructor if there is one.
        for(Instructor instructor : allInstructors){
            if(instructor.getCourseId() == id){
                currentInstructor = instructor;
            }
        }

        editCourseTitle = findViewById(R.id.courseTitle);
        editCourseStartDate = findViewById(R.id.courseStartDate);
        editInstructorName = findViewById(R.id.instructorName);
        editInstructorPhone = findViewById(R.id.instructorPhone);
        editInstructorEmail = findViewById(R.id.instructorEmail);
        editCourseNotes = findViewById(R.id.courseNotes);
        //This sets the date after the user has picked it from the date picker
        ditw = new DateInputTextWatcher(editCourseStartDate);
        myCourseStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                sdf = new SimpleDateFormat(myFormat, Locale.US);
                updateCourseStart();
            }
        };
        //Populates the date picker after the edit text assessment start date is clicked
        editCourseStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AssessmentListAndCourseDetails.this, myCourseStartDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editCourseEndDate = findViewById(R.id.courseEndDate);
        ditw = new DateInputTextWatcher(editCourseEndDate);
        //This sets the date after the user has picked it from the date picker
        myCourseEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                sdf = new SimpleDateFormat(myFormat, Locale.US);

                updateCourseEnd();
            }
        };
        //Populates the date picker after the edit text assessment start date is clicked
        editCourseEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AssessmentListAndCourseDetails.this, myCourseEndDate, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Checks to see if the current course is null and if it is not
        //sets the edit text's with the current course and instructor information.
        if(currentCourse != null){
            editCourseTitle.setText(currentCourse.getCourseTitle());
            editCourseStartDate.setText(currentCourse.getCourseStartDate());
            editCourseEndDate.setText((currentCourse.getCourseEndDate()));
            editCourseNotes.setText(currentCourse.getNote());
            editInstructorName.setText(currentInstructor.getInstructorName());
            editInstructorPhone.setText(currentInstructor.getInstructorPhone());
            editInstructorEmail.setText(currentInstructor.getInstructorEmail());
            getCourseStatus();
        }

        recyclerView = findViewById(R.id.assessmentRecView); //sets recycler view by id
        refreshAssessmentsList(); //helps fill and refresh the assessment list
    }

    //Inflates the menu for the assessment list and course details screen
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }

    //Determines which menu item is selected and performs the action
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.deleteCourse:
                if(numCourses == 0) {
                    repository.delete(currentCourse);
                    Toast.makeText(getApplicationContext(), "Course deleted", Toast.LENGTH_LONG).show();
                    intent = new Intent(AssessmentListAndCourseDetails.this, CourseListAndTermDetails.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Can't delete course with associated assessments", Toast.LENGTH_LONG).show();
                }
            case R.id.refreshAssessments:
                refreshAssessmentsList();
                return true;
            case R.id.shareNote:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editCourseNotes.getText().toString());
                // (Optional) Here we're setting the title of the content
                sendIntent.putExtra(Intent.EXTRA_TITLE, editCourseTitle.getText().toString() + " notes");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.courseNotification:
                intent=new Intent(AssessmentListAndCourseDetails.this, MyReceiver.class);
                intent.putExtra("key", currentCourse.getCourseTitle() + " starts today!");
                String dateFromScreen=editCourseStartDate.getText().toString();
                sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate=null;
                try {
                    myDate=sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                PendingIntent sender= PendingIntent.getBroadcast(AssessmentListAndCourseDetails.this,
                        ++MainActivity.numAlert, intent,0);
                AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                date=myDate.getTime();
                alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    //Sets the course status based on radio button selected
    public void onCourseStatusRBClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.inProgressRB:
                if(checked){
                    courseStatus = CourseStatus.IN_PROGRESS;
                }
            case R.id.completedRB:
                if(checked){
                    courseStatus = CourseStatus.COMPLETED;
                }
            case R.id.droppedRB:
                if(checked){
                    courseStatus = CourseStatus.DROPPED;
                }
            case R.id.toTakeRB:
                if(checked){
                    courseStatus = CourseStatus.TO_TAKE;
                }
        }
    }

    //Determines if the course is new or needs to be updated and then uses the
    //repository class to write the information from the edit text fields to the
    //database. This also updates information for the instructor tables.
    public void saveCourse(View view) {
        if(id == -1){
            id = allCourses.get(allCourses.size() -1).getCourseId();
            if(checkForEmptyText()){
                Toast.makeText(getApplicationContext(), "Course fields must be filled in, no course created", Toast.LENGTH_LONG).show();
            }
            else {
                Course course = new Course(++id, termId, editCourseTitle.getText().toString(), editCourseStartDate.getText().toString(), editCourseEndDate.getText().toString(),
                        courseStatus, editCourseNotes.getText().toString());
                repository.insert(course);
                instructorId = allInstructors.get(allInstructors.size() - 1).getInstructorId();
                Instructor instructor = new Instructor(++instructorId, id, editInstructorName.getText().toString(), editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString());
                repository.insert(instructor);
                this.finish();
            }
        }
        else{
            if(checkForEmptyText()){
                Toast.makeText(getApplicationContext(), "Course fields must be filled in, course not updated", Toast.LENGTH_LONG).show();
            }
            else {
                Course course = new Course(id, currentCourse.getTermId(), editCourseTitle.getText().toString(),
                        editCourseStartDate.getText().toString(), editCourseEndDate.getText().toString(),
                        courseStatus, editCourseNotes.getText().toString());
                repository.update(course);
                Instructor instructor = new Instructor(instructorId, currentCourse.getCourseId(), editInstructorName.getText().toString(),
                        editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString());
                repository.update(instructor);
                this.finish();
            }

        }
    }

    //Takes the user to the activity to where they can add an assessment to the course. It
    //bundles the course id so it can be given the the next activity.
    public void addAssessment(View view) {
        if(id == -1 || checkForEmptyText()){
            Toast.makeText(getApplicationContext(), "Course fields must be filled and saved before creating an assessment", Toast.LENGTH_LONG).show();
        }
        else {
            intent = new Intent(AssessmentListAndCourseDetails.this, AssessmentDetails.class);
            Bundle bundle = new Bundle();
            bundle.putInt("courseIdBundle", currentCourse.getCourseId());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    //This method checks the current course for its status and will
    //fill in the radio button that corresponds. It is called in the
    //onCreate method above.
    public void getCourseStatus(){
        if(currentCourse.getCourseStatus() == CourseStatus.IN_PROGRESS){
            inProgressRB.setChecked(true);
        }
        if(currentCourse.getCourseStatus() == CourseStatus.COMPLETED){
            completedRB.setChecked(true);
        }
        if(currentCourse.getCourseStatus() == CourseStatus.DROPPED){
            droppedRB.setChecked(true);
        }
        if(currentCourse.getCourseStatus() == CourseStatus.TO_TAKE){
            toTakeRB.setChecked(true);
        }
    }

    //This method sets the adapter for recycler view with the assessmentListAndCourseDetailsAdapter
    //and sets layout to  linear. It builds a filtered list based on the assessments course id.
    //The filtered list is set to the adapter.
    public void refreshAssessmentsList(){
        final AssessmentListAndCourseDetailsAdapter assessmentListAndCourseDetailsAdapter = new AssessmentListAndCourseDetailsAdapter(this);
        recyclerView.setAdapter(assessmentListAndCourseDetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for(Assessment assessment : repository.getAssessmentList()){
            if(assessment.getCourseId() == id){
                filteredAssessments.add(assessment);
            }
        }
        numCourses = filteredAssessments.size();
        assessmentListAndCourseDetailsAdapter.setAssessment(filteredAssessments);
    }
    //Updates edit text the user sees with the date they selected
    private void updateCourseStart(){
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        editCourseStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }
    //Updates edit text the user sees with the date they selected
    private void updateCourseEnd(){
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        editCourseEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }
    //Method that checks for empty text in edit text fields
    public boolean checkForEmptyText(){
        if(editCourseTitle.getText().toString().isEmpty()||
                editCourseStartDate.getText().toString().isEmpty()||
                editCourseEndDate.getText().toString().isEmpty()||
                editCourseStartDate.getText().toString().contains("Y")||
                editCourseStartDate.getText().toString().contains("D")||
                editCourseStartDate.getText().toString().contains("M")||
                editCourseEndDate.getText().toString().contains("Y")||
                editCourseEndDate.getText().toString().contains("D")||
                editCourseEndDate.getText().toString().contains("M")){
            return true;

        }
        else{
            return false;
        }
    }

}