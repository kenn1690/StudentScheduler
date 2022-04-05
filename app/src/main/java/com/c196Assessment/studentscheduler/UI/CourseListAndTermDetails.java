package com.c196Assessment.studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.c196Assessment.studentscheduler.Database.Repository;
import com.c196Assessment.studentscheduler.Entities.Course;
import com.c196Assessment.studentscheduler.Entities.Term;
import com.c196Assessment.studentscheduler.R;
import com.c196Assessment.studentscheduler.Utilities.DateInputTextWatcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

//The course list and term details class controls the course list and term details view.
//This class writes to the term database

public class CourseListAndTermDetails extends AppCompatActivity {
    final Calendar myCalendarStart = Calendar.getInstance(); //returns a calendar
    final Calendar myCalendarEnd = Calendar.getInstance(); //returns a calendar
    DatePickerDialog.OnDateSetListener myTermStartDate; //Confirms user has selected date
    DatePickerDialog.OnDateSetListener myTermEndDate; //Confirms user has selected date
    SimpleDateFormat sdf;
    private Repository repository;
    public static int numClasses;
    int id;
    String myFormat = "MM/dd/yyyy";
    List<Term> allTerms;
    EditText editTermTitle;
    EditText editTermStartDate;
    EditText editTermEndDate;
    Term currentTerm;
    RecyclerView recyclerView;
    Intent intent;
    DateInputTextWatcher ditw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list_and_term_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Gets back arrow to return a page
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Gets back arrow to return a page

        id = getIntent().getIntExtra("termId", - 1); //Gets the selected term id or assigns a value of -1
        repository = new Repository(getApplication());
        allTerms = repository.getTermList();
        //This method gets and sets the current
        //term if there is one.
        for(Term term : allTerms){
            if(term.getTermId() == id){
                currentTerm = term;
            }
        }
        editTermTitle = findViewById(R.id.termTitle);
        editTermStartDate = findViewById(R.id.termStartDate);
        ditw = new DateInputTextWatcher(editTermStartDate);
        //This sets the date after the user has picked it from the date picker
        myTermStartDate = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                sdf = new SimpleDateFormat(myFormat, Locale.US);
                updateTermStart();
            }
        };
        //Populates the date picker after the edit text assessment start date is clicked
        editTermStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseListAndTermDetails.this, myTermStartDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editTermEndDate = findViewById(R.id.termEndDate);
        ditw = new DateInputTextWatcher(editTermEndDate);
        //This sets the date after the user has picked it from the date picker
        myTermEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                sdf = new SimpleDateFormat(myFormat, Locale.US);

                updateTermEnd();
            }
        };
        //Populates the date picker after the edit text assessment start date is clicked
        editTermEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseListAndTermDetails.this, myTermEndDate, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //Checks to see if the current term is null and if it is not
        //sets the edit text's with the current term information.
        if(currentTerm != null){
            editTermTitle.setText(currentTerm.getTermTitle());
            editTermStartDate.setText(currentTerm.getTermStartDate());
            editTermEndDate.setText(currentTerm.getTermEndDate());
        }

        recyclerView = findViewById(R.id.courseRecView); //sets recycler view by id
        refreshCourseList(); //helps fill and refresh the course list
    }


    //Inflates the menu for the course list and term details screen
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_term_details, menu);
        return true;
    }
    //Determines which menu item is selected and performs the action
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.deleteTerm:
                if(numClasses == 0) {
                    repository.delete(currentTerm);
                    Toast.makeText(getApplicationContext(), "Term deleted", Toast.LENGTH_LONG).show();
                    intent = new Intent(CourseListAndTermDetails.this, TermList.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Can't delete term with associated classes", Toast.LENGTH_LONG).show();
                }
            case R.id.refreshClasses:
                refreshCourseList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Determines if the term is new or needs to be updated and then uses the
    //repository class to write the information from the edit text fields to the
    //database.
    public void saveTerm(View view) {
        if(id == - 1){
            id = allTerms.get(allTerms.size() - 1).getTermId();
            if(checkForEmptyText()){
                Toast.makeText(getApplicationContext(), "All fields must be filled in, term not created", Toast.LENGTH_LONG).show();
            }
            else {
                Term term = new Term(++id, editTermTitle.getText().toString(), editTermStartDate.getText().toString(), editTermEndDate.getText().toString());
                repository.insert(term);
                this.finish();
            }
        }
        else{
            if(checkForEmptyText()){
                Toast.makeText(getApplicationContext(), "All fields must be filled in, term not updated", Toast.LENGTH_LONG).show();
            }
            else {
                Term term = new Term(id, editTermTitle.getText().toString(), editTermStartDate.getText().toString(), editTermEndDate.getText().toString());
                repository.update(term);
                this.finish();
            }
        }

    }

    //Takes the user to the activity to where they can add a course to the term. It
    //bundles the term id so it can be given the the next activity.
    public void addCourse(View view) {
        if(id == -1 || checkForEmptyText()){
            Toast.makeText(getApplicationContext(), "All fields must be filled in and Term saved before adding classes", Toast.LENGTH_LONG).show();
        }
        else {
            intent = new Intent(CourseListAndTermDetails.this, AssessmentListAndCourseDetails.class);
            Bundle bundle = new Bundle();
            bundle.putInt("termIdBundle", currentTerm.getTermId());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    //This method sets the adapter for recycler view with the courseListAndTermDetailsAdapter
    //and sets layout to  linear. It builds a filtered list based on the courses term id.
    //The filtered list is set to the adapter.
    public void refreshCourseList(){
        final CourseListAndTermDetailsAdapter courseListAndTermDetailsAdapter = new CourseListAndTermDetailsAdapter(this);
        recyclerView.setAdapter(courseListAndTermDetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourse = new ArrayList<>();
        List<Course> allCourse = repository.getCourseList();
        for (Course course : allCourse){
            if(course.getTermId() == id){
                filteredCourse.add(course);
            }
        }
        numClasses = filteredCourse.size();
        courseListAndTermDetailsAdapter.setCourse(filteredCourse);
    }

    //Updates edit text the user sees with the date they selected
    private void updateTermStart() {
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        editTermStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }
    //Updates edit text the user sees with the date they selected
    private void updateTermEnd() {
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        editTermEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public boolean checkForEmptyText() {
        if (editTermTitle.getText().toString().isEmpty() ||
                editTermStartDate.getText().toString().isEmpty() ||
                editTermEndDate.getText().toString().isEmpty() ||
                editTermStartDate.getText().toString().contains("Y") ||
                editTermStartDate.getText().toString().contains("D") ||
                editTermStartDate.getText().toString().contains("M") ||
                editTermEndDate.getText().toString().contains("Y") ||
                editTermEndDate.getText().toString().contains("D") ||
                editTermEndDate.getText().toString().contains("M")) {
            return true;

        } else {
            return false;
        }
    }

}