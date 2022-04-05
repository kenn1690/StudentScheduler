package com.c196Assessment.studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.c196Assessment.studentscheduler.Database.Repository;
import com.c196Assessment.studentscheduler.Entities.Term;
import com.c196Assessment.studentscheduler.R;

import java.util.ArrayList;
import java.util.List;

//The term list controls the term list view activity. Also, it displays all the terms.

public class TermList extends AppCompatActivity {
    private Repository repository;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Gets back arrow to return a page
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Gets back arrow to return a page

        repository = new Repository(getApplication());


        recyclerView = findViewById(R.id.termRecView); //sets recycler view by id
        refreshTermList();

        EditText editText = findViewById(R.id.searchBar);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String text){
        List<Term> allTerms = repository.getTermList();
        ArrayList<Term> filteredTermList = new ArrayList<>();

        for(Term term : allTerms){
            if(term.getTermTitle().toLowerCase().contains(text.toLowerCase())){
                filteredTermList.add(term);
            }
        }
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(filteredTermList);

    }

    //Inflates the menu for the course list and term details screen
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_term_list, menu);
        return true;
    }

    //Determines which menu item is selected and performs the action
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.refreshTerms:
                refreshTermList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Takes the user to the activity to where they can add a term.
    public void addTerm(View view) {
        Intent intent = new Intent (TermList.this, CourseListAndTermDetails.class);
        startActivity(intent);
    }

    public void refreshTermList(){
        //This method sets the adapter for recycler view with the termAdapter and
        //sets the adapter with the full list of terms
        final TermAdapter termAdapter = new TermAdapter(this);
        List<Term> allTerms = repository.getTermList();
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
    }
}