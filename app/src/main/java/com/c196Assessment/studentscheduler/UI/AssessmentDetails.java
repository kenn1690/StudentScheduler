package com.c196Assessment.studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

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
import com.c196Assessment.studentscheduler.Entities.AssessmentType;
import com.c196Assessment.studentscheduler.R;
import com.c196Assessment.studentscheduler.Utilities.DateInputTextWatcher;
import com.c196Assessment.studentscheduler.Utilities.MyReceiver;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//The assessment details class controls the assessment details view.

public class AssessmentDetails extends AppCompatActivity {
    final Calendar myCalendarStart = Calendar.getInstance(); //returns a calendar
    final Calendar myCalendarEnd = Calendar.getInstance(); //returns a calendar
    DatePickerDialog.OnDateSetListener myAssessmentStartDate; //Confirms user has selected date
    DatePickerDialog.OnDateSetListener myAssessmentEndDate; //Confirms user has selected date
    SimpleDateFormat sdf;
    String myFormat = "MM/dd/yyyy";
    private AssessmentType assessmentType;
    private Repository repository;
    int id;
    int courseId;
    EditText editAssessmentTitle;
    EditText editAssessmentStartDate;
    EditText editAssessmentEndDate;
    RadioButton objectiveRB;
    RadioButton performanceRB;
    Assessment currentAssessment;
    List<Assessment> allAssessments;
    Intent intent;
    Long date; //Long to store the current date time
    DateInputTextWatcher ditw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Gets back arrow to return a page
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Gets back arrow to return a page
        id = getIntent().getIntExtra("assessmentId", -1); //Gets the selected assessment id or assigns a value of -1
        Bundle bundle = getIntent().getExtras(); //Gets the course id from the previous activities screen so it can be assigned to the assessment
        courseId = bundle.getInt("courseIdBundle"); //sets the course id
        objectiveRB = findViewById(R.id.objectiveRB);
        performanceRB = findViewById(R.id.performanceRB);
        repository = new Repository(getApplication());
        allAssessments = repository.getAssessmentList();
        //This method gets and sets the current
        //assessment if there is one.
        for(Assessment assessment : allAssessments){
            if(assessment.getAssessmentId() == id){
                currentAssessment = assessment;
            }
        }

