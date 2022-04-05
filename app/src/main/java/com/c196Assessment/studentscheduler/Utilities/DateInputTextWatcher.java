package com.c196Assessment.studentscheduler.Utilities;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Calendar;

//This class makes sure that the dates are going in the correct format

public class DateInputTextWatcher implements TextWatcher {
    private String current = "";
    private String mmddyyyy = "MMDDYYYY";
    private Calendar cal = Calendar.getInstance();
    private EditText date;

    public DateInputTextWatcher(EditText date){
        this.date = date;
        this.date.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals(current)) {
            String clear = s.toString().replaceAll("[^\\d.]|\\.", ""); //takes out any slashes
            String clearCurrent = current.replaceAll("[^\\d.]|\\.", ""); //current is set to the current string before change takes effect

            int cl = clear.length(); //gets the length of date with out slashes
            int sel = cl;
            for (int i = 2; i <= cl && i < 6; i += 2) { //
                sel++;
            }

            if (clear.equals(clearCurrent)){
                sel--; //makes sure it does not delete slashes
            }

            if (clear.length() < 8){ //if the length is still less than 8, it will add the MM, DD or YY based on length
                clear = clear + mmddyyyy.substring(clear.length());
            }
            else{
                //This part parses the date into individual pieces to
                //check and make sure they are with in parameters
                int mon  = Integer.parseInt(clear.substring(0,2));
                int day  = Integer.parseInt(clear.substring(2,4));
                int year = Integer.parseInt(clear.substring(4,8));

                mon = mon < 1 ? 1 : mon > 12 ? 12 : mon; //Makes sure month is between 1-12
                cal.set(Calendar.MONTH, mon-1); //sets the month to minus one because java and calendars starting at 0.
                year = (year<1900)?1900:(year>2100)?2100:year; //checks to make sure its a valid year
                cal.set(Calendar.YEAR, year); //sets the year

                //This here gets the current maximum calendar days in the selected month and confirms the
                //day entered is not greater than the max. If it is, it will set the day, year or month
                //to the max allowed by the parameters set.
                day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                clear = String.format("%02d%02d%02d",mon, day, year);
            }

            //adds the MM/DD/YYYY  format as you are typing
            clear = String.format("%s/%s/%s", clear.substring(0, 2),
                    clear.substring(2, 4),
                    clear.substring(4, 8));

            sel = sel < 0 ? 0 : sel;
            current = clear; //does one last check before setting text
            date.setText(current);
            date.setSelection(sel < current.length() ? sel : current.length());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
