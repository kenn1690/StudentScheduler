package com.c196Assessment.studentscheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c196Assessment.studentscheduler.Entities.Course;
import com.c196Assessment.studentscheduler.R;

import java.util.List;

//This class is the adapter for the course list recycler view.

public class CourseListAndTermDetailsAdapter extends RecyclerView.Adapter<CourseListAndTermDetailsAdapter.CourseListViewHolder> {
    class CourseListViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseIdItemView;
        private final TextView courseTitleItemView;
        private final TextView courseStartDateItemView;
        private final TextView courseEndDateItemView;


        //This class is the view holder for the course list.
        //It also listens for clicks and stores the information in
        //intents to give to the next activity.
        public CourseListViewHolder(@NonNull View itemView) {
            super(itemView);
            courseIdItemView = itemView.findViewById(R.id.courseId);
            courseTitleItemView = itemView.findViewById(R.id.courseTitle);
            courseStartDateItemView = itemView.findViewById(R.id.courseStartDate);
            courseEndDateItemView = itemView.findViewById(R.id.courseEndDate);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    final Course current = mCourse.get(position);
                    Intent intent = new Intent(context, AssessmentListAndCourseDetails.class);
                    intent.putExtra("courseId", current.getCourseId());
                    intent.putExtra("courseTitle", current.getCourseTitle());
                    intent.putExtra("courseStarDate", current.getCourseStartDate());
                    intent.putExtra("courseEndDate", current.getCourseEndDate());

                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Course> mCourse;
    private final Context context;
    private final LayoutInflater mInflater;

    //Constructor method for adapter
    public CourseListAndTermDetailsAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    //Creation of the view holder is made with the list item .xml and returned
    @NonNull
    @Override
    public CourseListAndTermDetailsAdapter.CourseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseListViewHolder(itemView);
    }

    //This class sets the text for the recycler view list that will be seen by the user
    @Override
    public void onBindViewHolder(@NonNull CourseListViewHolder holder, int position) {
        if(mCourse != null){
            final Course current = mCourse.get(position);
            holder.courseIdItemView.setText(Integer.toString(current.getCourseId()));
            holder.courseTitleItemView.setText(current.getCourseTitle());
            holder.courseStartDateItemView.setText(current.getCourseStartDate());
            holder.courseEndDateItemView.setText(current.getCourseEndDate());
        }
        else {
            holder.courseIdItemView.setText("No course id");
            holder.courseTitleItemView.setText("No course title");
            holder.courseStartDateItemView.setText("No course start date");
            holder.courseEndDateItemView.setText("No course end date");
        }
    }

    //Sets the course list to be used.
    public void setCourse(List<Course> courses){
        mCourse = courses;
        notifyDataSetChanged();
    }

    //Gets the total item count of the passed in list.
    @Override
    public int getItemCount() {
        if(mCourse != null){
            return mCourse.size();
        }
        else {
            return 0;
        }
    }


}