        editAssessmentTitle = findViewById(R.id.assessmentTitle);
        editAssessmentStartDate = findViewById(R.id.assessmentStartDate);
        ditw = new DateInputTextWatcher(editAssessmentStartDate);
        //This sets the date after the user has picked it from the date picker
        myAssessmentStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                sdf = new SimpleDateFormat(myFormat, Locale.US);
                //calls method to update the edit text that the user sees to appropriate date
                updateAssessmentStart();
            }
        };
        //Populates the date picker after the edit text assessment start date is clicked
        editAssessmentStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AssessmentDetails.this, myAssessmentStartDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editAssessmentEndDate = findViewById(R.id.assessmentEndDate);
        ditw = new DateInputTextWatcher(editAssessmentEndDate);
        //This sets the date after the user has picked it from the date picker
        myAssessmentEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                sdf = new SimpleDateFormat(myFormat, Locale.US);

                updateAssessmentEnd();
            }
        };
        //Populates the date picker after the edit text assessment start date is clicked
        editAssessmentEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AssessmentDetails.this, myAssessmentEndDate, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Checks to see if the current assessment is null and if it is not
        //sets the edit text's with the current assessment information.
        if(currentAssessment != null){
            editAssessmentTitle.setText(currentAssessment.getAssessmentTitle());
            editAssessmentStartDate.setText(currentAssessment.getAssessmentStartDate());
            editAssessmentEndDate.setText(currentAssessment.getAssessmentEndDate());
            getAssessmentType();
        }

    }
    //Inflates the menu for the assessment details screen
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    //Determines which menu item is selected and performs the action
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.deleteAssessment:
                repository.delete(currentAssessment);
            case R.id.assessmentNotification:
                intent=new Intent(AssessmentDetails.this, MyReceiver.class);
                intent.putExtra("key", currentAssessment.getAssessmentTitle() + " test is today!");
                String dateFromScreen=editAssessmentStartDate.getText().toString();
                sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate=null;
                try {
                    myDate=sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                PendingIntent sender= PendingIntent.getBroadcast(AssessmentDetails.this,
                        ++MainActivity.numAlert, intent,0);
                AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                date=myDate.getTime();
                alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    //Sets the assessment type based on the radio button selected
    public void assessmentTypeRBOnClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()){
            case R.id.objectiveRB:
                if(checked){
                    assessmentType = AssessmentType.OBJECTIVE;
                }
            case R.id.performanceRB:
                if(checked){
                    assessmentType = AssessmentType.PERFORMANCE;
                }
        }
    }

    //Determines if the assessment is new or needs to be updated and then uses the
    //repository class to write the information from the edit text fields to the
    //database.
    public void saveAssessment(View view) {
        if (id == -1) {
            id = allAssessments.get(allAssessments.size() - 1).getAssessmentId();
            //If any of the fields are empty
            if(editAssessmentStartDate.getText().toString().isEmpty() ||
                    editAssessmentEndDate.getText().toString().isEmpty() ||
                    editAssessmentTitle.getText().toString().isEmpty() ||
                    editAssessmentStartDate.getText().toString().contains("Y")||
                    editAssessmentStartDate.getText().toString().contains("D")||
                    editAssessmentStartDate.getText().toString().contains("M")||
                    editAssessmentEndDate.getText().toString().contains("Y")||
                    editAssessmentEndDate.getText().toString().contains("D")||
                    editAssessmentEndDate.getText().toString().contains("M")){
                Toast.makeText(getApplicationContext(), "All fields must be filled in, no assessment created", Toast.LENGTH_LONG).show();
            }
            else {
                Assessment assessment = new Assessment(++id, editAssessmentTitle.getText().toString(), editAssessmentStartDate.getText().toString(),
                        editAssessmentEndDate.getText().toString(), assessmentType, courseId);
                repository.insert(assessment);
                this.finish();
            }
        } else {
            //If any of the fields are empty
            if(editAssessmentStartDate.getText().toString().isEmpty() ||
                    editAssessmentEndDate.getText().toString().isEmpty() ||
                    editAssessmentTitle.getText().toString().isEmpty() ||
                    editAssessmentStartDate.getText().toString().contains("Y")||
                    editAssessmentStartDate.getText().toString().contains("D")||
                    editAssessmentStartDate.getText().toString().contains("M")||
                    editAssessmentEndDate.getText().toString().contains("Y")||
                    editAssessmentEndDate.getText().toString().contains("D")||
                    editAssessmentEndDate.getText().toString().contains("M")){
                Toast.makeText(getApplicationContext(), "All fields must be filled in, assessment didn't update", Toast.LENGTH_LONG).show();
            }
            else {
                Assessment assessment = new Assessment(id, editAssessmentTitle.getText().toString(), editAssessmentStartDate.getText().toString(),
                        editAssessmentEndDate.getText().toString(), assessmentType, courseId);
                repository.update(assessment);
                this.finish();
            }
        }

    }

    //This method checks the current assessment for its type and will
    //fill in the radio button that corresponds. It is called in the
    //onCreate method above.
    public void getAssessmentType(){
        if(currentAssessment.getAssessmentType() == AssessmentType.OBJECTIVE){
            objectiveRB.setChecked(true);
        }
        if(currentAssessment.getAssessmentType() == AssessmentType.PERFORMANCE){
            performanceRB.setChecked(true);
        }
    }

    //Updates edit text the user sees with the date they selected
    private void updateAssessmentStart(){
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        editAssessmentStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }
    //Updates edit text the user sees with the date they selected
    private void updateAssessmentEnd(){
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        editAssessmentEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }



}